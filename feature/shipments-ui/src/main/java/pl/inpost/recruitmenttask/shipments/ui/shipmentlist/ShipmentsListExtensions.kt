package pl.inpost.recruitmenttask.shipments.ui.shipmentlist

import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI.HeaderUI
import pl.inpost.recruitmenttask.shipments.ui.model.ShipmentListItemUI.ShipmentUI
import java.util.Comparator.naturalOrder

//3. Sort list items in groups by (the closest date to current date should be at top of the list):
//* status - order is described in `ShipmentStatus.kt` file (the higher order, the higher it should be on the list)
//* pickupDate
//* expireDate
//* storedDate
//* number
fun List<ShipmentUI>.transformToGroupedAndSortedList(): List<ShipmentListItemUI> =
    groupBy { it.status }
        .map { entry ->
            buildList {
                add(HeaderUI(entry.key))
                entry.value
                    .sortedWith(shipmentsComparator)
                    .forEach { add(it) }
            }
        }.flatten()

private val shipmentsComparator: Comparator<ShipmentUI> = Comparator.comparing(ShipmentUI::status)
    .thenComparing(ShipmentUI::pickUpDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::expiryDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::storedDate, Comparator.nullsLast(naturalOrder()))
    .thenComparing(ShipmentUI::number, naturalOrder())
