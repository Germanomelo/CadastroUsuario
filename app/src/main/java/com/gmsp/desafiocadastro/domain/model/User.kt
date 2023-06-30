package com.gmsp.desafiocadastro.domain.model

import java.io.Serializable
import java.util.Date


data class User(
    val name: String,
    val cpf: String,
    val dateBirth: Date,
    val phone: String
):Serializable
