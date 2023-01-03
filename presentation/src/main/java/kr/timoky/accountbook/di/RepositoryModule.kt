package kr.timoky.accountbook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.timoky.data.repository.dataSource.LocalDataSource
import kr.timoky.data.repository.repository.CategoryRepositoryImpl
import kr.timoky.data.repository.repository.ExpenseRepositoryImpl
import kr.timoky.domain.repository.CategoryRepository
import kr.timoky.domain.repository.ExpenseRepository
import kr.timoky.domain.utils.ErrorCallback
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideExpenseRepository(
        localDataSource: LocalDataSource,
        errorCallback: ErrorCallback
    ): ExpenseRepository = ExpenseRepositoryImpl(
        localDataSource,
        errorCallback
    )

    @Singleton
    @Provides
    fun provideCategoryRepository(
        localDataSource: LocalDataSource,
        errorCallback: ErrorCallback
    ): CategoryRepository = CategoryRepositoryImpl(
        localDataSource,
        errorCallback
    )
}