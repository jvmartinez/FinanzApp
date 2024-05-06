package com.jvmartinez.finanzapp.ui.home

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.base.ViewToolbar
import com.jvmartinez.finanzapp.ui.home.items.ItemTitleWithButton
import com.jvmartinez.finanzapp.ui.home.items.ItemTransaction
import com.jvmartinez.finanzapp.ui.model.BalanceView
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.TextSizes

@Composable
fun ScreenHome(viewModel: HomeViewModel = hiltViewModel()) {
    viewModel.onBalance()
    val balanceView: BalanceView = viewModel.onBalance()
    Scaffold(
        topBar = { ViewToolbar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            CardBalance(balanceView?.balance.orEmpty())
            ScrollContent(balanceView)
        }
    }
}

@Composable
fun ScrollContent(balanceView: BalanceView?) {
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
                stringResource(id = R.string.copy_title_button),
                stringResource(id = R.string.copy_see_all)
            )
        }
        item {
            ItemTitleWithButton(
                stringResource(id = R.string.copy_title_transactions),
                stringResource(id = R.string.copy_see_all),
                action = { }
            )
        }
        items(balanceView?.transactions.orEmpty()) { transaction ->
            ItemTransaction(transactionView = transaction)
        }
    }
}

@Composable
fun CardBalance(balance: String) {
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
        CardItemBalanceFooter()
    }
}

@Composable
fun CardItemBalanceFooter() {
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
            Row {
                TextCustom(
                    title = stringResource(id = R.string.copy_my_wallet),
                    textColor = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(end = Margins.Medium),
                    textAlign = TextAlign.End
                )
                ImageBasic(
                    resourceDrawable = R.drawable.ic_bg_white_arrow_next,
                )
            }
        }
    }
}

@Composable
fun CardItemInformation(balance: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                        .padding(top = 4.dp)
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
            .height(100.dp)
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
