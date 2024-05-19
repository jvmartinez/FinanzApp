package com.jvmartinez.finanzapp.ui.income

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jvmartinez.finanzapp.R
import com.jvmartinez.finanzapp.component.text.TextCustom

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabScreen(
    viewModel: IncomeAndOutComeViewModel = hiltViewModel(),
    navigationBack: () -> Unit = { }
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(stringResource(id = R.string.income), stringResource(id = R.string.out_come))

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { TextCustom(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(
                                imageVector = Icons.TwoTone.Add,
                                contentDescription = null
                            )
                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> IncomeScreen(viewModel, navigationBack)
            1 -> OutcomeScreen(viewModel, navigationBack)
        }
    }
}
