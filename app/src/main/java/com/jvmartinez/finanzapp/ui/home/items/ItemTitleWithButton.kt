package com.jvmartinez.finanzapp.ui.home.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ItemTitleWithButton(
    title: String,
    textButton: String,
    action: () -> Unit = { }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        TextCustom(
            title = title,
            textSize = TextSizes.Medium,
            isBold = true,
            modifier = Modifier
                .weight(1f)
                .padding(start = Margins.Large)
        )
        ButtonTransparentBasic(title = textButton, action = action)
    }
}