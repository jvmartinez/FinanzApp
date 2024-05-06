package com.jvmartinez.finanzapp.navigation

sealed class RouterScreen(
    val route: String
) {
    object LoginToSignUpScreen : RouterScreen("loginToSignUp")
    object LoginToHomeScreen : RouterScreen("loginToHome")
    object SplashScreen : RouterScreen("toSplash")
    object SplashToLoginScreen : RouterScreen("splashToLogin")
    object SplashToHomeScreen : RouterScreen("splashToHome")

}
