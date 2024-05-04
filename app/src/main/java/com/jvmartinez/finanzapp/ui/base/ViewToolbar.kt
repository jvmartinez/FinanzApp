package com.jvmartinez.finanzapp.ui.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.MissGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewToolbar(
    title: String = "home",
    action: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = action,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MissGrey,
            titleContentColor = GrayDark,
        )
    )
}


@Preview
@Composable
fun ViewToolbarPreview() {
    ViewToolbar()
}