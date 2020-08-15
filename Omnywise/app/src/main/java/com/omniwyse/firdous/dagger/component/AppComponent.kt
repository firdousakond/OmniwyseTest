package com.omniwyse.firdous.dagger.component

import android.app.Application
import android.content.Context
import com.omniwyse.firdous.OmniwyseApplication
import com.omniwyse.firdous.dagger.module.ActivityModule
import com.omniwyse.firdous.dagger.module.ApiServiceModule
import com.omniwyse.firdous.dagger.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton



@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ApiServiceModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(context: Context) : Builder

        fun build(): AppComponent
    }

    fun inject(omniwyseApplication: OmniwyseApplication)

}