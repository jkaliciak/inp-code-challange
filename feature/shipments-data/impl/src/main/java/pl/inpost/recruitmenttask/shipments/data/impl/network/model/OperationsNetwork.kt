package pl.inpost.recruitmenttask.shipments.data.impl.network.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.OperationsEntity

/**
 * @param manualArchive - shipment can be manually (gesture) archived
 * @param delete - shipment can be manually deleted
 * @param collect - shipment can be remotely collected
 * @param highlight - shipment is ready to pick up - grouping
 * @param expandAvizo - shipment time to pick up can be expanded - show button
 * @param endOfWeekCollection - shipment will be available to pick up over the weekend - change colors
 */
data class OperationsNetwork(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean,
)

fun OperationsNetwork.toEntity() = OperationsEntity(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)