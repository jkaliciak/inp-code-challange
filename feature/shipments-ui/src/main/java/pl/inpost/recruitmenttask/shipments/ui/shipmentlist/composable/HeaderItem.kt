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
import pl.inpost.recruitmenttask.theme.AppTheme

@Composable
fun HeaderItem(
    isFirstItem: Boolean,
    @StringRes headerStringResId: Int,
    modifier: Modifier = Modifier,
) {
    val height = if (isFirstItem) AppTheme.dimensions.spacer48 else AppTheme.dimensions.spacer32
    val paddingBottom =
        if (isFirstItem) AppTheme.dimensions.spacer0 else AppTheme.dimensions.spacer16
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
                .height(AppTheme.dimensions.spacer1)
                .background(color = AppTheme.colors.listHeaderLine)
        )
        Text(
            text = stringResource(id = headerStringResId),
            style = AppTheme.typography.h7,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(
                    start = AppTheme.dimensions.spacer16,
                    end = AppTheme.dimensions.spacer16,
                    bottom = paddingBottom
                )
        )
    }
}