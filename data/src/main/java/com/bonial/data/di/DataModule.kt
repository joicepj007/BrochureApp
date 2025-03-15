package com.bonial.data.di

import com.bonial.data.mapper.BrochureMapper
import com.bonial.data.remote.BrochureApiService
import com.bonial.data.remote.BrochureRemoteDataSource
import com.bonial.data.remote.BrochureRemoteDataSourceImpl
import com.bonial.data.repository.BrochureRepositoryImpl
import com.bonial.domain.repository.BrochureRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideBrochureMapper(): BrochureMapper {
        return BrochureMapper()
    }

    @Provides
    @Singleton
    fun provideBrochureRemoteDataSource(
        apiService: BrochureApiService,
        gson: Gson
    ): BrochureRemoteDataSource {
        return BrochureRemoteDataSourceImpl(apiService, gson)
    }

    @Provides
    @Singleton
    fun provideBrochureRepository(
        remoteDataSource: BrochureRemoteDataSource,
        mapper: BrochureMapper
    ): BrochureRepository {
        return BrochureRepositoryImpl(remoteDataSource, mapper)
    }
}