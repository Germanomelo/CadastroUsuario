package com.gmsp.desafiocadastro.domain.usecase

import com.gmsp.desafiocadastro.domain.model.AddresseeEnum
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.domain.model.ServiceType
import com.gmsp.desafiocadastro.domain.model.User

interface SendForwardUseCase {
    suspend operator fun invoke(from: User, to: AddresseeEnum, motive: String, serviceType: ServiceType): Forward
}