package com.osapps.capitalslearner.dagger

import android.app.Application

import com.osapps.capitalslearner.App

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by osApps
 */

/**
 * The app component class serves as a glue between the application and the modules.
 * Here we state which modules will be used in the application.
 * AndroidSupportInjectionModule - a generic injection module belongs to google's dagger.
 * AppModule - a module which holds all of the single instances in the app scope (SharedPrefs, ApiServices, Picasso and etc).
 * ActivityBuilder - a module which holds all of the activities modules and providers
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class]) //map of all of the activities and their modules here
interface AppComponent : AndroidInjector<DaggerApplication> {


    //Injection types incl Application or Service type
    fun inject(app: App)  //application injection

    //fun inject(service: FirebaseTokenService)  //service injection example


    //a generic builder
    override fun inject(instance: DaggerApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
