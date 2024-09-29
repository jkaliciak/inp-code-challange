package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.model.toUI
import pl.inpost.recruitmenttask.ui.extensions.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val observeShipmentsUseCase: ObserveShipmentsUseCase,
    private val updateShipmentsUseCase: UpdateShipmentsUseCase
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = mutableUiState

    private val mutableUiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent: Flow<UiEvent> = mutableUiEvent.receiveAsFlow()

    init {
        observeShipments()
        refreshShipments()
    }

    private fun observeShipments() {
        observeShipmentsUseCase.observeShipments()
            .onEach {
                when (it) {
                    is AppResult.Loading -> {
                        mutableUiState.setState { copy(isRefreshing = it.isLoading) }
                    }

                    is AppResult.Success -> {
                        mutableUiState.setState {
                            copy(
                                isRefreshing = false,
                                shipments = it.data
                                    .toUI()
                                    .transformToGroupedAndSortedList(),
                            )
                        }
                    }

                    is AppResult.Error -> it.throwable
                }
            }.launchIn(viewModelScope)
    }

    fun sendUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.RefreshShipments -> refreshShipments()
        }
    }

    private fun refreshShipments() {
        viewModelScope.launch {
            mutableUiState.setState { copy(isRefreshing = true) }
            updateShipmentsUseCase.updateShipments()
            mutableUiState.setState { copy(isRefreshing = false) }
        }
    }

    data class UiState(
        val isRefreshing: Boolean = false,
        val shipments: List<ShipmentListItemUI> = emptyList(),
    )

    sealed class UiEvent {
        data object RefreshShipments : UiEvent()
    }
}
