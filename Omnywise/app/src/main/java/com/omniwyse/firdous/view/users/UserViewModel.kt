package com.omniwyse.firdous.view.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omniwyse.firdous.model.UserModel
import com.omniwyse.firdous.service.UserService
import com.omniwyse.firdous.util.LiveDataResult
import com.omniwyse.firdous.util.PER_PAGE
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserViewModel @Inject constructor(private var userService: UserService) : ViewModel() {

    var userLiveData = MutableLiveData<LiveDataResult<List<UserModel>>>()
    private lateinit var disposable: Disposable

    fun fetchUsers(since: Int){
        userService.getUsers(since, PER_PAGE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(UserResult())
    }

    private inner class UserResult : SingleObserver<List<UserModel>>{

        override fun onSuccess(t: List<UserModel>) {
           userLiveData.value = LiveDataResult.success(t)
        }

        override fun onSubscribe(d: Disposable) {
            disposable = d
            userLiveData.value = LiveDataResult.loading()
        }

        override fun onError(e: Throwable) {
           userLiveData.value = LiveDataResult.error(e)
        }

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}