package com.krevetka.linux

enum class Distro(
    val id: String,
    val displayName: String,
    val packageManager: String,
    val desktop: String,
    val description: String,
    val icon: String
) {
    DEBIAN("debian", "Debian", "apt", "XFCE", "Стабильная система для повседневных задач", "🐧"),
    ARCH("arch", "Arch Linux", "pacman", "XFCE", "Гибкая система со свежими пакетами", "🔷"),
    ALPINE("alpine", "Alpine Linux", "apk", "LXDE", "Компактная система с минимальной базой", "🏔️")
}
