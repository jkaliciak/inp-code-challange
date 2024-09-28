package pl.inpost.recruitmenttask.shipments.domain.api.model

/**
 * Order of statuses - higher number = higher priority
 * 1. CREATED
 * 2. CONFIRMED
 * 3. ADOPTED_AT_SOURCE_BRANCH
 * 4. SENT_FROM_SOURCE_BRANCH
 * 5. ADOPTED_AT_SORTING_CENTER
 * 6. SENT_FROM_SORTING_CENTER
 * 7. OTHER
 * 8. DELIVERED
 * 9. RETURNED_TO_SENDER
 * 10. AVIZO
 * 11. OUT_FOR_DELIVERY
 * 12. READY_TO_PICKUP
 * 13. PICKUP_TIME_EXPIRED
 */
enum class ShipmentStatus {
    ADOPTED_AT_SORTING_CENTER,
    SENT_FROM_SORTING_CENTER,
    ADOPTED_AT_SOURCE_BRANCH,
    SENT_FROM_SOURCE_BRANCH,
    AVIZO,
    CONFIRMED,
    CREATED,
    DELIVERED,
    OTHER,
    OUT_FOR_DELIVERY,
    PICKUP_TIME_EXPIRED,
    READY_TO_PICKUP,
    RETURNED_TO_SENDER,
    UNKNOWN;

    companion object {
        fun String.toShipmentStatus(): ShipmentStatus = try {
            ShipmentStatus.valueOf(this)
        } catch (e: IllegalArgumentException) {
            UNKNOWN
        }
    }
}
