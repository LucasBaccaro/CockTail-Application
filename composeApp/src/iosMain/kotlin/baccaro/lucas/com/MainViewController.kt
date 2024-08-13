package baccaro.lucas.com

import androidx.compose.ui.window.ComposeUIViewController
import initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}