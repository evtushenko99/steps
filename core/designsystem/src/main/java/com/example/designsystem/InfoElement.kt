package com.example.designsystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Elevations
import com.example.designsystem.theme.Paddings
import com.example.designsystem.theme.StepsTheme
import com.example.utils.R

@Composable
fun InfoElementPrimary(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String? = null,
    chevronValue: String? = null,
    background: Color = MaterialTheme.colorScheme.primaryContainer,
    @DrawableRes iconRes: Int = R.drawable.ic_settings_24,
    iconBackground: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Elevations.medium
        )
    ) {
        Row(
            modifier = Modifier
                .padding(Paddings.medium)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = "",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBackground)
                    .fillMaxSize(),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            )
            Column(
                modifier = Modifier
                    .padding(start = Paddings.medium)
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
                if (!subTitle.isNullOrBlank()) {
                    Text(
                        text = subTitle,
                        modifier = Modifier
                            .padding(top = Paddings.xssmall),
                        color = textColor,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                }
            }

            if (chevronValue != null) {
                Text(
                    text = chevronValue,
                    modifier = Modifier
                        .padding(start = Paddings.medium),
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1
                )
            }
        }
    }
}

val DefaultInfoElementModifier = Modifier.padding(
    start = Paddings.medium,
    top = Paddings.small,
    end = Paddings.medium,
    bottom = Paddings.small
)

val InfoElementModifierTopBottom = Modifier.padding(
    top = Paddings.small,
    bottom = Paddings.small
)

val InfoElementModifierForDsCard = Modifier.padding(
    start = Paddings.xsmedium,
    top = Paddings.small,
    end = Paddings.xsmedium,
    bottom = Paddings.small
)

@Composable
fun InfoElementSecondary(modifier: Modifier = Modifier, title: String, subTitle: String) {
    InfoElementPrimary(
        modifier = modifier,
        title = title,
        subTitle = subTitle,
        background = MaterialTheme.colorScheme.secondaryContainer,
        iconRes = R.drawable.ic_local_fire_24,
        iconBackground = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary
    )
}

@Composable
fun InfoElementTertiary(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    iconRes: Int = R.drawable.ic_conversion_path_24
) {
    InfoElementPrimary(
        modifier = modifier,
        title = title,
        subTitle = subTitle,
        background = MaterialTheme.colorScheme.tertiaryContainer,
        iconRes = iconRes,
        iconBackground = MaterialTheme.colorScheme.tertiary,
        textColor = MaterialTheme.colorScheme.onTertiary
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewInformationElement() {
    StepsTheme {

        Column(Modifier.verticalScroll(rememberScrollState())) {
            InfoElementSecondary(DefaultInfoElementModifier, "0 kcal", "calorie burned")
            InfoElementTertiary(DefaultInfoElementModifier, "0.000 kg", "Carbon dioxide saved")
            InfoElementPrimary(
                modifier = DefaultInfoElementModifier,
                iconRes = R.drawable.ic_settings_24,
                title = "Дневная цель",
                chevronValue = "6000"
            )
            InfoElementPrimary(
                modifier = DefaultInfoElementModifier,
                iconRes = R.drawable.ic_settings_24,
                title = "Чувствительность",
                subTitle = "Чем выше точность, тем меньшие движения будут считать за шаги",
                chevronValue = "средний"
            )
            InfoElementPrimary(
                modifier = DefaultInfoElementModifier,
                title = stringResource(id = R.string.setting_more_title),
                subTitle = stringResource(id = R.string.setting_more_subtitle),
                iconRes = R.drawable.ic_settings_24
            ) { }
        }
    }
}