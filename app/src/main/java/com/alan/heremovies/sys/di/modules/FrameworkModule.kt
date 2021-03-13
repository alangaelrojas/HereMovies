package com.alan.heremovies.sys.di.modules

import com.alan.heremovies.data.datasource.database.Database
import com.alan.heremovies.data.datasource.database.dao.MoviesDao
import com.alan.heremovies.data.datasource.web.api.Services
import com.alan.heremovies.sys.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ContextModule::class, DatabaseModule::class])
class FrameworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constants.TIME_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIME_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.TIME_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://api.themoviedb.org/3/"
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providesServices(adapter: Retrofit): Services = adapter.create(Services::class.java)

    @Provides
    @Singleton
    fun providesMoviesDao(database: Database): MoviesDao {
        return database.moviesDao()
    }
}