package com.jvmartinez.finanzapp.ui.signup

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.ViewToolbar
import com.jvmartinez.finanzapp.ui.theme.AccentBlue
import com.jvmartinez.finanzapp.ui.theme.FinanzAppTheme
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes


@Composable
fun ScreenSignUp(navigationBack: () -> Boolean) {
    Scaffold(
        topBar = {
            ViewToolbar(
                title = stringResource(id = R.string.copy_title_sign_up),
                action = {
                    IconButton(onClick = { navigationBack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                ImageBasic(
                    resourceDrawable = R.drawable.ic_logo_sign_up,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Margins.Huge)
                )
            }
            item {
                TextCustom(
                    title = stringResource(id = R.string.copy_title_sign_up),
                    isBold = true,
                    textSize = TextSizes.XMedium,
                    modifier = Modifier
                        .padding(
                            top = Margins.ExtraHuge, bottom = Margins.Huge
                        )
                        .padding(horizontal = Margins.Large)
                        .fillMaxWidth()
                )
            }
            item {
                val email: String = ""
                val password: String = ""
                val name: String = ""

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
                        onValueChange = { },
                        label = stringResource(id = R.string.copy_field_password),
                        placeholder = stringResource(id = R.string.copy_field_password),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White),
                        isPassword = true
                    )
                }

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
                        onValueChange = { },
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
                        .padding(vertical = Margins.Small)
                ) {
                    ImageBasic(
                        resourceDrawable = R.drawable.ic_pad_lock, modifier = Modifier.align(
                            Alignment.CenterVertically
                        )
                    )
                    TextFieldBasic(
                        value = password,
                        onValueChange = { },
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
                TextCustom(
                    title = stringResource(id = R.string.copy_terms_and_conditions),
                    modifier = Modifier
                        .padding(horizontal = Margins.Large)
                        .padding(top = Margins.Huge),
                    textColor = AccentBlue
                )
            }
            item {
                ButtonBlackWithLetterWhite(
                    title = stringResource(id = R.string.copy_title_sign_up),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margins.Large)
                        .padding(top = Margins.ExtraHuge)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenSignUpPreview() {
    FinanzAppTheme {
        ScreenSignUp({true})
    }
}