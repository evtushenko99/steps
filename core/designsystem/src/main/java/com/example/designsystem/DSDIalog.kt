package com.example.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme

@Composable
fun StepsDialog(
    defaultValue: String,
    label: String,
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Dialog(onDismissRequest = { }) {
        var text by remember { mutableStateOf(defaultValue) }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.medium),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Paddings.medium)
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    label = { Text(label) },
                    keyboardOptions = keyboardOptions
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Paddings.xxxlarge,
                            top = Paddings.large,
                            bottom = Paddings.small
                        ),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .weight(1f),
                        contentPadding = PaddingValues.Absolute()
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            Text("Cancel", Modifier.align(Alignment.CenterEnd))
                        }
                    }
                    TextButton(
                        onClick = { onConfirmation(text) },
                        modifier = Modifier
                            .weight(1f),
                    ) {
                        Box(Modifier.fillMaxWidth()) {
                            Text("Save", Modifier.align(Alignment.CenterEnd))
                        }
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewResourceDialog() {
    StepsTheme {
        StepsDialog("70", "Дневная цель", {}, {})
    }
}