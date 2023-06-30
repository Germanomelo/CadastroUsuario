package com.gmsp.desafiocadastro.domain.model

data class Forward(
    val from: User?,
    var to: AddresseeEnum?,
    var motive: String?,
    val service: ServiceType?
)
