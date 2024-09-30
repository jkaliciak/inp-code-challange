package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.domain.api.model.Operations
import pl.inpost.recruitmenttask.shipments.domain.api.model.Shipment
import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentStatus
import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentType
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ArchiveShipmentUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.ObserveShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.domain.api.usecase.UpdateShipmentsUseCase
import pl.inpost.recruitmenttask.shipments.ui.model.toUI
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class ShipmentListViewModelTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val observeShipmentsUseCase: ObserveShipmentsUseCase = mockk()
    private val updateShipmentsUseCase: UpdateShipmentsUseCase = mockk()
    private val archiveShipmentUseCase: ArchiveShipmentUseCase = mockk()

    private lateinit var underTest: ShipmentListViewModel

    private val defaultShipments = createShipments()
    private val initialState = ShipmentListViewModel.UiState()

    @Before
    fun before() {
//        coEvery { appDatabase.shipmentDao() } returns shipmentDao
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should trigger UpdateShipmentsUseCase and ObserveShipmentsUseCase when ViewModel initialized`() =
        runTest {
            coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
            coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
                AppResult.Success(defaultShipments)
            )

            underTest = createViewModel()
            advanceUntilIdle()

            coVerify(exactly = 1) { updateShipmentsUseCase.updateShipments() }
            verify(exactly = 1) { observeShipmentsUseCase.observeShipments() }
            confirmVerified(updateShipmentsUseCase, observeShipmentsUseCase)
    }

    @Test
    fun `should emit uiState with non-empty shipments when ViewModel initialized`() = runTest {
        coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
        coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
            AppResult.Success(defaultShipments)
        )

        underTest = createViewModel()

        underTest.uiState.test {
            assertEquals(expected = initialState, actual = awaitItem())
            assertEquals(
                expected = initialState.copy(shipments = defaultShipments.toUI()),
                actual = awaitItem()
            )
            assertEquals(
                expected = initialState.copy(
                    isRefreshing = true,
                    shipments = defaultShipments.toUI()
                ),
                actual = awaitItem()
            )
            assertEquals(
                expected = initialState.copy(
                    isRefreshing = false,
                    shipments = defaultShipments.toUI()
                ),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `should emit uiState with empty shipments when ViewModel initialized and observe failed`() =
        runTest {
            coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
            val throwable = Exception("test")
            coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
                AppResult.Error(throwable)
            )

            underTest = createViewModel()

            underTest.uiState.test {
                assertEquals(expected = initialState, actual = awaitItem())
                assertEquals(
                    expected = initialState.copy(
                        isRefreshing = true,
                        shipments = emptyList()
                    ),
                    actual = awaitItem()
                )
                assertEquals(
                    expected = initialState.copy(
                        isRefreshing = false,
                        shipments = emptyList()
                    ),
                    actual = awaitItem()
                )
            }
        }

    @Test
    fun `should emit General uiError when ViewModel initialized and observe failed`() = runTest {
        coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
        val throwable = Exception("test")
        coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
            AppResult.Error(throwable)
        )

        underTest = createViewModel()

        underTest.uiError.test {
            assertEquals(
                expected = ShipmentListViewModel.UiError.General(throwable),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `should emit General uiError when ViewModel initialized and update failed`() = runTest {
        val throwable = Exception("test")
        coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Error(throwable)
        coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
            AppResult.Success(defaultShipments)
        )

        underTest = createViewModel()

        underTest.uiError.test {
            assertEquals(
                expected = ShipmentListViewModel.UiError.General(throwable),
                actual = awaitItem()
            )
        }
    }

    @Test
    fun `should emit General uiError when RefreshShipments event received and update failed`() =
        runTest {
            val throwable = Exception("test")
            coEvery { updateShipmentsUseCase.updateShipments() } returnsMany listOf(
                AppResult.Success(Unit),
                AppResult.Error(throwable)
            )
            coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
                AppResult.Success(defaultShipments)
            )

            underTest = createViewModel()
            underTest.sendUiEvent(ShipmentListViewModel.UiEvent.RefreshShipments)

            underTest.uiError.test {
                assertEquals(
                    expected = ShipmentListViewModel.UiError.General(throwable),
                    actual = awaitItem()
                )
            }
        }

    @Test
    fun `should emit uiState with refreshing flag changes when ArchiveShipment event received`() =
        runTest {
            coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
            coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
                AppResult.Success(emptyList())
            )
            coEvery { archiveShipmentUseCase.archiveShipment(any()) } returns AppResult.Success(Unit)

            underTest = createViewModel()
            advanceUntilIdle()
            underTest.sendUiEvent(ShipmentListViewModel.UiEvent.ArchiveShipment("1234567890"))

            underTest.uiState.test {
                assertEquals(expected = initialState, actual = awaitItem())
                assertEquals(
                    expected = initialState.copy(isRefreshing = true),
                    actual = awaitItem()
                )
                assertEquals(
                    expected = initialState.copy(isRefreshing = false),
                    actual = awaitItem()
                )
            }
        }

    @Test
    fun `should emit General uiError when ArchiveShipment event received and archive failed`() =
        runTest {
            coEvery { updateShipmentsUseCase.updateShipments() } returns AppResult.Success(Unit)
            coEvery { observeShipmentsUseCase.observeShipments() } returns MutableStateFlow(
                AppResult.Success(defaultShipments)
            )
            val throwable = Exception("test")
            coEvery { archiveShipmentUseCase.archiveShipment(any()) } returns AppResult.Error(
                throwable
            )

            underTest = createViewModel()
            underTest.sendUiEvent(ShipmentListViewModel.UiEvent.ArchiveShipment("1234567890"))

            underTest.uiError.test {
                assertEquals(
                    expected = ShipmentListViewModel.UiError.General(throwable),
                    actual = awaitItem()
                )
            }
        }

    private fun createViewModel() = ShipmentListViewModel(
        observeShipmentsUseCase = observeShipmentsUseCase,
        updateShipmentsUseCase = updateShipmentsUseCase,
        archiveShipmentUseCase = archiveShipmentUseCase
    )

    private fun createShipments() = listOf(
        Shipment(
            number = "unum",
            shipmentType = ShipmentType.UNKNOWN,
            status = ShipmentStatus.OTHER,
            openCode = null,
            expiryDate = null,
            storedDate = null,
            pickUpDate = null,
            receiver = null,
            sender = null,
            operations = Operations(
                manualArchive = false,
                delete = false,
                collect = false,
                highlight = false,
                expandAvizo = false,
                endOfWeekCollection = false
            ),
            eventLog = listOf()
        )
    )
}