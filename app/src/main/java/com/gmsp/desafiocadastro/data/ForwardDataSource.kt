package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.Forward

interface ForwardDataSource {

    suspend fun sendForward(forward: Forward): Forward
}