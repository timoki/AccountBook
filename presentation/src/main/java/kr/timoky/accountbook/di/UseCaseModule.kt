package kr.timoky.accountbook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kr.timoky.domain.repository.CategoryRepository
import kr.timoky.domain.repository.ExpenseRepository
import kr.timoky.domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetExpenseListUseCase(
        expenseRepository: ExpenseRepository
    ): GetExpenseListUseCase = GetExpenseListUseCase(
        expenseRepository
    )

    @Provides
    @ViewModelScoped
    fun provideGetExpenseUseCase(
        expenseRepository: ExpenseRepository
    ): GetExpenseUseCase = GetExpenseUseCase(
        expenseRepository
    )

    @Provides
    @ViewModelScoped
    fun provideInsertExpenseItemListUseCase(
        expenseRepository: ExpenseRepository
    ): InsertExpenseItemUseCase = InsertExpenseItemUseCase(
        expenseRepository
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteExpenseItemListUseCase(
        expenseRepository: ExpenseRepository
    ): DeleteExpenseItemUseCase = DeleteExpenseItemUseCase(
        expenseRepository
    )

    @Provides
    @ViewModelScoped
    fun provideGetCategoryListUseCase(
        categoryRepository: CategoryRepository
    ): GetCategoryListUseCase = GetCategoryListUseCase(
        categoryRepository
    )

    @Provides
    @ViewModelScoped
    fun provideGetCategoryUseCase(
        categoryRepository: CategoryRepository
    ): GetCategoryUseCase = GetCategoryUseCase(
        categoryRepository
    )

    @Provides
    @ViewModelScoped
    fun provideInsertCategoryUseCase(
        categoryRepository: CategoryRepository
    ): InsertCategoryUseCase = InsertCategoryUseCase(
        categoryRepository
    )

    @Provides
    @ViewModelScoped
    fun provideDeleteCategoryUseCase(
        categoryRepository: CategoryRepository
    ): DeleteCategoryUseCase = DeleteCategoryUseCase(
        categoryRepository
    )
}