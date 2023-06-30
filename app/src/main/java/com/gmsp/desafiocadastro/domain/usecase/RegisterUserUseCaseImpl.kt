package com.gmsp.desafiocadastro.domain.usecase

import com.gmsp.desafiocadastro.data.UserRepository
import com.gmsp.desafiocadastro.domain.model.User
import java.lang.Exception
import java.util.Date
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : RegisterUserUseCase {

    override suspend fun invoke(name: String, cpf: String, dateBirth: Date, phone: String): User {
        return try {
            userRepository.createUser(User(name, cpf, dateBirth, phone))
        } catch (e: Exception) {
            throw e
        }
    }
}