package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.User

interface UserDataSource {

    suspend fun createUser(user: User): User
}