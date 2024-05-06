package com.jvmartinez.finanzapp.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.MissGrey
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ButtonTransparentBasic(
    title: String,
    modifier: Modifier? = Modifier,
    action: () -> Unit = { },
    textAlign: TextAlign? = null,
    textColor: Color? = null
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
                color = textColor ?: GrayLight,
                textAlign = textAlign ?: TextAlign.Center,
            )
        }
    }
}

@Composable
fun ButtonBlackWithLetterWhite(
    title: String,
    modifier: Modifier? = Modifier,
    action: () -> Unit = { },
    isEnabled: Boolean = false
) {
    Row {
        Button(
            onClick = {
                action()
            },
            enabled = isEnabled,
            modifier = (
                    modifier ?: Modifier
                        .background(Color.Transparent)
                    ),
            shape = RoundedCornerShape(Margins.XSmall),
            colors = ButtonDefaults.buttonColors(containerColor = (if(isEnabled)  Color.Black else  Color.LightGray))
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = TextSizes.Small,
                color = Color.White
            )
        }
    }
}

@Composable
fun ButtonGoogle(
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
            shape = RoundedCornerShape(Margins.XSmall),
            colors = ButtonDefaults.buttonColors(containerColor = MissGrey)

        ) {
            ImageBasic(
                R.drawable.ic_logo_google,
                modifier = Modifier
                    .size(Margins.Large)
                    .padding(end = Margins.Small)
            )
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                fontSize = TextSizes.Small,
                color = GrayDark,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewButtons() {
    Column {
        ButtonTransparentBasic(title = "Example")
        ButtonBlackWithLetterWhite(title = "Continue")
        ButtonGoogle(title = "Login with Google")
    }
}
