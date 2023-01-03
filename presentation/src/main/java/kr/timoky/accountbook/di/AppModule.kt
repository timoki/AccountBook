package kr.timoky.accountbook.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.timoky.domain.utils.ErrorCallback
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideErrorCallback(): ErrorCallback {
        return ErrorCallback()
    }
}