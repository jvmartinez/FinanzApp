package com.jvmartinez.finanzapp.component.textField

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.MissGrey
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Suppress("LongParameterList")
@Composable
fun TextFieldBasic(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    placeholder: String = "",
    modifier: Modifier,
    textCustom: TextFieldColors? = null,
    isPassword: Boolean = false
    ) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            TextCustom(
                title = label,
                textSize = TextSizes.Tiny
            )
        },
        placeholder = {
            TextCustom(
                title = placeholder
            )
        },
        modifier = modifier,
        textStyle = TextStyle(color = Color.Black),
        shape = RoundedCornerShape(Margins.Medium),
        maxLines = 1,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = textCustom ?: TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = MissGrey,
                focusedIndicatorColor = MissGrey,
                cursorColor = GrayDark
            )
    )
}
