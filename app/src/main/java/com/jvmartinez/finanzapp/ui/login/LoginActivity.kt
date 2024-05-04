package com.jvmartinez.finanzapp.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.button.ButtonGoogle
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.theme.FinanzAppTheme
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanzAppTheme(
                darkTheme = false, dynamicColor = false
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenLogin(innerPadding, viewModel)
                }
            }
        }
    }
}

@Composable
fun ScreenLogin(innerPadding: PaddingValues, viewModel: LoginViewModel) {
    val email: String by viewModel.onEmail().observeAsState(initial = "")
    val password: String by viewModel.onPassword().observeAsState(initial = "")
    val toggleButton: Boolean by viewModel.onToggleButton().observeAsState(initial = false)
    LazyColumn(
        modifier = Modifier.padding(innerPadding)
    ) {
        item {
            ImageBasic(
                resourceDrawable = R.drawable.ic_rafiki,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Margins.Huge)
            )
        }
        item {
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
        item {
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
                    onValueChange = { viewModel.onChangedEmail(it) },
                    label = stringResource(id = R.string.copy_field_email),
                    placeholder = stringResource(id = R.string.copy_field_email),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                )
            }
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
                    onValueChange = { viewModel.onChangedPassword(it) },
                    label = stringResource(id = R.string.copy_field_password),
                    placeholder = stringResource(id = R.string.copy_field_password),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    isPassword = true
                )
            }
        }
        item {
            ButtonTransparentBasic(
                title = stringResource(id = R.string.forgot_password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margins.Large),
                textAlign = TextAlign.End,

            )
        }
        item {
            ButtonBlackWithLetterWhite(
                title = stringResource(id = R.string.copy_title_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.ExtraHuge),
                isEnabled = toggleButton
            )
        }
        item {
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
        item {
            ButtonGoogle(
                title = stringResource(id = R.string.copy_login_with_google),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.ExtraLarge)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanzAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ScreenLogin(innerPadding, LoginViewModel())
        }
    }
}