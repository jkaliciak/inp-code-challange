package pl.inpost.recruitmenttask.shipments.ui.model

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.shipments.domain.api.model.ShipmentStatus
import pl.inpost.recruitmenttask.shipments.ui.R

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
enum class ShipmentStatusUI(
    @StringRes val nameRes: Int
) {
    ADOPTED_AT_SORTING_CENTER(R.string.status_adopted_at_sorting_center),
    SENT_FROM_SORTING_CENTER(R.string.status_sent_from_sorting_center),
    ADOPTED_AT_SOURCE_BRANCH(R.string.status_adopted_at_source_branch),
    SENT_FROM_SOURCE_BRANCH(R.string.status_sent_from_source_branch),
    AVIZO(R.string.status_avizo),
    CONFIRMED(R.string.status_confirmed),
    CREATED(R.string.status_created),
    DELIVERED(R.string.status_delivered),
    OTHER(R.string.status_other),
    OUT_FOR_DELIVERY(R.string.status_out_for_delivery),
    PICKUP_TIME_EXPIRED(R.string.status_pickup_time_expired),
    READY_TO_PICKUP(R.string.status_ready_to_pickup),
    RETURNED_TO_SENDER(R.string.status_returned_to_sender),
    UNKNOWN(R.string.status_unknown),
}

fun ShipmentStatus.toUI() = when (this) {
    ShipmentStatus.ADOPTED_AT_SORTING_CENTER -> ShipmentStatusUI.ADOPTED_AT_SORTING_CENTER
    ShipmentStatus.SENT_FROM_SORTING_CENTER -> ShipmentStatusUI.SENT_FROM_SORTING_CENTER
    ShipmentStatus.ADOPTED_AT_SOURCE_BRANCH -> ShipmentStatusUI.ADOPTED_AT_SOURCE_BRANCH
    ShipmentStatus.SENT_FROM_SOURCE_BRANCH -> ShipmentStatusUI.SENT_FROM_SOURCE_BRANCH
    ShipmentStatus.AVIZO -> ShipmentStatusUI.AVIZO
    ShipmentStatus.CONFIRMED -> ShipmentStatusUI.CONFIRMED
    ShipmentStatus.CREATED -> ShipmentStatusUI.CREATED
    ShipmentStatus.DELIVERED -> ShipmentStatusUI.DELIVERED
    ShipmentStatus.OTHER -> ShipmentStatusUI.OTHER
    ShipmentStatus.OUT_FOR_DELIVERY -> ShipmentStatusUI.OUT_FOR_DELIVERY
    ShipmentStatus.PICKUP_TIME_EXPIRED -> ShipmentStatusUI.PICKUP_TIME_EXPIRED
    ShipmentStatus.READY_TO_PICKUP -> ShipmentStatusUI.READY_TO_PICKUP
    ShipmentStatus.RETURNED_TO_SENDER -> ShipmentStatusUI.RETURNED_TO_SENDER
    ShipmentStatus.UNKNOWN -> ShipmentStatusUI.UNKNOWN
}