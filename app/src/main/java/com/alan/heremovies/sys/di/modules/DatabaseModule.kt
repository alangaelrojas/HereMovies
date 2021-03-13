package com.alan.heremovies.sys.di.modules

import android.content.Context
import androidx.room.Room
import com.alan.heremovies.data.datasource.database.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): Database {
        return Room.databaseBuilder(context,
                Database::class.java, "database.db")
                .build()
    }
}