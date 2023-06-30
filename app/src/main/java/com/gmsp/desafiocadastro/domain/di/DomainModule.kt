package com.gmsp.desafiocadastro.domain.di

import com.gmsp.desafiocadastro.domain.usecase.RegisterUserUseCase
import com.gmsp.desafiocadastro.domain.usecase.RegisterUserUseCaseImpl
import com.gmsp.desafiocadastro.domain.usecase.SendForwardUseCase
import com.gmsp.desafiocadastro.domain.usecase.SendForwardUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindRegisterUserUseCase(useCase: RegisterUserUseCaseImpl): RegisterUserUseCase

    @Binds
    fun bindSendForwardUseCase(useCase: SendForwardUseCaseImpl): SendForwardUseCase
}