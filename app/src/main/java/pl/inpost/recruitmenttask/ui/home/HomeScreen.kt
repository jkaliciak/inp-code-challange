package pl.inpost.recruitmenttask.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.ShipmentListScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    ShipmentListScreen(modifier = modifier)
}