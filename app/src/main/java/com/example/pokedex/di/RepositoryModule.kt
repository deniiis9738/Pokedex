package com.example.pokedex.di

import com.example.pokedex.data.repositories.FallBackRepositoryImpl
import com.example.pokedex.domain.repositories.IPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(fallBackRepositoryImpl: FallBackRepositoryImpl): IPokemonRepository
}