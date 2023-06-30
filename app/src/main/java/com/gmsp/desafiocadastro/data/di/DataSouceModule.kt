package com.gmsp.desafiocadastro.data.di

import com.gmsp.desafiocadastro.data.DataBaseForwardMock
import com.gmsp.desafiocadastro.data.DataBaseUserMock
import com.gmsp.desafiocadastro.data.ForwardDataSource
import com.gmsp.desafiocadastro.data.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSouceModule {

    @Singleton
    @Binds
    fun bindUserDataSource(dataSource: DataBaseUserMock): UserDataSource

    @Singleton
    @Binds
    fun bindForwardDataSource(dataSource: DataBaseForwardMock): ForwardDataSource
}