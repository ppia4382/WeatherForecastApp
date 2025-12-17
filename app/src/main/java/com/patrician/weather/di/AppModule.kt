package com.patrician.weather.di

import android.content.Context
import androidx.room.Room
import com.patrician.weather.data.local.AppDatabase
import com.patrician.weather.data.local.dao.ForecastDao
import com.patrician.weather.data.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 依存関係を提供する Hilt モジュール。
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(appContext, AppDatabase::class.java, "forecast.db").build()

    @Provides
    fun provideForecastDao(db: AppDatabase): ForecastDao = db.forecastDao()

    @Provides
    @Singleton
    fun provideForecastRepository(dao: ForecastDao): ForecastRepository =
        ForecastRepository(dao)

}