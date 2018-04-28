package com.osapps.capitalslearner.dagger

import com.osapps.capitalslearner.dagger.scope.PerActivity
import com.osapps.capitalslearner.main.di.ListFragmentProvider
import com.osapps.capitalslearner.main.di.MainActivityModule
import com.osapps.capitalslearner.main.di.TabsManagerFragmentProvider
import com.osapps.capitalslearner.main.view.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * A module meant to:
 * 1) connect the application module with it's corresponding activities
 * 2) state which modules each app should use
 *
 * Every activity should be declared here, with at least one module.
 */
@Module
abstract class ActivityBuilder {


    //a simple one view activity which doesn't carry any fragments.


    /**
     * this is an example of activity which carries one fragment. Notice that in addition to the activity
     * module, we also included the specific fragment provider (which holds all of the fragment modules)
    */
    @PerActivity
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,  //the activity module
        ListFragmentProvider::class,
        TabsManagerFragmentProvider::class
    ])


    internal abstract fun bindMainActivity(): MainActivity


}
