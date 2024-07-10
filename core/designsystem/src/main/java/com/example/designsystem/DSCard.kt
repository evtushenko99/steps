package com.example.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.designsystem.theme.BorderWidth
import com.example.designsystem.theme.Elevations
import com.example.designsystem.theme.Paddings

@Composable
fun DSCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                start = Paddings.xsssmall,
                end = Paddings.xsssmall,
                top = Paddings.small,
                bottom = Paddings.small
            )
            .then(modifier),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevations.medium),
        border = BorderStroke(BorderWidth.small, MaterialTheme.colorScheme.onBackground)
    ) {
        content()
    }
}