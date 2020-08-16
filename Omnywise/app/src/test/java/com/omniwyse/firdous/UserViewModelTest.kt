package com.omniwyse.firdous

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.omniwyse.firdous.model.UserModel
import com.omniwyse.firdous.service.UserService
import com.omniwyse.firdous.util.LiveDataResult
import com.omniwyse.firdous.view.users.UserViewModel
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var userViewModel: UserViewModel

    @Mock
    lateinit var userService: UserService

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxSchedulerRule()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.userViewModel =
            UserViewModel(userService)
    }

    @Test
    fun `fetch user positive response`() {
        Mockito.`when`(userService.getUsers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenAnswer {
            return@thenAnswer Single.just(ArgumentMatchers.anyList<UserModel>())
        }
        val observer = Mockito.mock(Observer::class.java)
                as Observer<LiveDataResult<List<UserModel>>>
        userViewModel.userLiveData.observeForever(observer)
        userViewModel.fetchUsers(eq(ArgumentMatchers.anyInt()))
        Assert.assertNotNull(userViewModel.userLiveData.value)
        assertEquals(LiveDataResult.Status.SUCCESS, userViewModel.userLiveData.value?.status)

    }

    @Test
    fun `fetch user error response`() {
        Mockito.`when`(userService.getUsers(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
            .thenAnswer {
                return@thenAnswer Single.error<Throwable>(Throwable("Network Error"))
            }

        val observer = Mockito.mock(Observer::class.java)
                as Observer<LiveDataResult<List<UserModel>>>

        userViewModel.userLiveData.observeForever(observer)

        userViewModel.fetchUsers(eq(ArgumentMatchers.anyInt()))

        Assert.assertNotNull(userViewModel.userLiveData.value)
        assertEquals(LiveDataResult.Status.ERROR, userViewModel.userLiveData.value?.status)
        assert(userViewModel.userLiveData.value?.err is Throwable)
    }

}
