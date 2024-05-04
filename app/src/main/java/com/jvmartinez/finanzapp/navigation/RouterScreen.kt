package com.jvmartinez.finanzapp.navigation

sealed class RouterScreen(
    val route: String
) {
    object HomeScreen : RouterScreen("toHome")
    object LoginScreen : RouterScreen("toLogin")
    object SignUpScreen : RouterScreen("toSignUp")


}