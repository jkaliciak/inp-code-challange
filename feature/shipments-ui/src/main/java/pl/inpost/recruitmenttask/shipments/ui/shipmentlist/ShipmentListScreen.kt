package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.ShipmentListViewModel.UiEvent
import pl.inpost.recruitmenttask.shipments.ui.shipmentlist.composable.ShipmentList
import pl.inpost.recruitmenttask.theme.Typography
import pl.inpost.recruitmenttask.common.translation.R as translationR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentListScreen(modifier: Modifier = Modifier) {
    val viewModel: ShipmentListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val uiError by viewModel.uiError.collectAsStateWithLifecycle(null)
    val pullRefreshState = rememberPullToRefreshState()
    val snackbarHostState = SnackbarHostState()

    val generalErrorMessage = stringResource(id = translationR.string.shipment_list_general_error)
    LaunchedEffect(key1 = uiError) {
        snackbarHostState.showSnackbar(
            message = generalErrorMessage,
            duration = SnackbarDuration.Short,
        )
    }

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        state = pullRefreshState,
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.sendUiEvent(UiEvent.RefreshShipments) },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = translationR.string.app_name),
                            style = Typography.titleMedium,
                        )
                    },
                )
            },
            content = { paddingValues ->
                ShipmentList(
                    modifier = modifier.padding(paddingValues),
                    uiState = uiState,
                    onArchiveShipment = { viewModel.sendUiEvent(UiEvent.ArchiveShipment(it.number)) }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        )
    }
}
