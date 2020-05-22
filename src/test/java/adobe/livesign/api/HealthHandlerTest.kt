package adobe.livesign.api

import adobe.livesign.config.AppConfig
import adobe.livesign.config.AppContext
import me.alexpanov.net.FreePortFinder
import okhttp3.OkHttpClient
import okhttp3.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class HealthHandlerTest {

    private fun getFreePort() = FreePortFinder.findFreeLocalPort()
    private val httpClientWithoutAuthorization = OkHttpClient()
    private val testAppConfig = AppConfig(
            serverPort = getFreePort()
    )

    private val appContext = AppContext(testAppConfig)
    private val server = appContext.server

    @Test
    fun shouldGetHealthResponse() {
        // given
        server.start()
        // when
        val request = Request.Builder()
                .url("http://localhost:${testAppConfig.serverPort}/health")
                .get()
                .build()
        val response = httpClientWithoutAuthorization.newCall(request).execute()
        // then
        response.use {
            val healthResponse = it.body?.string()
            assertThat(healthResponse).contains("\"isHealthy\": true")
        }
        server.stop()
    }
}