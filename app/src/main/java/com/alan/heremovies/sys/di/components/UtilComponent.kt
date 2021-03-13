package com.alan.heremovies.sys.di.components

import android.content.Context
import com.alan.heremovies.sys.di.modules.ContextModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class])
interface UtilComponent {

    fun getContext(): Context

}