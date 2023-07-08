package com.phone.booking.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Phone(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val model: String,
    var available: Boolean = true,
    var bookedTime: LocalDateTime? = null,
    var bookedBy: String? = null,
    val technology: String? = null,
    val bands2g: String? = null,
    val bands3g: String? = null,
    val bands4g: String? = null
)
