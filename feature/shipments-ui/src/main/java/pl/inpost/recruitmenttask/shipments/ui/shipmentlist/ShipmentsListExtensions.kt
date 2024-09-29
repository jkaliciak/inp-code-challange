package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI.HeaderUI
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI.ShipmentUI
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentStatusUI
import java.util.Comparator.naturalOrder
import java.util.Comparator.reverseOrder

//1. Add grouping to the list of Shipments by flag **ShipmentNetwork.operations.highlight**

//3. Sort list items in groups by (the closest date to current date should be at top of the list):
//* status - order is described in `ShipmentStatus.kt` file (the higher order, the higher it should be on the list)
//* pickupDate
//* expireDate
//* storedDate
//* number

/**
 * Method divides the shipment list into two groups if there are shipments that have to be highlighted.
 * Otherwise no grouping. The list is also sorted by status, pickupDate, expiryDate, storedDate, number
 */
fun List<ShipmentUI>.transformToGroupedAndSortedList(): List<ShipmentListItemUI> {
    val groups = groupBy { it.operations.highlight }
    return if (groups.size > 1 && groups.all { it.value.isNotEmpty() }) {
        // 2 groups with grouped shipments
        groups.map { entry ->
            buildList {
                val sortedShipments = entry.value
                    .sortedWith(shipmentsComparator)

                // add headers only if some items from list are in the other group
                if (entry.value.size != this.size) {
                    val status = if (entry.key) {
                        // take status of most important shipment as header status
                        sortedShipments.first().status
                    } else {
                        ShipmentStatusUI.OTHER
                    }
                    add(HeaderUI(status))
                }
                sortedShipments.forEach { add(it) }
            }
        }.flatten()
    } else {
        // less than 2 groups with grouped shipments
        this.sortedWith(shipmentsComparator)
    }
}

private val shipmentsComparator: Comparator<ShipmentUI> =
    Comparator.comparing(ShipmentUI::status, reverseOrder())
    .thenComparing(ShipmentUI::pickUpDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::expiryDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::storedDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::number, naturalOrder())
