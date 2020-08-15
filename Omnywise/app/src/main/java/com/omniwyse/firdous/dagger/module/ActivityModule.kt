package com.omniwyse.firdous.dagger.module

import com.omniwyse.firdous.view.details.UserDetailsActivity
import com.omniwyse.firdous.view.users.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract fun contributeUserActivity() : UserActivity

    @ContributesAndroidInjector
    abstract fun contributeUserDetailsActivity(): UserDetailsActivity

}