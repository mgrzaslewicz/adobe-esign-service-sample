package adobe.livesign.config

import adobe.livesign.agreement.AdobeAgreementsService
import adobe.livesign.api.AgreementHandler
import adobe.livesign.api.HealthHandler
import adobe.livesign.api.ServerBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import retrofit2.Retrofit
import java.net.SocketAddress


class AppContext(private val appConfig: AppConfig) {
    companion object : KLogging()

    val objectMapper = ObjectMapper()

    val healthHander = HealthHandler()

    val retrofit = Retrofit.Builder()
            .baseUrl(appConfig.adobeApiBaseUrl)
            .build()

    val adobeAgreementsService = retrofit.create(AdobeAgreementsService::class.java)
    val agreementsHandler = AgreementHandler(objectMapper, adobeAgreementsService)

    val server = ServerBuilder(
            appServerPort = appConfig.serverPort,
            apiHandlers = listOf(
                    healthHander,
                    agreementsHandler
            )
    ).build()


    fun start(): SocketAddress {
        logger.info { "Starting server" }
        server.start()
        return server.listenerInfo[0].address
    }

}