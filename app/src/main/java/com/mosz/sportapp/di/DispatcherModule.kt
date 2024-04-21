package com.mosz.sportapp.di

import com.mosz.sportapp.utils.DefaultDispatcherProvider
import com.mosz.sportapp.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DispatcherModule {
    @Provides
    fun provideDispatcher(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider =
        dispatcherProvider
}
