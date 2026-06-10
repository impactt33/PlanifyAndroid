package com.example.planify.main.features.favorites.di

import com.example.planify.main.features.favorites.data.repositories_impl.FavoritesRepositoryImpl
import com.example.planify.main.features.favorites.domain.repositories.FavoritesRepository
import com.example.planify.main.features.favorites.domain.services.FavoritesService
import com.example.planify.main.features.favorites.domain.services_impl.FavoritesServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesFeatureBindingModule {
    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesService(impl: FavoritesServiceImpl): FavoritesService
}
