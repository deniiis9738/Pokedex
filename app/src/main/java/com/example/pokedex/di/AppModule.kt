package com.example.pokedex.di

import android.app.Application
import com.example.pokedex.data.repositories.ApìRepositoryImpl
import com.example.pokedex.data.repositories.JsonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApìRepositoryImpl(): ApìRepositoryImpl {
        return ApìRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideJsonRepositoryImpl(): JsonRepositoryImpl {
        return JsonRepositoryImpl(Application())
    }
}