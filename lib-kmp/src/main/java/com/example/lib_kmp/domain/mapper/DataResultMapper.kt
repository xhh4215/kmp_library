package com.example.lib_kmp.domain.mapper
interface DtoMapper<DTO, Domain> {
    fun mapToDomain(dto: DTO): Domain

    fun mapToDomainList(dtoList: List<DTO>): List<Domain> =
        dtoList.map { mapToDomain(it) }
}

interface UiMapper<Domain, UI> {
    fun mapToUi(domain: Domain): UI

}


