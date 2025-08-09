package com.jvmartinez.finanzapp.ui.base

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.devsapiens.finanzapp.R
import com.jvmartinez.finanzapp.component.button.ButtonBlackWithLetterWhite
import com.jvmartinez.finanzapp.component.button.ButtonTransparentBasic
import com.jvmartinez.finanzapp.component.image.ImageBasic
import com.jvmartinez.finanzapp.component.text.TextCustom
import com.jvmartinez.finanzapp.ui.theme.GREEN
import com.jvmartinez.finanzapp.ui.theme.GrayDark
import com.jvmartinez.finanzapp.ui.theme.HitGray
import com.jvmartinez.finanzapp.ui.theme.Margins
import com.jvmartinez.finanzapp.ui.theme.RedLight
import com.jvmartinez.finanzapp.ui.theme.bgBase

@Composable
fun CustomDialogBase(
    showDialog: Boolean,
    onDismissClick: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissClick() },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { }
                        }
                        .shadow(
                            Margins.Medium,
                            shape = RoundedCornerShape(Margins.Large)
                        )
                        .width(Margins.WidthMedium)
                        .clip(RoundedCornerShape(Margins.Large))
                        .background(
                            color = HitGray
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun DialogWithOneAction(
    @StringRes titleDialog: Int,
    @StringRes messageAlert: Int,
    onDismissClick: () -> Unit,
) {
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        var graphicVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) { graphicVisible = true }

        AnimatedVisibility(
            visible = graphicVisible,
            enter = expandVertically(
                animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
                expandFrom = Alignment.CenterVertically,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Margins.WidthXSmall)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                HitGray,
                                GrayDark,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                ImageBasic(resourceDrawable = R.drawable.ic_header_search)
            }
        }

        Column(
            modifier = Modifier.padding(Margins.Large)
        ) {
            Box(modifier = Modifier.height(Margins.Medium))
            TextCustom(title = stringResource(id = titleDialog), isBold = true)
            Box(modifier = Modifier.height(Margins.Medium))
            TextCustom(title = stringResource(id = messageAlert))
        }
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .padding(Margins.Medium)
                    .clip(RoundedCornerShape(Margins.Medium))
                    .clickable { onDismissClick() }
                    .weight(1f)
                    .padding(vertical = Margins.XLarge),
                contentAlignment = Alignment.Center
            ) {
                TextCustom(title = stringResource(id = R.string.title_button_accept))
            }
        }
    }
}

@Composable
fun DialogWithoutAction(
    @StringRes messageAlert: Int,
    @DrawableRes icon: Int = R.drawable.ic_warning_white_amber_24,
    colorBackground: Color = RedLight,
    colorText: Color = Color.White,
    showDialog: Boolean = true,
) {
    var dialogVisible by remember { mutableStateOf(showDialog) }
    Column(
        Modifier.background(MaterialTheme.colorScheme.surface)
    ) {
        AnimatedVisibility(
            visible = dialogVisible,
            enter = expandVertically(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                ),
                expandFrom = Alignment.CenterVertically,
            )
        ) {
            Row(
                modifier = Modifier
                    .background(colorBackground)
                    .height(Margins.WidthXMicro),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center,

                    ) {
                    ImageBasic(
                        resourceDrawable = icon,
                        modifier = Modifier.padding(horizontal = Margins.XMedium)
                    )
                }
                TextCustom(
                    title = stringResource(id = messageAlert),
                    textAlign = TextAlign.Center,
                    textColor = colorText,
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = Margins.Medium)
                        .padding(vertical = Margins.Large),
                    isBold = true
                )
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    ImageBasic(
                        modifier = Modifier
                            .padding(end = Margins.Medium)
                            .clickable { dialogVisible = false },
                        resourceDrawable = R.drawable.ic_close_white_24,
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewResetWarning() {
    CustomDialogBase(
        showDialog = true,
        onDismissClick = {},
        content = {
            DialogWithoutAction(
                messageAlert = R.string.copy_message_dialog_login,
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDatePicker(
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    state: DatePickerState,
    dateFormatter: DatePickerFormatter,
) {
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            ButtonBlackWithLetterWhite(
                title = stringResource(id = R.string.title_button_accept),
                action = { onAccept() },
                isEnabled = true
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
            showModeToggle = false,
            dateFormatter = dateFormatter,
            state = state,
            colors = DatePickerDefaults.colors(
                todayDateBorderColor = GREEN,
                dayInSelectionRangeContentColor = GrayDark,
                dayInSelectionRangeContainerColor = GrayDark,
                selectedDayContainerColor = GREEN,
                todayContentColor = GrayDark
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDatePicker(
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    stateRange: DateRangePickerState,
    dateFormatter: DatePickerFormatter,
) {
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            ButtonBlackWithLetterWhite(
                title = stringResource(id = R.string.title_button_accept),
                action = { onAccept() },
                isEnabled = true
            )
            ButtonTransparentBasic(
                title = stringResource(id = R.string.title_button_cancel),
                action = {
                    onDismiss()
                }
            )
        }
    ) {
        DateRangePicker(
            showModeToggle = true,
            dateFormatter = dateFormatter,
            state = stateRange,
            colors = DatePickerDefaults.colors(
                todayDateBorderColor = GREEN,
                dayInSelectionRangeContentColor = Color.White,
                dayInSelectionRangeContainerColor = bgBase,
                selectedDayContainerColor = GREEN,
                todayContentColor = GrayDark
            ),
            modifier = Modifier.weight(1f)
        )
    }
}