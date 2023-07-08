package com.phone.booking.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Device(
    @Id val id: Long,
    var urlHash: String,
    var brandId: Int,
    var name: String,
    var picture: String? = null,
    var releaseAt: String? = null,
    var body: String? = null,
    var os: String? = null,
    var storage: String? = null,
    var displaySize: String? = null,
    var displayResolution: String? = null,
    var cameraPixels: String? = null,
    var videoPixels: String? = null,
    var ram: String? = null,
    var chipset: String? = null,
    var batterySize: String? = null,
    var batteryType: String? = null,
    var specifications: String,
    var deletedAt: LocalDateTime? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null
)
