package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.Forward
import java.lang.Exception
import kotlin.coroutines.suspendCoroutine

class DataBaseForwardMock(): ForwardDataSource {

    override suspend fun sendForward(forward: Forward): Forward {
        return suspendCoroutine {
            try {
                // call db send forword
                it.resumeWith(Result.success(forward))
            } catch (e: Exception) {
                it.resumeWith(Result.failure(e))
            }
        }
    }
}
