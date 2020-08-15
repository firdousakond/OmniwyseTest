package com.omniwyse.firdous.dagger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.omniwyse.firdous.OmniwyseApplication
import com.omniwyse.firdous.dagger.component.DaggerAppComponent
import com.omniwyse.firdous.util.Logger
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    fun init(omniwyseApplication: OmniwyseApplication) {
        DaggerAppComponent.builder().application(omniwyseApplication).appContext(omniwyseApplication)
            .build().inject(omniwyseApplication)

        omniwyseApplication
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {
                    Logger.debug("Activity Started")
                }

                override fun onActivityResumed(activity: Activity) {
                    Logger.debug("Activity Resumed")
                }

                override fun onActivityPaused(activity: Activity) {
                    Logger.debug("Activity Paused")
                }

                override fun onActivityStopped(activity: Activity) {
                    Logger.debug("Activity Stopped")
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                    Logger.debug("Activity Saved Instance")
                }

                override fun onActivityDestroyed(activity: Activity) {
                    Logger.debug("Activity Destroyed")
                }
            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is Injectable) AndroidSupportInjection.inject(f)
                        }

                    }, true
                )
        }
    }
}