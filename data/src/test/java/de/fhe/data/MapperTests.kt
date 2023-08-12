package de.fhe.data

import de.fhe.adoptapal.data.AddressModel
import de.fhe.adoptapal.data.fromDomain
import de.fhe.adoptapal.data.toDomain
import de.fhe.adoptapal.domain.Address
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

/**
 * Mapper tests to map from and to domain <-> data entities/models
 */
class MapperTests{

    /**
     * test mapping from domain entity
     */
    @Test
    fun testMapAddressFromDomain() {
        val domainAddress = Address(houseNumber = "10", street = "Teststrasse", city = "Erfurt", zipCode = "O-5465")

        val result = domainAddress.fromDomain()

        assertEquals(domainAddress.zipCode, result.zipCode)
        assertEquals(domainAddress.city, result.city)
        assertEquals(domainAddress.street, result.street)
        assertEquals(domainAddress.houseNumber, result.houseNumber)
    }

    /**
     * test mapping to address domain entity
     */
    @Test
    fun testMapAddressToDomain() {
        val dataAddress = AddressModel(
            id = 187,
            createdTimestamp = LocalDateTime.now(),
            lastChangeTimestamp = LocalDateTime.now(),
            isDeleted = false,
            houseNumber = "10",
            street = "Teststreet",
            city = "Jena",
            zipCode = "12345",
            latitude = 54.0,
            longitude = 11.0
        )

        val result = dataAddress.toDomain()

        assertEquals(dataAddress.id, result.id)
        assertEquals(dataAddress.houseNumber, result.houseNumber)
        assertEquals(dataAddress.street, result.street)
        assertEquals(dataAddress.city, result.city)
        assertEquals(dataAddress.zipCode, result.zipCode)
        assertEquals(dataAddress.latitude, result.latitude)
        assertEquals(dataAddress.longitude, dataAddress.longitude)
    }

}