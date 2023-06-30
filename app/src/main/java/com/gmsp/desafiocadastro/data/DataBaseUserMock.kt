package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.User
import java.lang.Exception
import kotlin.coroutines.suspendCoroutine

class DataBaseUserMock: UserDataSource {

    override suspend fun createUser(user: User): User {
        return suspendCoroutine {
            try {
                // call db create user
                it.resumeWith(Result.success(user))
            } catch (e: Exception) {
                it.resumeWith(Result.failure(e))
            }
        }
    }
}
