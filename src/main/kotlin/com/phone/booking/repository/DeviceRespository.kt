package com.phone.booking.repository

import com.phone.booking.models.Device
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceRespository : JpaRepository<Device, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<Device>
    fun findByNameIgnoreCase(name: String): List<Device>
}
