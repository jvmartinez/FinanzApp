package com.jvmartinez.finanzapp.ui.home.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.ui.model.TransactionView

@Composable
fun ItemTransaction(transactionView: TransactionView) {
    Row {
        Image(
            painter = painterResource(
                id = R.drawable.ic_positive
            ),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Column {
            Text(
                text = transactionView.description
            )
            Text(
                text = transactionView.amount
            )
        }
        Text(
            text = transactionView.date,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun ItemTransactionPreview() {
    ItemTransaction(TransactionView("Description", "Amount", "Date"))
}
