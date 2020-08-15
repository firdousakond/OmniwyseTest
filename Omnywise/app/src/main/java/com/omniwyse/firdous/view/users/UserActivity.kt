package com.omniwyse.firdous.view.users

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omniwyse.firdous.R
import com.omniwyse.firdous.model.UserModel
import com.omniwyse.firdous.util.*
import com.omniwyse.firdous.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_toolbar.*
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

class UserActivity : BaseActivity() {

    @Inject
    lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private var page = 0
    private var isListView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        initComponents()
        setListener()
        initObservers()
        initToolbar(tool_bar)

    }

    private fun initObservers() {
        userViewModel.userLiveData.observe(this, userObserver)
    }

    private val userObserver = Observer<LiveDataResult<List<UserModel>>> { result ->
        when (result.status) {
            LiveDataResult.Status.LOADING -> {
                userAdapter.addLoadingFooter()
            }

            LiveDataResult.Status.SUCCESS -> {
                userAdapter.removeLoadingFooter()
                userAdapter.addAll(result.data!!)

            }

            LiveDataResult.Status.ERROR -> {
                userAdapter.removeLoadingFooter()
                displaySnackbar(this, getString(R.string.error_msg_user), MessageType.ERROR)
            }
        }

    }


    private fun setListener(){
        ivSwitchView.setOnClickListener { switchView() }
    }

    private fun switchView() {
        userAdapter.setViewType(getViewType())
        setUserAdapter()
    }

    private fun getViewType(): Int {
        return if(isListView){
            isListView = false
            rvUsers.initializeGridVertical(userAdapter,2)
            ivSwitchView.setImageResource(R.drawable.ic_list)
            GRID_VIEW

        }else{
            isListView = true
            rvUsers.initializeVertical(userAdapter)
            ivSwitchView.setImageResource(R.drawable.ic_grid)
            LIST_VIEW
        }
    }

    private fun initComponents() {
        tvHeader.text = getString(R.string.users)
        ivSwitchView.show()
        userAdapter = UserAdapter(this)
        rvUsers.initializeVertical(userAdapter)
        userViewModel.fetchUsers(page)
        setUserAdapter()
    }

    private fun setUserAdapter() {
        val layoutManager = if(isListView) {
            rvUsers.layoutManager as LinearLayoutManager
        }else{
            rvUsers.layoutManager as GridLayoutManager
        }
        var pastVisibleItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            page++
                            userViewModel.fetchUsers(page)
                        }
                }
            }


        })
    }

}