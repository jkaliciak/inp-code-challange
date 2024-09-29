package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.theme.Mercury
import pl.inpost.recruitmenttask.theme.Typography

@Composable
fun HeaderItem(
    isFirstItem: Boolean,
    @StringRes headerStringResId: Int,
    modifier: Modifier = Modifier,
) {
    val height = if (isFirstItem) 48.dp else 32.dp
    val paddingBottom = if (isFirstItem) 0.dp else 16.dp
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = paddingBottom)
                .height(1.dp)
                .background(color = Mercury)
        )
        Text(
            text = stringResource(id = headerStringResId),
            style = Typography.headlineSmall,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingBottom
                )
        )
    }
}