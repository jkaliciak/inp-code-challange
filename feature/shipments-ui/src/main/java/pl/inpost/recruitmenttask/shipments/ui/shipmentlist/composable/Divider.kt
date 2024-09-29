package pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import pl.inpost.recruitmenttask.theme.Iron

@Composable
fun Divider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Iron, MaterialTheme.colorScheme.background),
                    endY = 15f
                )
            )
    )
}