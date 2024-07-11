package com.example.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSAppBar(
    titleText: String,
    navIconClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = titleText,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(
                        start = if (navIconClick == null) {
                            Paddings.medium
                        } else {
                            Paddings.zero
                        }
                    )
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (navIconClick != null) {
                IconButton(onClick = navIconClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStepsAppBar() {
    StepsTheme {
        Column {
            DSAppBar("Tree", {})
            DSAppBar("Tree")
        }

    }
}