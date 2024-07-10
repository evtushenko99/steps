package com.example.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.Paddings

@Composable
fun DSRowWithRadioButton(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    buttonState: Boolean = false,
    onSwitchClick: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(Paddings.xmedium)
            .then(modifier),
    ) {


        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

            if (!subtitle.isNullOrBlank()) {
                Text(
                    text = subtitle,
                    modifier = modifier
                        .padding(top = Paddings.medium),
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(start = Paddings.medium)
        ) {

            Switch(
                checked = buttonState,
                onCheckedChange = {
                    onSwitchClick(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    DSRowWithRadioButton(title = "Is Enabled"){}
}

@Preview(showBackground = true)
@Composable
fun PreviewDesc() {
    DSRowWithRadioButton(
        title = "Is Enabled",
        subtitle = "How we use it and what do you get if enable or disable this switch"
    ) {}
}