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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults.dateFormatter
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.component.textField.TextFieldBasic
import com.jvmartinez.finanzapp.core.model.CategoryModel
import com.jvmartinez.finanzapp.ui.base.ItemDatePicker
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.RedLight
import com.jvmartinez.finanzapp.utils.Utils
import java.time.LocalDate
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OutcomeScreen(
    viewModel: IncomeAndOutComeViewModel = hiltViewModel(),
    navigationBack: () -> Boolean = { false },
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            viewModel.clear()
            FormOutComeScreen(viewModel)
            ContentOutCome(viewModel, navigationBack)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentOutCome(
    viewModel: IncomeAndOutComeViewModel,
    navigationBack: () -> Boolean
) {
    val context = LocalContext.current
    val categories by viewModel.getOutComeCategories().observeAsState(initial = listOf())
    val enableButton by viewModel.onEnableButtonIncome().observeAsState(initial = false)
    if (categories.isEmpty()) {
        viewModel.setOutComeCategories(Utils.getListCategoryExpenses(context))
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Margins.Medium)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(Margins.Micro)
                    .background(GrayDark)
            )
        }
        item {
            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 3)
            FlowRow {
                categories.forEach {
                    ItemOutScreen(
                        it,
                        itemSize,
                    ) { category ->
                        viewModel.setTpeTransaction(category, 2)
                    }
                }
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .height(Margins.Micro)
                    .background(GrayDark)
            )
            ButtonBlackWithLetterWhite(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.Large),
                title = stringResource(id = R.string.title_button_save),
                action = {
                    viewModel.save(2)
                },
                isEnabled = enableButton
            )
            ButtonTransparentBasic(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = Margins.Large)
                    .padding(top = Margins.Large),
                title = stringResource(id = R.string.title_button_back),
                action = {
                    navigationBack()
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormOutComeScreen(viewModel: IncomeAndOutComeViewModel) {
    val date by viewModel.onDate().observeAsState(initial = "")
    val description by viewModel.getDescription().observeAsState(initial = "")
    val amount by viewModel.getAmount().observeAsState(initial = "")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextFieldBasic(
            value = description,
            onValueChange = { viewModel.onChangeSave(it, amount) },
            label = stringResource(id = R.string.description),
            placeholder = stringResource(id = R.string.description),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            isPassword = false
        )

        TextFieldBasic(
            value = amount,
            onValueChange = { viewModel.onChangeSave(description, it) },
            label = stringResource(id = R.string.amount),
            placeholder = stringResource(id = R.string.amount),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            isPassword = false,
            isOnlyNumber = true
        )

        val state = rememberDatePickerState(
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }

                override fun isSelectableYear(year: Int): Boolean {
                    return year <= LocalDate.now().year
                }
            },
            initialSelectedDateMillis = Calendar.getInstance().timeInMillis
        )
        var showDialog by remember { mutableStateOf(false) }
        val dateFormatter: DatePickerFormatter = remember { dateFormatter("dd/MM/yyyy") }
        if (showDialog) {
            ItemDatePicker(
                {
                    showDialog = false
                    viewModel.processDate(state.selectedDateMillis)
                },
                { showDialog = false },
                state,
                dateFormatter
            )
        }
        Row {
            TextFieldBasic(
                value = date,
                onValueChange = { viewModel.onChangeSave(description, amount) },
                label = stringResource(id = R.string.copy_field_date),
                placeholder = stringResource(id = R.string.copy_field_date),
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White),
                isPassword = false,
                isEnable = false
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
    }
}

@Composable
fun ItemOutScreen(
    categoryModel: CategoryModel,
    itemSize: Dp,
    selectCategory: (CategoryModel) -> Unit
) {
    Card(
        modifier = Modifier
            .size(itemSize)
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
