package pl.inpost.recruitmenttask.shipments.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentUI
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

    init {
        refreshShipments()
        observeShipments()
    }

    private fun refreshShipments() {
        viewModelScope.launch {
            updateShipmentsUseCase.updateShipments()
        }
    }

    private fun observeShipments() {
        observeShipmentsUseCase.observeShipments()
            .onEach {
                when (it) {
                    is AppResult.Loading -> {
                        mutableUiState.setState {
                            copy(isLoading = it.isLoading)
                        }
                    }

                    is AppResult.Success -> {
                        mutableUiState.setState {
                            copy(
                                isLoading = false,
                                shipments = it.data.toUI(),
                            )
                        }
                    }

                    is AppResult.Error -> it.throwable
                }
            }.launchIn(viewModelScope)
    }

    data class UiState(
        val isLoading: Boolean = false,
        val shipments: List<ShipmentUI> = emptyList(),
    )
}
