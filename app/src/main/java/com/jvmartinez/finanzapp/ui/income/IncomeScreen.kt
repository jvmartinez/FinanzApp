package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
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
import com.jvmartinez.finanzapp.core.model.CategoryModel
import com.jvmartinez.finanzapp.ui.base.ViewToolbar
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.RedLight
import com.jvmartinez.finanzapp.utils.Utils

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
        ) {
            FormIncomeAndOutComeScreen(viewModel)
            Content(viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(viewModel: IncomeAndOutComeViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val categories by viewModel.getCategories().observeAsState(initial = listOf())
    if (categories.isEmpty()) {
        viewModel.setCategories(Utils.getListCategoryIncome(context))
    }
    Column {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Margins.Medium),
            columns = GridCells.Fixed(3),
        ) {
            items(categories) { categoryModel ->
                ItemIncomeScreen(
                    categoryModel
                ) { category ->
                    viewModel.setTpeTransaction(category)
                }
            }
        }
        Spacer(modifier = Modifier.height(Margins.Micro).background(GrayDark))
        ButtonBlackWithLetterWhite(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = Margins.Large)
                .padding(top = Margins.Large),
            title = stringResource(id = R.string.title_button_save),
            action = {
                viewModel.save()
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormIncomeAndOutComeScreen(viewModel: IncomeAndOutComeViewModel = hiltViewModel()) {
    val data by viewModel.onDate().observeAsState(initial = "")
    val description by viewModel.getDescription().observeAsState(initial = "")
    val amount by viewModel.getAmount().observeAsState(initial = 0.0)
    val date by viewModel.onDate().observeAsState(initial = "")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFieldBasic(
            value = "",
            onValueChange = { viewModel.onChangeSave(it, amount, date) },
            label = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.description),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            isPassword = false
        )

        TextFieldBasic(
            value = amount.toString(),
            onValueChange = { viewModel.onChangeSave(description, it.toDouble(), date) },
            label = stringResource(id = R.string.amount),
            placeholder = stringResource(id = R.string.amount),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            isPassword = false
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
                onValueChange = { viewModel.onChangeSave(description, amount, it) },
                label = stringResource(id = R.string.copy_field_date),
                placeholder = stringResource(id = R.string.copy_field_date),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                isPassword = false
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
fun ItemIncomeScreen(categoryModel: CategoryModel, selectCategory: (CategoryModel) -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .padding(Margins.Small)
            .clickable(onClick = { selectCategory(categoryModel) }),
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
                        resourceDrawable = categoryModel.iconDrawable
                    )

                }

            }
            TextCustom(
                title = categoryModel.name,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Margins.Medium)
                    .padding(bottom = Margins.XMicro)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Margins.Micro)
                    .padding(horizontal = Margins.Large)
                    .background(if (categoryModel.selected) RedLight else GrayDark)
            )
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
                action = { onDismiss() },
                isEnabled = true
            )
            ButtonTransparentBasic(
                title = stringResource(id = R.string.title_button_cancel),
                action = {
                    state.selectedDateMillis = null
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
