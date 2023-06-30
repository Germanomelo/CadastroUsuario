package com.gmsp.desafiocadastro.data

import com.gmsp.desafiocadastro.domain.model.Forward
import javax.inject.Inject

class ForwardRepository @Inject constructor(
    private val dataSource: ForwardDataSource
){
    suspend fun sendForward(forward: Forward): Forward = dataSource.sendForward(forward)
}