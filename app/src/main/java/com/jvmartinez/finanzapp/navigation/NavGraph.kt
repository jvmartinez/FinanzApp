package com.jvmartinez.finanzapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvmartinez.finanzapp.ui.credential.ScreenLogin
import com.jvmartinez.finanzapp.ui.credential.ScreenSignUp
import com.jvmartinez.finanzapp.ui.home.ScreenHome
import com.jvmartinez.finanzapp.ui.splash.ScreenSplash

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RouterScreen.SplashScreen.route
    ) {
        composable(
            route = RouterScreen.SplashScreen.route
        ) {
            ScreenSplash(
                navigationToLogin = {
                    navController.navigate(RouterScreen.SplashToLoginScreen.route)
                },
                navigationToHome = {
                    navController.navigate(RouterScreen.SplashToHomeScreen.route)
                }
            )
        }
        composable(
            route = RouterScreen.SplashToHomeScreen.route
        ) {
            ScreenHome()
        }

        composable(
            route = RouterScreen.SplashToLoginScreen.route
        ) {
            ScreenLogin(
                navigateToSignUp = {
                    navController.navigate(RouterScreen.LoginToSignUpScreen.route)
                },
                navigateToHome = {
                    navController.navigate(RouterScreen.LoginToHomeScreen.route)
                })
        }
        composable(
            route = RouterScreen.LoginToSignUpScreen.route
        ) {
            ScreenSignUp(
                navigationBack = { navController.popBackStack() },
                navigateToHome = {
                    navController.navigate(RouterScreen.LoginToHomeScreen.route)
                }
            )
        }
        composable(
            route = RouterScreen.LoginToHomeScreen.route
        ){
            ScreenHome()
        }
    }
}
