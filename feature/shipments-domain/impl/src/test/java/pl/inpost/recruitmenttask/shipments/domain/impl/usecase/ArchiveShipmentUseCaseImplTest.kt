package pl.inpost.recruitmenttask.shipments.domain.impl.usecase

import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.data.model.AppResult
import pl.inpost.recruitmenttask.shipments.data.api.repository.ShipmentsRepository
import kotlin.test.assertTrue

class ArchiveShipmentUseCaseImplTest {

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val shipmentsRepository: ShipmentsRepository = mockk()
    private lateinit var underTest: ArchiveShipmentUseCaseImpl

    @Before
    fun before() {
        underTest = ArchiveShipmentUseCaseImpl(shipmentsRepository, coroutineRule.dispatcher)
    }

    @After
    fun after() {
        unmockkAll()
    }

    @Test
    fun `should return success when use case executed`() = runTest {
        coEvery { shipmentsRepository.archiveShipment(any()) } just Runs

        val result = underTest.archiveShipment("1234567890")

        assertTrue(result is AppResult.Success)
    }

    @Test
    fun `should return error when use case executed and failed`() = runTest {
        coEvery { shipmentsRepository.archiveShipment(any()) } throws Exception()

        val result = underTest.archiveShipment("1234567890")

        assertTrue(result is AppResult.Error)
    }

    @Test
    fun `should trigger ShipmentsRepository updateShipments when use case executed`() = runTest {
        coEvery { shipmentsRepository.archiveShipment(any()) } just Runs

        underTest.archiveShipment("1234567890")

        coVerify(exactly = 1) { shipmentsRepository.archiveShipment(any()) }
        confirmVerified(shipmentsRepository)
    }
}
