package com.jvmartinez.finanzapp.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devsapiens.finanzapp.R
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.ui.base.ItemDatePicker
import com.jvmartinez.finanzapp.ui.home.items.ItemTransaction
import com.jvmartinez.finanzapp.ui.home.items.ItemTransactionEmpty
import com.jvmartinez.finanzapp.ui.theme.Margins
import java.util.Calendar

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenDetailTransaction(
    viewModel: DetailTransactionViewModel = hiltViewModel(),
    navigationBack: () -> Boolean = { false }
) {
    var showDialog by remember { mutableStateOf(false) }
    val transactions by viewModel.onTransaction().collectAsStateWithLifecycle()
    val dateStart by viewModel.onDateStart().collectAsStateWithLifecycle()
    val dateEnd by viewModel.onDateEnd().collectAsStateWithLifecycle()
    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Calendar.getInstance().timeInMillis,
        initialSelectedEndDateMillis = Calendar.getInstance().timeInMillis,
        initialDisplayMode = DisplayMode.Picker,
        yearRange = 2010..Calendar.getInstance().get(Calendar.YEAR),
    )
    val dateFormatter: DatePickerFormatter = remember {
        DatePickerDefaults.dateFormatter(
            "dd/MM/yy",
            "dd/MM/yy",
            "dd/MM/yy"
        )
    }
    Scaffold {
        Column (
            modifier = Modifier.padding(it)
        ){

            ImageBasic(
                resourceDrawable = R.drawable.ic_arrow_back,
                modifier = Modifier
                    .padding(vertical = Margins.Large)
                    .padding(horizontal = Margins.Large)
                    .clickable { navigationBack() }
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.Medium)
            ) {
                ImageBasic(
                    resourceDrawable = R.drawable.ic_edit_calendar_24,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                TextFieldBasic(
                    label = stringResource(id = R.string.copy_field_date_start),
                    placeholder = stringResource(id = R.string.copy_field_date_start),
                    value = dateStart,
                    onValueChange = { viewModel.setDateStart(state.selectedStartDateMillis ?: 0) },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .clickable { showDialog = true },
                    isEnable = false
                )
                ImageBasic(
                    resourceDrawable = R.drawable.ic_edit_calendar_24,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                TextFieldBasic(
                    label = stringResource(id = R.string.copy_field_date_end),
                    placeholder = stringResource(id = R.string.copy_field_date_end),
                    value = dateEnd,
                    onValueChange = { viewModel.setDateEnd(state.selectedEndDateMillis ?: 0) },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .clickable { showDialog = true },
                    isEnable = false

                )
            }
            LazyColumn {
                if (transactions.isEmpty()) {
                    item {
                        ItemTransactionEmpty()
                    }
                }
                items(transactions) {transaction ->
                    ItemTransaction(transactionView = transaction)
                }
            }
        }
        if (showDialog) {
            ItemDatePicker(
                onAccept = {
                    viewModel.setDateStart(state.selectedStartDateMillis ?: 0)
                    viewModel.setDateEnd(state.selectedEndDateMillis ?: 0)
                    viewModel.findTransactions()
                    showDialog = false
                },
                onDismiss = { showDialog = false },
                stateRange = state,
                dateFormatter = dateFormatter
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreenDetailTransaction() {
    ScreenDetailTransaction()
}