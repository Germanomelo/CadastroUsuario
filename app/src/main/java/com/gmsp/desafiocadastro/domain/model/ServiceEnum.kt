package com.gmsp.desafiocadastro.domain.model

enum class ServiceType(val value: String){
    FORWARD("Encaminhamento"),
    ACCOMPANY("Acompanhamento"),
    APPROACH_SOCIAL("Abordagem Social"),
    SHELTER("Acolhimento"),
    SCFV("SCFV");

    companion object {
        private val map = ServiceType.values().associateBy(ServiceType::value)
        fun fromString(ServiceType: String) = map[ServiceType]
    }
}