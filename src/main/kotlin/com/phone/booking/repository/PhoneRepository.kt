package com.phone.booking.repository

import com.phone.booking.models.Phone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneRepository : JpaRepository<Phone, Long> {
    fun findByModelIgnoreCase(model: String): List<Phone>
}