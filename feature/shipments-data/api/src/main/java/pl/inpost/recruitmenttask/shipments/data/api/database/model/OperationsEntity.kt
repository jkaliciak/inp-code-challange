package pl.inpost.recruitmenttask.shipments.data.api.database.model

data class OperationsEntity(
    val manualArchive: Boolean,
    val delete: Boolean,
    val collect: Boolean,
    val highlight: Boolean,
    val expandAvizo: Boolean,
    val endOfWeekCollection: Boolean
)