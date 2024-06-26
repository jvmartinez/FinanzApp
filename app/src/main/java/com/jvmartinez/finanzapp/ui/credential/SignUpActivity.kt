package com.jvmartinez.finanzapp.ui.credential

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.CustomDialogBase
import com.jvmartinez.finanzapp.ui.base.DialogWithOneAction
import com.jvmartinez.finanzapp.ui.base.DialogWithoutAction
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.base.ViewToolbar
import com.jvmartinez.finanzapp.ui.base.WebView
import com.jvmartinez.finanzapp.ui.theme.AccentBlue
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ScreenSignUp(
    navigationBack: () -> Boolean,
    navigateToHome: () -> Unit,
    viewModel: CredentialViewModel = hiltViewModel(),
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animate))
    val onLoading by viewModel.onLoadingData().observeAsState(initial = StatusData.Empty)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        when (onLoading) {
            StatusData.Empty -> ContentSignUp(innerPadding, viewModel, navigationBack)

            is StatusData.Error -> {
                CustomDialogBase(
                    showDialog = true,
                    onDismissClick = { viewModel.onDismissDialog() },
                    content = {
                        DialogWithOneAction(
                            R.string.copy_title_dialog_sign_up,
                            R.string.copy_message_dialog_generic,
                        ) { viewModel.onDismissDialog() }
                    }
                )
            }

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

@Composable
fun ContentSignUp(
    innerPadding: PaddingValues,
    viewModel: CredentialViewModel,
    navigationBack: () -> Boolean
) {
    val email by viewModel.onEmail().observeAsState(initial = "")
    val password by viewModel.onPassword().observeAsState(initial = "")
    val name by viewModel.onName().observeAsState(initial = "")
    val toggleButton: Boolean by viewModel.onToggleButton().observeAsState(initial = false)
    val onValidPassword: Boolean by viewModel.onValidPassword().observeAsState(initial = false)
    val onValidEmail: Boolean by viewModel.onValidEmail().observeAsState(initial = false)
    val onValidName: Boolean by viewModel.onValidName().observeAsState(initial = false)
    var showTermsAndCondition by remember { mutableStateOf(false) }
    val actionView: () -> Unit = {
        showTermsAndCondition = true
    }
    if (showTermsAndCondition) {
        WebView(url = "https://sites.google.com/view/finanz-app/p%C3%A1gina-principal")
    } else {
        Column {
            ImageBasic(
                resourceDrawable = R.drawable.ic_arrow_back_24,
                modifier = Modifier
                    .padding(top = Margins.Large)
                    .padding(horizontal = Margins.Large)
                    .clickable {
                        viewModel.onClearField()
                        navigationBack()
                    }
            )
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
            if (onValidName.not()) {
                DialogWithoutAction(
                    R.string.copy_message_alert_name,
                    colorBackground = GrayLight,
                    colorText = Color.White,
                )
            }
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    ItemLogoSignUp()
                }
                item {
                    ItemTitleSignUp()
                }
                item {
                    ItemTextFieldName(
                        name = name,
                        onChangeName = {
                            viewModel.onChanceTextFieldSignUp(
                                it,
                                email,
                                password
                            )
                        })
                    ItemTextFieldEmail(
                        email = email,
                        onChangeEmail = {
                            viewModel.onChanceTextFieldSignUp(
                                name,
                                it,
                                password
                            )
                        })
                    ItemTextFieldPassword(
                        password = password,
                        onChangePassword = {
                            viewModel.onChanceTextFieldSignUp(
                                name,
                                email,
                                it
                            )
                        })
                }
                item {
                    ItemTermsAndConditions(actionView)
                }
                item {
                    ItemButtonSignUp(
                        action = { viewModel.onSignUp() },
                        isEnabled = toggleButton
                    )
                }
            }
        }
    }
}

@Composable
fun ItemButtonSignUp(action: () -> Unit = {}, isEnabled: Boolean) {
    ButtonBlackWithLetterWhite(
        title = stringResource(id = R.string.copy_title_sign_up),
        action = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(top = Margins.ExtraHuge),
        isEnabled = isEnabled
    )
}

@Composable
fun ItemTermsAndConditions(actionView: () -> Unit) {
    TextCustom(
        title = stringResource(id = R.string.copy_terms_and_conditions),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(top = Margins.Huge)
            .clickable {
                actionView()
            },
        textColor = AccentBlue,
        textSize = TextSizes.Small,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ItemTextFieldPassword(password: String, onChangePassword: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(vertical = Margins.Small)
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
fun ItemTextFieldEmail(email: String, onChangeEmail: (String) -> Unit) {
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
fun ItemTextFieldName(name: String, onChangeName: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(vertical = Margins.Small)
    ) {
        ImageBasic(
            resourceDrawable = R.drawable.ic_contact, modifier = Modifier.align(
                Alignment.CenterVertically
            )
        )
        TextFieldBasic(
            value = name,
            onValueChange = { onChangeName(it) },
            label = stringResource(id = R.string.copy_field_name),
            placeholder = stringResource(id = R.string.copy_field_name),
            modifier = Modifier
                .weight(1f)
                .background(Color.White),
            isPassword = false
        )
    }
}

@Composable
fun ItemTitleSignUp() {
    TextCustom(
        title = stringResource(id = R.string.copy_title_sign_up),
        isBold = true,
        textSize = TextSizes.XMedium,
        modifier = Modifier
            .padding(top = Margins.ExtraHuge, bottom = Margins.Huge)
            .padding(horizontal = Margins.Large)
            .fillMaxWidth()
    )
}

@Composable
fun ItemLogoSignUp() {
    ImageBasic(
        resourceDrawable = R.drawable.ic_logo_sign_up,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Margins.Huge)
    )
}

