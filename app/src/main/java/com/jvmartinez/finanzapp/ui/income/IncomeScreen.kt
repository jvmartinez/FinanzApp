package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults.dateFormatter
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.ViewToolbar
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun IncomeAndOutComeScreen(
    viewModel: IncomeAndOutComeViewModel = hiltViewModel(),
    navigationBack: () -> Boolean = { false }
) {
    Scaffold(
        topBar = {
            ViewToolbar(
                title = stringResource(id = R.string.copy_title_income_and_expenses),
                action = {
                    IconButton(onClick = {
                        navigationBack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            FormIncomeAndOutComeScreen(viewModel)
            Content()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content() {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(3),
    ) {
        items(9) {
            ItemIncomeScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormIncomeAndOutComeScreen(viewModel: IncomeAndOutComeViewModel) {
    val data by viewModel.onDate().observeAsState(initial = "")
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFieldBasic(
            value = "",
            onValueChange = { },
            label = stringResource(id = R.string.copy_field_password),
            placeholder = stringResource(id = R.string.copy_field_password),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            isPassword = true
        )

        val state = rememberDatePickerState()
        var showDialog by remember { mutableStateOf(false) }
        val dateFormatter: DatePickerFormatter = remember { dateFormatter("dd/MM/yyyy") }
        if (showDialog) {
            ItemDatePicker(
                { showDialog = false },
                state,
                dateFormatter
            )
        }
        Row {
            TextFieldBasic(
                value = data,
                onValueChange = { },
                label = stringResource(id = R.string.copy_field_date),
                placeholder = stringResource(id = R.string.copy_field_date),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                isPassword = true
            )
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .background(Transparent)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(containerColor = GrayLight)
            ) {
                ImageBasic(resourceDrawable = R.drawable.ic_calendar)
            }
        }
        viewModel.processDate(state.selectedDateMillis)
    }
}

@Composable
fun ItemIncomeScreen() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .padding(Margins.Small),
            colors = CardDefaults.cardColors(Transparent)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(id = R.drawable.ic_box),
                        contentScale = ContentScale.FillBounds
                    )
            ) {
                Card(
                    modifier = Modifier
                        .padding(Margins.XMedium)
                        .align(Alignment.CenterHorizontally),
                    colors = CardDefaults.cardColors(Transparent)


                ) {
                    Box(
                        modifier = Modifier
                            .padding(Margins.XSmall)
                            .align(Alignment.CenterHorizontally)
                    ) {

                        ImageBasic(
                            modifier = Modifier
                                .padding(Margins.XSmall),
                            resourceDrawable = R.drawable.frame_8753
                        )
                        ImageBasic(
                            modifier = Modifier
                                .padding(Margins.XSmall)
                                .align(Alignment.Center),
                            resourceDrawable = R.drawable.ic_twotone_restaurant_24
                        )

                    }

                }
                TextCustom(
                    title = "Casa",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDatePicker(
    onDismiss: () -> Unit,
    state: DatePickerState,
    dateFormatter: DatePickerFormatter,
) {
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            ButtonBlackWithLetterWhite(
                title = stringResource(id = R.string.title_button_accept),
                action = { onDismiss() }
            )
            ButtonTransparentBasic(
                title = stringResource(id = R.string.title_button_cancel),
                action = {
                    onDismiss()
                }
            )
        }
    ) {
        DatePicker(
            dateFormatter = dateFormatter,
            state = state
        )
    }
}
