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
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.model.TransactionView
import com.jvmartinez.finanzapp.ui.theme.GREEN
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.RedLight
import com.jvmartinez.finanzapp.ui.theme.bgBase

@Composable
fun ItemTransaction(transactionView: TransactionView) {
    val icon = transactionView.typeIcon
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
                .background(bgBase)
                .align(Alignment.CenterHorizontally)

        ) {
            Image(
                painter = painterResource(
                    id = if (icon > 0) icon else R.drawable.ic_positive
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.Small)
                    .padding(start = Margins.Medium)
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
                    textColor = Color.Black
                )
                TextCustom(
                    title = transactionView.amount,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = Margins.Large, start = Margins.Medium, end = Margins.Medium)
                        .fillMaxWidth(),
                    textColor = if (transactionView.type == 1) GREEN else RedLight
                )
            }
            TextCustom(
                title = transactionView.date,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.Small)
                    .padding(end = Margins.Medium),
                textColor = GrayDark
            )
        }
    }
}


@Composable
fun ItemTransactionEmpty() {

    Card(
        colors = CardDefaults.cardColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .height(Margins.HeightXMicro)
            .padding(top = Margins.Medium)
            .padding(horizontal = Margins.Large)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(bgBase)
                .align(Alignment.CenterHorizontally)
        ){
            ImageBasic(
                resourceDrawable = R.drawable.ic_blur_off_24,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Margins.Small)
                    .padding(start = Margins.Medium)

            )
            TextCustom(
                title = stringResource(id = R.string.copy_title_empty),
                modifier = Modifier
                    .padding(Margins.Small)
                    .padding(end = Margins.Medium)
                    .align(Alignment.CenterVertically),
                textColor = GrayDark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ItemTransactionPreview() {
    ItemTransaction(TransactionView(0.0, "Amount", "Date"))
    ItemTransactionEmpty()
}
