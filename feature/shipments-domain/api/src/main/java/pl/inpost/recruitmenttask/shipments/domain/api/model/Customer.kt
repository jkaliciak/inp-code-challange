package pl.inpost.recruitmenttask.shipments.domain.api.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.CustomerEntity

data class Customer(
    val email: String?,
    val phoneNumber: String?,
    val name: String?,
)

fun CustomerEntity.toDomain() = Customer(
    email = email,
    phoneNumber = phoneNumber,
    name = name,
)