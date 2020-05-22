package adobe.livesign.config

import java.lang.System.getProperty
import java.lang.System.getenv


data class AppConfig(
        val serverPort: Int = getPropertyThenEnv("SERVER_PORT", "80").toInt(),
        val serviceName: String = getPropertyThenEnv("SERVICE_NAME", "adobe-esign-service-sample"),
        val dataFolder: String = getPropertyThenEnv("DATA_PATH", "data"),
        val adobeApiBaseUrl: String = "https://api.eu1.echosign.com/api/rest/v6/"
)

fun loadConfig(): AppConfig {
    return AppConfig()
}

private fun getPropertyThenEnv(propertyName: String): String {
    return getProperty(propertyName, getenv(propertyName))
}

private fun <T> getPropertyThenEnv(propertyName: String, existingPropertyParser: (String) -> T, defaultValue: T): T {
    val propertyValue = getProperty(propertyName, getenv(propertyName))
    return if (propertyValue != null) {
        existingPropertyParser(propertyValue)
    } else {
        defaultValue
    }
}

private fun getPropertyThenEnv(propertyName: String, defaultValue: String): String {
    return getProperty(propertyName, getenv(propertyName).orElse(defaultValue))
}

private fun String?.orElse(value: String) = this ?: value