package adobe.livesign

import java.lang.System.setProperty

/**
 * Copy this file to src/main and provide settings to run
 * Add limits when running process
-Xmx120M
-XX:+ExitOnOutOfMemoryError
-XX:+HeapDumpOnOutOfMemoryError
 */
fun main() {
    setProperty("SERVER_PORT", "15000")
    setProperty("SERVICE_NAME", "adobe-esign-service-sample")
    main(emptyArray())
}
