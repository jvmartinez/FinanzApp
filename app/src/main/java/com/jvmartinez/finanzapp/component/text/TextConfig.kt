package com.jvmartinez.finanzapp.component.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

fun textStyle(
    isBold: Boolean = false,
    textSize: TextUnit = TextSizes.Standard,
    textColor: Color = Color.Black
    ): TextStyle = TextStyle(
    fontSize = textSize,
    color = textColor,
    fontFamily = FontFamily.SansSerif,
    fontWeight = (if (isBold) FontWeight.SemiBold else FontWeight.Normal)
)

@Composable
fun TextCustom(
    title: String,
    modifier: Modifier? = Modifier,
    isBold: Boolean = false,
    textColor: Color = Color.Black,
    textSize: TextUnit = TextSizes.Standard,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = title,
        style = textStyle(isBold,textSize,textColor),
        modifier = (
                modifier ?: Modifier
                    .fillMaxWidth()
                    .padding(Margins.Medium)
                ),
        textAlign = textAlign
    )
}


@Preview
@Composable
fun PreviewTexts() {
    TextCustom(
        title = "Example",
        isBold = true,
        textColor = Color.Green
    )
}