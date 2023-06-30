package com.gmsp.desafiocadastro.domain.usecase

import com.gmsp.desafiocadastro.domain.model.User
import java.util.Date

interface RegisterUserUseCase {

    suspend operator fun invoke(name: String, cpf: String, dateBirth: Date, phone: String ): User
}