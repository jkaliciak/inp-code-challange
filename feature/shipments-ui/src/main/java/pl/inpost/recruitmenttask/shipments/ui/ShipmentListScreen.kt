package pl.inpost.recruitmenttask.shipments.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentUI

@Composable
fun ShipmentListScreen(modifier: Modifier = Modifier) {
    val viewModel: ShipmentListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ShipmentList(modifier, uiState)
}

@Composable
private fun ShipmentList(
    modifier: Modifier,
    uiState: ShipmentListViewModel.UiState,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            items = uiState.shipments,
            key = { item: ShipmentUI -> item.number },
        ) { item: ShipmentUI ->
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "PARCEL NUMBER")
                Text(text = item.number, color = Color.Black)
            }
        }
    }
}
