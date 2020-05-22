package adobe.livesign.api

import io.undertow.server.HttpHandler
import io.undertow.util.Methods.GET

class HealthHandler : ApiHandler {
    override val method = GET
    override val contentType = "application/json"
    override val urlTemplate = "/health"
    override val httpHandler = HttpHandler { serverExchange ->
        serverExchange.responseSender.send("""
            {
              "isHealthy": true
            }
        """.trimIndent())
    }
}