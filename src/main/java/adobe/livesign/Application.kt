package adobe.livesign

import adobe.livesign.config.AppContext
import adobe.livesign.config.loadConfig
import mu.KotlinLogging
import java.net.SocketAddress
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger { }

fun main(args: Array<String>) {
    var address: SocketAddress? = null
    val bootTimeMillis = measureTimeMillis {
        val config = loadConfig()
        logger.info { "Config: $config" }
        val appContext = AppContext(config)
        address = appContext.start()
    }
    logger.info { "Started in $bootTimeMillis ms on $address" }
}
