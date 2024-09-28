package pl.inpost.recruitmenttask.shipments.data.impl.network.model

import pl.inpost.recruitmenttask.shipments.data.api.database.model.CustomerEntity

data class CustomerNetwork(
    val email: String?,
    val phoneNumber: String?,
    val name: String?,
)

fun CustomerNetwork.toEntity() = CustomerEntity(
    email = email,
    phoneNumber = phoneNumber,
    name = name,
)