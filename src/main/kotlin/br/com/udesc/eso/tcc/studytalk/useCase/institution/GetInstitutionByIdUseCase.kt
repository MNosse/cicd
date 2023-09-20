package br.com.udesc.eso.tcc.studytalk.useCase.institution

import br.com.udesc.eso.tcc.studytalk.entity.administrator.exception.AdministratorNotFoundException
import br.com.udesc.eso.tcc.studytalk.entity.administrator.gateway.AdministratorGateway
import br.com.udesc.eso.tcc.studytalk.entity.institution.exception.InstitutionNotFoundException
import br.com.udesc.eso.tcc.studytalk.entity.institution.gateway.InstitutionGateway
import br.com.udesc.eso.tcc.studytalk.entity.institution.model.Institution
import org.springframework.stereotype.Service

@Service
class GetInstitutionByIdUseCase(
    private val administratorGateway: AdministratorGateway,
    private val institutionGateway: InstitutionGateway
) {
    @Throws(
        AdministratorNotFoundException::class,
        InstitutionNotFoundException::class,
    )
    fun execute(input: Input): Output {
        administratorGateway.getByUid(input.requestingUid)?.let {
            institutionGateway.getById(input.id)?.let {
                return Output(it)
            } ?: throw InstitutionNotFoundException()
        } ?: throw AdministratorNotFoundException()
    }

    data class Input(
        val requestingUid: String,
        val id: Long
    )

    data class Output(val institution: Institution)
}