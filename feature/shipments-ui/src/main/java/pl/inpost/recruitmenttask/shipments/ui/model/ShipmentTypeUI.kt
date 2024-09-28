package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentType

enum class ShipmentTypeUI {
    PARCEL_LOCKER,
    COURIER,
    UNKNOWN
}

fun ShipmentType.toUI() = when (this) {
    ShipmentType.PARCEL_LOCKER -> ShipmentTypeUI.PARCEL_LOCKER
    ShipmentType.COURIER -> ShipmentTypeUI.COURIER
    ShipmentType.UNKNOWN -> ShipmentTypeUI.UNKNOWN
}