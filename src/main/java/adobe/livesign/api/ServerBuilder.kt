package adobe.livesign.api

import io.undertow.Undertow
import io.undertow.UndertowOptions
import io.undertow.server.HttpHandler
import io.undertow.server.RoutingHandler
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.HttpString
import io.undertow.util.HttpString.tryFromString

interface ApiHandler {
    val contentType: String
    val method: HttpString
    val urlTemplate: String
    val httpHandler: HttpHandler
}

interface ApiController {
    fun apiHandlers(): List<ApiHandler>
}


class ServerBuilder(
        val appServerPort: Int,
        private val apiControllers: List<ApiController> = listOf(),
        private val apiHandlers: List<ApiHandler> = listOf()
) {
    init {
        if (apiControllers.flatMap { it.apiHandlers() }.isEmpty() && apiHandlers.isEmpty()) {
            throw IllegalArgumentException("No API handlers provided")
        }
    }

    fun build(): Undertow {
        val routingHandler = RoutingHandler()
        (apiControllers.flatMap { it.apiHandlers() } + apiHandlers).forEach {
            routingHandler.add(it.method, it.urlTemplate, it.httpHandler.wrapWithContentTypeHeader(it.contentType))
        }
        return Undertow.builder()
                .addHttpListener(appServerPort, "0.0.0.0")
                .setHandler(routingHandler
                        .wrapWithOptionsHandler()
                        .wrapWithCorsHeadersHandler()
                )
                .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, true)
                .build()
    }

    private fun HttpHandler.wrapWithCorsHeadersHandler(): HttpHandler {
        return HttpHandler {
            with(it.responseHeaders) {
                put(tryFromString("Access-Control-Allow-Origin"), "*")
                put(tryFromString("Access-Control-Allow-Credentials"), "true")
                put(tryFromString("Access-Control-Allow-Headers"), "Origin, X-Requested-With, Content-Type, Accept, Authorization, Cache-Control")
                put(tryFromString("Access-Control-Allow-Methods"), "GET, HEAD, POST, PUT, DELETE, OPTIONS")
                put(tryFromString("Access-Control-Max-Age"), "3600")
            }
            this.handleRequest(it)
        }
    }

    private fun HttpHandler.wrapWithOptionsHandler(): HttpHandler {
        return HttpHandler {
            if (it.requestMethod.toString().toUpperCase() == "OPTIONS") {
                it.statusCode = 204 // no content
                it.responseSender.send("")
            } else {
                this.handleRequest(it)
            }
        }
    }

    private fun HttpHandler.wrapWithContentTypeHeader(contentType: String): HttpHandler {
        return HttpHandler {
            with(it.responseHeaders) {
                put(CONTENT_TYPE, contentType)
            }
            this.handleRequest(it)
        }
    }

}
