package com.omniwyse.firdous

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.omniwyse.firdous.service.UserService
import com.omniwyse.firdous.view.users.UserViewModel
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var userViewModel: UserViewModel

    @Mock
    private lateinit var userService: UserService

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

}
