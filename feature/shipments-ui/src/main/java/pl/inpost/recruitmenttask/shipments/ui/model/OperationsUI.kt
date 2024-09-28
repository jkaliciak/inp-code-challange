package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.shipments.domain.api.model.Operations

data class OperationsUI(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
)

fun Operations.toUI() = OperationsUI(
    manualArchive = manualArchive,
    delete = delete,
    collect = collect,
    highlight = highlight,
    expandAvizo = expandAvizo,
    endOfWeekCollection = endOfWeekCollection
)