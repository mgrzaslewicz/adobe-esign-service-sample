package adobe.livesign.api

import adobe.livesign.agreement.AdobeAgreementRequest
import adobe.livesign.agreement.AdobeAgreementRequest.*
import adobe.livesign.agreement.AdobeAgreementsService
import com.fasterxml.jackson.databind.ObjectMapper
import io.undertow.server.HttpHandler
import io.undertow.util.Methods.POST
import mu.KLogging

data class AgreementRequest(
        val transientDocumentIds: List<String>,
        val recipientEmails: List<String>,
        val name: String,
        val redirectUrlAfterSignOff: String,
        val message: String,
        val authorizationHeader: String
)

class AgreementHandler(
        private val objectMapper: ObjectMapper,
        private val adobeAgreementsService: AdobeAgreementsService
) : ApiHandler {
    companion object : KLogging()

    override val method = POST
    override val contentType = "application/json"
    override val urlTemplate = "/agreement"
    override val httpHandler = HttpHandler { serverExchange ->
        serverExchange.requestReceiver.receiveFullString { _, message: String? ->
            val agreementsRequest = objectMapper.readValue(message, AgreementRequest::class.java)
            val adobeAgreementRequest = AdobeAgreementRequest.builder()
                    .participantSetInfo(
                            agreementsRequest.recipientEmails.mapIndexed { index, email ->
                                AdobeAgreementRequest.AdobeAgreementParticipant.builder()
                                        .memberInfos(listOf(AdobeAgreementMember(email)))
                                        .order(index + 1)
                                        .build()
                            }
                    )
                    .name(agreementsRequest.name)
                    .signatureType("ESIGN")
                    .state("IN_PROCESS")
                    .fileInfos(
                            agreementsRequest.transientDocumentIds.map { AdobeTransientDocument(it) }
                    )
                    .postSignOption(AdobePostSignOption(agreementsRequest.redirectUrlAfterSignOff))
                    .message(agreementsRequest.message)
                    .build()
            adobeAgreementsService.createAgreement(agreementsRequest.authorizationHeader, adobeAgreementRequest)
        }
        serverExchange.responseSender.send("")
    }
}