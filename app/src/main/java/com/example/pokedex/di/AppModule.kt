package com.example.pokedex.di

import android.app.Application
import com.example.pokedex.data.repositories.InfoPokemonRepository
import com.example.pokedex.data.repositories.PokemonListRepository
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
    fun providePokemonListRepository(): PokemonListRepository {
        return PokemonListRepository()
    }

    @Provides
    @Singleton
    fun provideInfoPokemonRepository(): InfoPokemonRepository {
        return InfoPokemonRepository(Application())
    }
}