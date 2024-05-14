package com.jvmartinez.finanzapp.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.button.BottomSheetCountries
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.base.CustomDialogBase
import com.jvmartinez.finanzapp.ui.base.DialogWithOneAction
import com.jvmartinez.finanzapp.ui.base.StatusData
import com.jvmartinez.finanzapp.ui.home.items.ItemTitleWithButton
import com.jvmartinez.finanzapp.ui.home.items.ItemTransaction
import com.jvmartinez.finanzapp.ui.home.items.ItemTransactionEmpty
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenHome(
    navigateToIncomeAndExpenses: () -> Unit = {},
    navigateToDetails: () -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val onBalanceView by viewModel.onLoadingData().observeAsState(initial = StatusData.Empty)
    var showSheet by remember { mutableStateOf(false) }
    val currentCountry by viewModel.getCurrencyKey().observeAsState()
    if (onBalanceView is StatusData.Empty) {
        viewModel.getBalance()
    }
    Scaffold { innerPadding ->
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animate))
        when (onBalanceView) {
            is StatusData.Error -> CustomDialogBase(
                showDialog = true,
                onDismissClick = { viewModel.onDismissDialog() },
                content = {
                    DialogWithOneAction(
                        R.string.copy_title_dialog_error,
                        R.string.copy_message_dialog_generic,
                    ) { viewModel.onDismissDialog() }
                }
            )

            StatusData.Empty, StatusData.Loading -> LottieAnimation(
                composition = composition,
                iterations = Int.MAX_VALUE,
                modifier = Modifier.fillMaxSize()
            )

            is StatusData.Success -> {
                val balanceView = (onBalanceView as StatusData.Success).data
                Column(modifier = Modifier.padding(innerPadding)) {
                    CardBalance(
                        balanceView.balance.orEmpty(),
                        currentCountry = currentCountry?.first.orEmpty()
                    ) { showSheet = true }
                    ScrollContent(
                        balanceView,
                        navigateToIncomeAndExpenses
                    ) {
                        navigateToDetails()
                    }
                }
            }
        }

        if (showSheet) {
            val context = LocalContext.current
            BottomSheetCountries(
                onDismiss = { showSheet = false },
                onSelect = {
                    viewModel.onSelectedCountry(it)
                    viewModel.getBalance()
                    showSheet = false
                },
                countries = viewModel.getCountries(context)?.countries.orEmpty()
            )
        }
    }
}

@Composable
fun ScrollContent(
    balanceView: BalanceView?,
    navigateToIncomeAndExpenses: () -> Unit,
    showTransaction: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Margins.Medium)
    ) {
        item {
            SubCardBalance(balanceView)
        }
        item {
            ItemTitleWithButton(
                stringResource(id = R.string.copy_title_income_outcome),
                stringResource(id = R.string.copy_title_button_here),
                action = { navigateToIncomeAndExpenses() }
            )
        }
        item {
            ItemTitleWithButton(
                stringResource(id = R.string.copy_title_transactions),
                stringResource(id = R.string.copy_see_all),
                action = {
                    showTransaction()
                }
            )
        }
        if (balanceView?.transactions.orEmpty().isEmpty()) {
            item {
                ItemTransactionEmpty()
            }
        }
        items(balanceView?.transactions.orEmpty()) { transaction ->
            ItemTransaction(transactionView = transaction)
        }
    }
}

@Composable
fun CardBalance(balance: String, currentCountry: String, showSheet: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { }
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
            .background(color = Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CardItemIconTopEnd()
            CardItemInformation(balance)
        }
        CardItemBalanceFooter(showSheet = showSheet, currentCountry = currentCountry)
    }
}

@Composable
fun CardItemBalanceFooter(showSheet: () -> Unit, currentCountry: String) {
    Box(
        contentAlignment = Alignment.BottomStart,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box {
            Image(
                contentScale = ContentScale.None,
                painter = painterResource(id = R.drawable.ic_semi_cicle),
                contentDescription = null,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.Transparent),
            ) {
                TextCustom(
                    title = stringResource(id = R.string.copy_my_currency),
                    textColor = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(end = Margins.Medium),
                    textAlign = TextAlign.End
                )
                Card(
                    shape = RoundedCornerShape(Margins.Medium),
                    elevation = CardDefaults.cardElevation(defaultElevation = Margins.Medium),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .height(Margins.Huge)
                        .background(color = Color.Transparent),
                    colors = CardDefaults.cardColors(GrayDark),

                    ) {
                    ButtonTransparentBasic(
                        title = currentCountry,
                        action = {
                            showSheet()
                        },
                        textColor = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(color = Color.Transparent),
                    )
                }
            }
        }
    }
}

@Composable
fun CardItemInformation(balance: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Margins.Large)
    ) {
        LazyColumn {
            item {
                TextCustom(
                    title = stringResource(id = R.string.copy_title_balance),
                    textColor = Color.White
                )
            }
            item {
                TextCustom(
                    title = balance,
                    textColor = Color.White,
                    textSize = TextSizes.Massive,
                    isBold = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Margins.Medium)
                )
            }
        }
    }
}

@Composable
fun CardItemIconTopEnd() {
    Box {
        Image(
            contentScale = ContentScale.None,
            painter = painterResource(id = R.drawable.ic_group_colors),
            contentDescription = null,
        )
    }
}

@Composable
fun SubCardBalance(balanceView: BalanceView?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Margins.HeightSmall)
            .clickable { }
            .padding(start = Margins.Large, end = Margins.Large)
            .background(color = Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SubCardItemIconTopStart()
            SubCardItemInformation(balanceView)
            SubCardItemIconBottomEnd()
        }
    }
}

@Composable
fun SubCardItemIconBottomEnd() {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box {
            ImageBasic(resourceDrawable = R.drawable.ic_semi_cicle_2)
        }
    }
}

@Composable
fun SubCardItemInformation(balanceView: BalanceView?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Margins.Large),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            item { ImageBasic(R.drawable.ic_positive) }
            item {
                Column(modifier = Modifier.padding(horizontal = Margins.Large)) {
                    TextCustom(title = "Income", textColor = Color.White)
                    TextCustom(
                        title = balanceView?.income.orEmpty(),
                        textColor = Color.White,
                        textSize = TextSizes.XMedium,
                        isBold = true
                    )
                }
            }
            item {
                ImageBasic(
                    resourceDrawable = R.drawable.ic_line,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Margins.Medium)
                )
            }
            item {
                ImageBasic(
                    R.drawable.ic_negative, modifier = Modifier.padding(
                        start = Margins.Large
                    )
                )
            }
            item {
                Column(
                    modifier = Modifier.padding(
                        horizontal = Margins.Medium
                    )
                ) {
                    TextCustom(title = "Outcome", textColor = Color.White)
                    TextCustom(
                        title = balanceView?.outcome.orEmpty(),
                        textColor = Color.White,
                        textSize = TextSizes.XMedium,
                        isBold = true
                    )
                }
            }
        }
    }
}

@Composable
fun SubCardItemIconTopStart() {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box {
            ImageBasic(resourceDrawable = R.drawable.ic_semi_cicle_top)
        }
    }
}
