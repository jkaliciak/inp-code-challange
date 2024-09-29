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
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.model.toUI
import pl.inpost.recruitmenttask.ui.extensions.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val observeShipmentsUseCase: ObserveShipmentsUseCase,
    private val updateShipmentsUseCase: UpdateShipmentsUseCase,
    private val archiveShipmentUseCase: ArchiveShipmentUseCase,
) : ViewModel() {

    private val mutableUiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = mutableUiState

    private val mutableUiError = Channel<UiError>(Channel.BUFFERED)
    val uiError: Flow<UiError> = mutableUiError.receiveAsFlow()

    init {
        observeShipments()
        refreshShipments()
    }

    private fun observeShipments() {
        observeShipmentsUseCase.observeShipments()
            .onEach { result ->
                when (result) {
                    is AppResult.Loading -> {
                        mutableUiState.setState { copy(isRefreshing = result.isLoading) }
                    }

                    is AppResult.Success -> {
                        mutableUiState.setState {
                            copy(
                                isRefreshing = false,
                                shipments = result.data
                                    .toUI()
                                    .transformToGroupedAndSortedList(),
                            )
                        }
                    }

                    is AppResult.Error -> mutableUiError.send(UiError.General(result.throwable))
                }
            }.launchIn(viewModelScope)
    }

    fun sendUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.RefreshShipments -> refreshShipments()
            is UiEvent.ArchiveShipment -> archiveShipment(uiEvent.number)
        }
    }

    private fun refreshShipments() {
        viewModelScope.launch {
            mutableUiState.setState { copy(isRefreshing = true) }
            when (val result = updateShipmentsUseCase.updateShipments()) {
                is AppResult.Loading -> {
                    mutableUiState.setState { copy(isRefreshing = result.isLoading) }
                }

                is AppResult.Success -> {
                    mutableUiState.setState { copy(isRefreshing = false) }
                }

                is AppResult.Error -> mutableUiError.send(UiError.General(result.throwable))
            }
        }
    }

    private fun archiveShipment(number: String) {
        viewModelScope.launch {
            mutableUiState.setState { copy(isRefreshing = true) }
            when (val result = archiveShipmentUseCase.archiveShipment(number)) {
                is AppResult.Loading -> {
                    mutableUiState.setState { copy(isRefreshing = result.isLoading) }
                }

                is AppResult.Success -> {
                    mutableUiState.setState { copy(isRefreshing = false) }
                }

                is AppResult.Error -> {
                    mutableUiState.setState { copy(isRefreshing = false) }
                    mutableUiError.send(UiError.General(result.throwable))
                }
            }
        }
    }

    data class UiState(
        val isRefreshing: Boolean = false,
        val shipments: List<ShipmentListItemUI> = emptyList(),
    )

    sealed class UiEvent {

        data object RefreshShipments : UiEvent()

        data class ArchiveShipment(val number: String) : UiEvent()
    }

    sealed class UiError {
        data class General(val throwable: Throwable) : UiError()
    }
}
