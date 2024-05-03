package com.jvmartinez.finanzapp.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ButtonTransparentBasic(
    title: String,
    modifier: Modifier? = Modifier,
    action: () -> Unit = { }
) {
    Row {
        Button(
            onClick = {
                action()
            },
            modifier = (
                    modifier ?: Modifier
                        .background(Color.Transparent)
                    ),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = TextSizes.Small,
                color = GrayLight
            )
        }
    }
}

@Preview
@Composable
fun PreviewButtons() {
    ButtonTransparentBasic(title = "Example")
}