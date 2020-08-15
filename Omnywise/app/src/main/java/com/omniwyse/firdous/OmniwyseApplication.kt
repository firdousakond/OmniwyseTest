package com.omniwyse.firdous

import android.app.Application
import com.omniwyse.firdous.dagger.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class OmniwyseApplication : Application(), HasAndroidInjector {

    companion object {
        lateinit var context: OmniwyseApplication
            private set
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        context = this
    }

    override fun androidInjector(): AndroidInjector<Any>  = dispatchingAndroidInjector


}