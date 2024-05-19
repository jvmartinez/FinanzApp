package com.jvmartinez.finanzapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jvmartinez.finanzapp.ui.credential.ScreenLogin
import com.jvmartinez.finanzapp.ui.credential.ScreenSignUp
import com.jvmartinez.finanzapp.ui.detail.ScreenDetailTransaction
import com.jvmartinez.finanzapp.ui.home.ScreenHome
import com.jvmartinez.finanzapp.ui.income.TabScreen
import com.jvmartinez.finanzapp.ui.resetPassword.ScreenResetPassword
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
            }, navigateToDetails = {
                navController.navigate(RouterScreen.HomeToDetailsScreen.route)
            }, navigateToLogin = {
                navController.navigate(RouterScreen.HomeToLogin.route)
            }
            )
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
                }, navigateToResetPassword = {
                    navController.navigate(RouterScreen.ResetScreen.route)
                }
            )
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
                },
                navigateToDetails = {
                    navController.navigate(RouterScreen.HomeToDetailsScreen.route)
                },
                navigateToLogin = {
                    navController.navigate(RouterScreen.HomeToLogin.route)
                }
            )
        }
        composable(
            route = RouterScreen.HomeToIncomeAndExpensesScreen.route
        ) {
            TabScreen(
                navigationBack = {
                    navController.navigate( RouterScreen.LoginToHomeScreen.route)
                },
            )
        }
        composable(
            route = RouterScreen.HomeToDetailsScreen.route
        ) {
            ScreenDetailTransaction(
                navigationBack = { navController.popBackStack() },
            )
        }
        composable(
            route = RouterScreen.HomeToLogin.route
        ) {
            ScreenLogin(
                navigateToSignUp = {
                    navController.navigate(RouterScreen.LoginToSignUpScreen.route)
                },
                navigateToHome = {
                    navController.navigate(RouterScreen.LoginToHomeScreen.route)
                },
                navigateToResetPassword = {
                    navController.navigate(RouterScreen.ResetScreen.route)
                }
            )
        }
        composable(
            route = RouterScreen.ResetScreen.route
        ) {
            ScreenResetPassword(
                navigationBack = { navController.popBackStack() },
            )
        }
    }
}
