package kr.timoky.accountbook.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.timoky.data.local.dao.CategoryDao
import kr.timoky.data.local.dao.ExpenseDao
import kr.timoky.data.local.database.MyDatabase
import kr.timoky.data.repository.dataSource.LocalDataSource
import kr.timoky.data.repository.dataSource.impl.LocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDatabase(
        @ApplicationContext context: Context
    ): MyDatabase = Room.databaseBuilder(
        context,
        MyDatabase::class.java,
        "account.db"
    ).fallbackToDestructiveMigration()
        .enableMultiInstanceInvalidation()
        .build()


    @Singleton
    @Provides
    fun provideExpenseDao(
        database: MyDatabase
    ): ExpenseDao = database.expenseDao()

    @Singleton
    @Provides
    fun provideCategoryDao(
        database: MyDatabase
    ): CategoryDao = database.categoryDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        expenseDao: ExpenseDao,
        categoryDao: CategoryDao
    ): LocalDataSource = LocalDataSourceImpl(
        expenseDao,
        categoryDao
    )
}