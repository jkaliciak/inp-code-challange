package pl.inpost.recruitmenttask.shipments.domain.api.model

enum class ShipmentType {
    PARCEL_LOCKER,
    COURIER,
    UNKNOWN;

    companion object {
        fun String.toShipmentType(): ShipmentType = try {
            ShipmentType.valueOf(this)
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        }
    }
}
