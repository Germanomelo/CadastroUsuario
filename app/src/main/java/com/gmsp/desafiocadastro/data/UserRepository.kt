package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: UserDataSource
){

    suspend fun createUser(user: User): User =
        dataSource.createUser(user)
}
