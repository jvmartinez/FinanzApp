package com.jvmartinez.finanzapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvmartinez.finanzapp.ui.credential.ScreenLogin
import com.jvmartinez.finanzapp.ui.credential.ScreenSignUp
import com.jvmartinez.finanzapp.ui.home.ScreenHome
import com.jvmartinez.finanzapp.ui.income.IncomeAndOutComeScreen
import com.jvmartinez.finanzapp.ui.splash.ScreenSplash

@RequiresApi(Build.VERSION_CODES.O)
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
            ScreenHome(navigateToIncomeAndExpenses = {
                navController.navigate(RouterScreen.HomeToIncomeAndExpensesScreen.route)
            })
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
            ScreenHome(
                navigateToIncomeAndExpenses = {
                    navController.navigate(RouterScreen.HomeToIncomeAndExpensesScreen.route)
                }
            )
        }
        composable(
            route = RouterScreen.HomeToIncomeAndExpensesScreen.route
        ) {
            IncomeAndOutComeScreen(
                navigationBack = { navController.popBackStack() },
            )
        }
    }
}
