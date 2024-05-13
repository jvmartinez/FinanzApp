package com.jvmartinez.finanzapp.ui.credential

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.button.ButtonGoogle
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.CustomDialogBase
import com.jvmartinez.finanzapp.ui.base.DialogWithOneAction
import com.jvmartinez.finanzapp.ui.base.DialogWithoutAction
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.theme.AccentBlue
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ScreenLogin(
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: CredentialViewModel = hiltViewModel(),
) {
    val onLoading by viewModel.onLoadingData().observeAsState(initial = StatusData.Empty)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animate))
    val onValidPassword: Boolean by viewModel.onValidPassword().observeAsState(initial = false)
    val onValidEmail: Boolean by viewModel.onValidEmail().observeAsState(initial = false)

    Column {
        if (onValidPassword.not()) {
            DialogWithoutAction(
                R.string.copy_message_alert_password,
                colorBackground = GrayLight,
            )
        }
        if (onValidEmail.not()) {
            DialogWithoutAction(
                R.string.copy_message_alert_email,
                colorBackground = GrayLight,
                colorText = Color.White,
            )
        }
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (onLoading) {
                StatusData.Empty -> {
                    ContentLogin(innerPadding, navigateToSignUp, viewModel)
                }

                is StatusData.Error -> CustomDialogBase(
                    showDialog = true,
                    onDismissClick = { viewModel.onDismissDialog() },
                    content = {
                        DialogWithOneAction(
                            R.string.copy_title_dialog_login,
                            R.string.copy_message_dialog_login,
                        ) { viewModel.onDismissDialog() }
                    }
                )

                StatusData.Loading -> {
                    LottieAnimation(
                        composition = composition,
                        iterations = Int.MAX_VALUE,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is StatusData.Success -> navigateToHome()
            }
        }
    }
}

@Composable
fun ContentLogin(
    innerPadding: PaddingValues,
    navigateToSignUp: () -> Unit,
    viewModel: CredentialViewModel
) {
    val email: String by viewModel.onEmail().observeAsState(initial = "")
    val password: String by viewModel.onPassword().observeAsState(initial = "")
    val toggleButton: Boolean by viewModel.onToggleButton().observeAsState(initial = false)
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item {
            ItemLogo()
        }
        item {
            ItemTitle()
        }
        item {
            ItemTextFieldEmailLogin(email = email) {
                viewModel.onChanceTextFieldLogin(
                    it,
                    password
                )
            }
            ItemTextFieldPasswordLogin(password = password) {
                viewModel.onChanceTextFieldLogin(
                    email,
                    it
                )
            }
        }
        item {
            ItemForgotPassword()
        }
        item {
            ItemButtonLogin(toggleButton) { viewModel.onLogin() }
        }
        item {
            ItemSpacerOr()
        }
        item {
            ButtonGoogle(
                title = stringResource(id = R.string.copy_login_with_google),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.ExtraLarge)

            )
        }
        item {
            ButtonTransparentBasic(
                title = stringResource(id = R.string.copy_label_sign_up),
                action = { navigateToSignUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.Large)
                    .wrapContentHeight(
                        align = Alignment.CenterVertically
                    ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ItemSpacerOr() {
    Row(
        modifier = Modifier
            .padding(horizontal = Margins.Large)
            .padding(top = Margins.ExtraLarge)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ImageBasic(
            resourceDrawable = R.drawable.ic_line_horizontal,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        TextCustom(
            title = stringResource(id = R.string.copy_or),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = Margins.Medium)
        )
        ImageBasic(
            resourceDrawable = R.drawable.ic_line_horizontal,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ItemButtonLogin(
    toggleButton: Boolean, onAction: () -> Unit = {}
) {
    ButtonBlackWithLetterWhite(
        title = stringResource(id = R.string.copy_title_continue),
        action = onAction,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(top = Margins.ExtraHuge),
        isEnabled = toggleButton
    )
}

@Composable
fun ItemForgotPassword(action: () -> Unit = {}) {
    ButtonTransparentBasic(
        title = stringResource(id = R.string.forgot_password),
        action = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large),
        textAlign = TextAlign.End,
        textColor = AccentBlue

    )
}

@Composable
fun ItemTextFieldPasswordLogin(password: String, onChangePassword: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(vertical = Margins.XMedium)
    ) {
        ImageBasic(
            resourceDrawable = R.drawable.ic_pad_lock, modifier = Modifier.align(
                Alignment.CenterVertically
            )
        )
        TextFieldBasic(
            value = password,
            onValueChange = { onChangePassword(it) },
            label = stringResource(id = R.string.copy_field_password),
            placeholder = stringResource(id = R.string.copy_field_password),
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
            isPassword = true
        )
    }
}

@Composable
fun ItemTextFieldEmailLogin(email: String, onChangeEmail: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
    ) {
        ImageBasic(
            resourceDrawable = R.drawable.ic_icon_enchant, modifier = Modifier.align(
                Alignment.CenterVertically
            )
        )
        TextFieldBasic(
            value = email,
            onValueChange = { onChangeEmail(it) },
            label = stringResource(id = R.string.copy_field_email),
            placeholder = stringResource(id = R.string.copy_field_email),
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
        )
    }
}

@Composable
fun ItemTitle() {
    TextCustom(
        title = stringResource(id = R.string.copy_title_login),
        isBold = true,
        textSize = TextSizes.XMedium,
        modifier = Modifier
            .padding(
                top = Margins.Large, bottom = Margins.Huge
            )
            .padding(horizontal = Margins.Large)
            .fillMaxWidth()
    )
}

@Composable
fun ItemLogo() {
    ImageBasic(
        resourceDrawable = R.drawable.ic_rafiki,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Margins.Huge)
    )
}
