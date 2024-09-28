package pl.inpost.recruitmenttask.shipments.ui.model

import pl.inpost.recruitmenttask.shipments.domain.api.model.Customer

data class CustomerUI(
    val email: String?,
    val phoneNumber: String?,
    val name: String?
)

fun Customer.toUI() = CustomerUI(
    email = email,
    phoneNumber = phoneNumber,
    name = name,
)