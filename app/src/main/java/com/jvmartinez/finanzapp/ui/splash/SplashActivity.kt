package com.jvmartinez.finanzapp.ui.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devsapiens.finanzapp.R
import com.jvmartinez.finanzapp.ui.base.StatusData

@Preview
@Composable
fun ScreenSplash(
    viewModel: SplashViewModel = hiltViewModel(),
    navigationToLogin: () -> Unit = {},
    navigationToHome: () -> Unit = {}
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_lottie))
        val isLoaded: StatusData<Boolean> by viewModel.onStatusData().collectAsStateWithLifecycle()
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            when (isLoaded) {
                is StatusData.Loading -> {
                    LottieAnimation(
                        composition = composition,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }

                is StatusData.Error -> {
                    navigationToLogin()
                }

                is StatusData.Success -> {
                    if ((isLoaded as StatusData.Success<Boolean>).data) {
                        navigationToHome()
                    } else {
                        navigationToLogin()
                    }
                }

                StatusData.Empty -> {}
            }
        }
    }
}
