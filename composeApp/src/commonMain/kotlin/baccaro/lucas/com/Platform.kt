package baccaro.lucas.com

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform