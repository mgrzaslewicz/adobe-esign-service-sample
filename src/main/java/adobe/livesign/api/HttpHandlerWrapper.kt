package adobe.livesign.api

import io.undertow.server.HttpHandler

interface HttpHandlerWrapper {
    fun wrap(next: HttpHandler): HttpHandler
}