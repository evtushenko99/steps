package com.example.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun InfoElementWithDialog(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    value: String,
    @DrawableRes iconRes: Int = com.example.utils.R.drawable.ic_settings_24,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    onConfirmation: (String) -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    InfoElementPrimary(
        modifier = modifier,
        iconRes = iconRes,
        title = title,
        subTitle = subTitle,
        chevronValue = value,
        onClick = { openDialog = true }
    )

    if (openDialog) {
        StepsDialog(
            defaultValue = value,
            label = title,
            onDismissRequest = { openDialog = false },
            onConfirmation = {
                onConfirmation.invoke(it)
                openDialog = false
            },
            keyboardOptions = keyboardOptions
        )
    }
}