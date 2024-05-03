package com.jvmartinez.finanzapp.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ImageBasic(@DrawableRes resourceDrawable: Int, modifier: Modifier = Modifier ) {
    Image(
        painter = painterResource(
            id = resourceDrawable
        ),
        contentDescription = null,
        modifier = modifier
    )
}