package com.thoughtworks.carapp.di

import android.content.Context
import com.thoughtworks.carapp.service.CarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCarService(@ApplicationContext context: Context): CarService {
        return CarService(context)
    }
}