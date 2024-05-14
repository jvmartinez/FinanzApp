package com.jvmartinez.finanzapp.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.core.model.Country
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
            colors = ButtonDefaults.buttonColors(containerColor = (if (isEnabled) Color.Black else Color.LightGray))
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetCountries(
    onDismiss: () -> Unit,
    onSelect: (Country) -> Unit,
    countries: List<Country>
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var textSearch by remember { mutableStateOf("") }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margins.Large)
                ) {
                    ImageBasic(
                        resourceDrawable = R.drawable.baseline_search_24, modifier = Modifier.align(
                            Alignment.CenterVertically
                        )
                    )
                    TextFieldBasic(
                        value = textSearch,
                        onValueChange = { textSearch = it  },
                        label = stringResource(id = R.string.copy_field_search),
                        placeholder = stringResource(id = R.string.copy_field_search),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White),
                    )
                }
            }
            items(
                countries.filter {
                    it.code.lowercase().contains(textSearch.lowercase()) || it.name.lowercase()
                        .contains(textSearch.lowercase()) || it.symbol.contains(textSearch) || textSearch == ""
                }
            ) {
                Button(
                    onClick = { onSelect(it) },
                    modifier = Modifier
                        .background(Color.Transparent),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Margins.Large, horizontal = Margins.Large)
                    ) {

                        TextCustom(
                            modifier = Modifier.weight(1f),
                            title = it.symbol
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TextCustom(
                            modifier = Modifier.weight(1f),
                            title = it.name
                        )
                    }
                }
            }
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
