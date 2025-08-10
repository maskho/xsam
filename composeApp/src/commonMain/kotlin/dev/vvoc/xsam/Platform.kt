package dev.vvoc.xsam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform