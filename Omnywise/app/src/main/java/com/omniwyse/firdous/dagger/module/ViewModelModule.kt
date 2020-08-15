package com.omniwyse.firdous.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omniwyse.firdous.dagger.OmniwyseViewModelFactory
import com.omniwyse.firdous.dagger.ViewModelKey
import com.omniwyse.firdous.view.users.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: OmniwyseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun userViewModel(userViewModel: UserViewModel): ViewModel


}
