package com.jvmartinez.finanzapp.ui.resetPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devsapiens.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.CustomDialogBase
import com.jvmartinez.finanzapp.ui.base.DialogWithOneAction
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ScreenResetPassword(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navigationBack: () -> Boolean = { false }
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animate))
    val state by viewModel.onStateIU().collectAsStateWithLifecycle()
    Scaffold {
        when (state.loadingData) {
            StatusData.Empty -> {
                ContentResetPassword(
                    it,
                    navigationBack,
                    state.email,
                    state.toggleButton,
                    { valueEmail ->
                        viewModel.changeInput(valueEmail)
                    },
                    {
                        viewModel.onResetPassword()
                    }
                )
            }

            is StatusData.Error -> {
                CustomDialogBase(
                    showDialog = true,
                    onDismissClick = {
                        viewModel.updateState(
                            loadingData = StatusData.Empty
                        )
                    },
                    content = {
                        DialogWithOneAction(
                            R.string.copy_reset_password,
                            R.string.copy_message_dialog_reset_password_error,
                        ) {
                            viewModel.updateState(
                                loadingData = StatusData.Empty
                            )
                        }
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

            is StatusData.Success -> {
                val status = (state.loadingData as StatusData.Success).data
                CustomDialogBase(
                    showDialog = true,
                    onDismissClick = {
                        viewModel.updateState(
                            loadingData = StatusData.Empty
                        )
                    },
                    content = {
                        DialogWithOneAction(
                            R.string.copy_reset_password,
                            if (status) {
                                R.string.copy_message_dialog_reset_password
                            } else {
                                R.string.copy_message_dialog_reset_password_error
                            },
                        ) {
                            viewModel.updateState(
                                loadingData = StatusData.Empty
                            )
                            if (status) {
                                navigationBack()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ContentResetPassword(
    paddingValues: PaddingValues,
    navigationBack: () -> Boolean,
    email: String,
    toggled: Boolean,
    changeInput: (String) -> Unit,
    action: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        ImageBasic(
            resourceDrawable = R.drawable.ic_arrow_back_24,
            modifier = Modifier
                .padding(top = Margins.Large)
                .padding(horizontal = Margins.Large)
                .clickable { navigationBack() }
        )
        ImageBasic(
            resourceDrawable = R.drawable.ic_logo_rest_password,
            modifier = Modifier
                .padding(top = Margins.ExtraHuge)
                .padding(horizontal = Margins.Large)
                .align(Alignment.CenterHorizontally)
        )
        TextCustom(
            title = stringResource(id = R.string.copy_reset_password),
            textSize = TextSizes.XMedium,
            isBold = true,
            modifier = Modifier
                .padding(vertical = Margins.Huge)
                .padding(horizontal = Margins.Large)
        )
        TextCustom(
            title = stringResource(id = R.string.copy_sub_title_reset_pasword),
            textSize = TextSizes.Standard,
            textColor = GrayDark,
            modifier = Modifier
                .padding(horizontal = Margins.Large)
        )
        ItemTextFieldEmailLogin(email = email) { value ->
            changeInput(value)
        }
        ItemButtonResetPassword(enable = toggled, action = action)
    }
}

@Composable
fun ItemTextFieldEmailLogin(email: String, onChangeEmail: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
            .padding(vertical = Margins.Huge)
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
fun ItemButtonResetPassword(enable: Boolean, action: () -> Unit = {}) {
    ButtonBlackWithLetterWhite(
        title = stringResource(R.string.copy_title_continue),
        isEnabled = enable,
        action = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Margins.Large)
    )
}
