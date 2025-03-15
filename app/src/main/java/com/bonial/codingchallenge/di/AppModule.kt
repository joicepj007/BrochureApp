package com.bonial.codingchallenge.di

import com.bonial.domain.repository.BrochureRepository
import com.bonial.domain.usecase.FilterBrochuresByDistanceUseCase
import com.bonial.domain.usecase.GetBrochuresUseCase
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
    fun provideGetBrochuresUseCase(repository: BrochureRepository): GetBrochuresUseCase {
        return GetBrochuresUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFilterBrochuresByDistanceUseCase(): FilterBrochuresByDistanceUseCase {
        return FilterBrochuresByDistanceUseCase()
    }
}