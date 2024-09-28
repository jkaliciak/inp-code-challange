package pl.inpost.recruitmenttask.shipments.domain.api.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.OperationsEntity

data class Operations(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean,
)

fun OperationsEntity.toDomain() = Operations(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection,
)