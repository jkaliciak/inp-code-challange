package pl.inpost.recruitmenttask.shipments.domain.api.model

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ShipmentTest {

    @Test
    fun `Shipment isValid should return true when shipment is valid`() {
        val shipment = createValidShipment()
        assertTrue(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when number is empty`() {
        val shipment = createValidShipment().copy(number = "")
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when number is blank`() {
        val shipment = createValidShipment().copy(number = " ")
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when status is unknown`() {
        val shipment = createValidShipment().copy(status = ShipmentStatus.UNKNOWN)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when type is unknown`() {
        val shipment = createValidShipment().copy(shipmentType = ShipmentType.UNKNOWN)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when sender is null`() {
        val shipment = createValidShipment().copy(sender = null)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender email is null`() {
        val sender = Customer(
            email = null,
            phoneNumber = "1234567890",
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender phone number is null`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = null,
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender name is null`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = "1234567890",
            name = null
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when all sender properties are null`() {
        val sender = Customer(
            email = null,
            phoneNumber = null,
            name = null
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender email is empty`() {
        val sender = Customer(
            email = "",
            phoneNumber = "1234567890",
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender phone number is empty`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = "",
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender name is empty`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = "1234567890",
            name = ""
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when all sender properties are empty`() {
        val sender = Customer(
            email = "",
            phoneNumber = "",
            name = ""
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender email is blank`() {
        val sender = Customer(
            email = " ",
            phoneNumber = "1234567890",
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender phone number is blank`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = " ",
            name = "Darth Vader"
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when only sender name is blank`() {
        val sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = "1234567890",
            name = " "
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    @Test
    fun `Shipment isValid should return false when all sender properties are blank`() {
        val sender = Customer(
            email = " ",
            phoneNumber = " ",
            name = " "
        )
        val shipment = createValidShipment().copy(sender = sender)
        assertFalse(shipment.isValid)
    }

    private fun createValidShipment() = Shipment(
        number = "gravida",
        shipmentType = ShipmentType.PARCEL_LOCKER,
        status = ShipmentStatus.CREATED,
        openCode = null,
        expiryDate = null,
        storedDate = null,
        pickUpDate = null,
        receiver = null,
        sender = Customer(
            email = "test@inpost.pl",
            phoneNumber = "1234567890",
            name = "Darth Vader"
        ),
        operations = Operations(
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        ),
        eventLog = emptyList()
    )
}