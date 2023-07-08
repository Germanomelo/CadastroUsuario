package com.gmsp.desafiocadastro.domain.model

enum class AddresseeType(val value: String){
    CRAS("CRAS"),
    CREAS("CREAS"),
    PUBLIC_DEFENSE("Defensoria Pública"),
    PUBLIC_MINISTRY("Ministério Público"),
    JUDICIAL_POWER("Poder Judiciário");

    companion object {
        private val map = AddresseeType.values().associateBy(AddresseeType::value)
        fun fromString(AddresseeEnum: String) = map[AddresseeEnum]
    }
}