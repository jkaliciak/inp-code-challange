package pl.inpost.recruitmenttask.shipments.data.impl.network.api

import pl.inpost.recruitmenttask.shipments.data.impl.network.model.ShipmentNetwork

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}
