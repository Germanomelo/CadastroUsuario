package com.gmsp.desafiocadastro.domain.model

data class Forward(
    var from: User?,
    var to: AddresseeType?,
    var motive: String?,
    var service: ServiceType?
) {
   constructor() : this(null, null,null,null)
}
