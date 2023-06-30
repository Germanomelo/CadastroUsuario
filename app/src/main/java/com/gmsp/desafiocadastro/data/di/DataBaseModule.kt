package com.gmsp.desafiocadastro.data.di

import com.gmsp.desafiocadastro.data.DataBaseForwardMock
import com.gmsp.desafiocadastro.data.DataBaseUserMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBaseUserMock(): DataBaseUserMock{
        return DataBaseUserMock()
    }

    @Singleton
    @Provides
    fun provideDataBaseForwardMock(): DataBaseForwardMock{
        return DataBaseForwardMock()
    }
}