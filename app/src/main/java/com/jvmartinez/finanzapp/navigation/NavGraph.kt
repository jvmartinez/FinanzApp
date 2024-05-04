package com.jvmartinez.finanzapp.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvmartinez.finanzapp.ui.login.ScreenLogin
import com.jvmartinez.finanzapp.ui.signup.ScreenSignUp

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RouterScreen.LoginScreen.route
    ) {
        composable(
            route = RouterScreen.LoginScreen.route
        ) {
            ScreenLogin(navigateToSignUp = {
                navController.navigate(RouterScreen.SignUpScreen.route)
            })
        }
        composable(
            route = RouterScreen.SignUpScreen.route
        ) {
            ScreenSignUp(navigationBack = { navController.popBackStack() })
        }
    }
}
