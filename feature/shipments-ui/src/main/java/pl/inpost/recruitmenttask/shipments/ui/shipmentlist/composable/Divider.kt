package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import pl.inpost.recruitmenttask.theme.AppTheme
import pl.inpost.recruitmenttask.theme.Iron

@Composable
fun Divider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimensions.spacer16)
            .background(
                Brush.verticalGradient(
                    colors = listOf(AppTheme.colors.shadowStart, AppTheme.colors.shadowEnd),
                    endY = 15f
                )
            )
    )
}