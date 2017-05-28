package nz.co.chrisdrake.tv

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import nz.co.chrisdrake.tv.data.DataModule
import javax.inject.Singleton

@Component(modules = arrayOf(
    ApplicationModule::class,
    ActivityBindingModule::class,
    AndroidInjectionModule::class,
    DataModule::class
))
@Singleton interface ApplicationComponent : AndroidInjector<TvApplication>