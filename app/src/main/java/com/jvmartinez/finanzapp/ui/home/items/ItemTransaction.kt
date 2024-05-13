package com.jvmartinez.finanzapp.ui.home.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.model.TransactionView
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.GrayLight
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.utils.Utils

@Composable
fun ItemTransaction(transactionView: TransactionView) {
    val context = LocalContext.current
    val icon = if (transactionView.type == 1) {
        Utils.getListCategoryIncome(context)
            .find { it.id == transactionView.typeIcon }?.iconDrawable
    } else {
        Utils.getListCategoryExpenses(context)
            .find { it.id == transactionView.typeIcon }?.iconDrawable
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(Margins.HeightXMicro)
            .padding(top = Margins.Medium)
            .padding(horizontal = Margins.Large)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(GrayLight)
                .align(Alignment.CenterHorizontally)

        ) {
            Image(
                painter = painterResource(
                    id = icon ?: R.drawable.ic_positive
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.Small)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                TextCustom(
                    title = transactionView.description,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = Margins.Small, start = Margins.Medium, end = Margins.Medium)
                        .fillMaxWidth(),
                    textColor = Color.White
                )
                TextCustom(
                    title = transactionView.amount,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = Margins.Large, start = Margins.Medium, end = Margins.Medium)
                        .fillMaxWidth(),
                    textColor = if (transactionView.type == 1) Color.Green else Color.Red
                )
            }
            TextCustom(
                title = transactionView.date,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.Small),
                textColor = GrayDark
            )
        }
    }
}


@Preview
@Composable
fun ItemTransactionPreview() {
    ItemTransaction(TransactionView(0.0, "Amount", "Date"))
}
