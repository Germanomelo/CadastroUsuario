package com.gmsp.desafiocadastro.domain.usecase

import com.gmsp.desafiocadastro.data.ForwardRepository
import com.gmsp.desafiocadastro.domain.model.AddresseeType
import com.gmsp.desafiocadastro.domain.model.Forward
import com.gmsp.desafiocadastro.domain.model.ServiceType
import com.gmsp.desafiocadastro.domain.model.User
import java.lang.Exception
import javax.inject.Inject

class SendForwardUseCaseImpl @Inject constructor(
    private val repository: ForwardRepository
) : SendForwardUseCase {

    override suspend fun invoke(from: User, to: AddresseeType, motive: String, serviceType: ServiceType): Forward {
        return try {
            repository.sendForward(Forward(from, to, motive, serviceType))
        } catch (e: Exception) {
            throw e
        }
    }

}