package com.phone.booking.controllers

import com.phone.booking.models.Phone
import com.phone.booking.repository.PhoneRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime


@RestController
@RequestMapping("/phones")
@Tags(Tag(name = "Phone API"))
class PhoneBookingController {

    @Autowired
    private lateinit var phoneRepository: PhoneRepository

    @GetMapping
    @Operation(summary = "Get all phones available")
    fun getAllPhones(): List<Phone> {
        return phoneRepository.findAll()
    }

    @PostMapping("/{model}/book")
    @Operation(summary = "Book a phone")
    fun bookPhone(
        @Parameter(name = "model", description = "Phone model")
        @PathVariable("model") model: String,
        @Parameter(name = "bookedBy", description = "Person who is booking")
        @RequestParam("bookedBy") bookedBy: String
    ): ResponseEntity<String> {
        val phones = findPhonesByModel(model)
        return if (phones.isNotEmpty()) {
            val phone = phones.firstOrNull { it.available }
            if (phone != null) {
                phone.available = false
                phone.bookedTime = LocalDateTime.now()
                phone.bookedBy = bookedBy
                phoneRepository.save(phone)
                ResponseEntity.ok("Phone $model has been booked by $bookedBy")
            } else {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone $model is not available.")
            }
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone $model not found.")
        }
    }

    @PostMapping("/{model}/return")
    @Operation(summary = "Return the booked phone")
    fun returnPhone(
        @Parameter(name = "model", description = "Phone model")
        @PathVariable("model") model: String,
        @Parameter(name = "bookedBy", description = "Person who booked")
        @RequestParam("bookedBy") bookedBy: String
    ): ResponseEntity<String> {
        val phones = findPhonesByModel(model)
        return if (phones.isNotEmpty()) {
            val phone = phones.firstOrNull { !it.available && it.bookedBy == bookedBy}
            if (phone != null) {
                phone.available = true
                phone.bookedTime = null
                phone.bookedBy = null
                phoneRepository.save(phone)
                ResponseEntity.ok("Phone $model has been returned.")
            } else {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone $model is already available.")
            }
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone $model not found.")
        }
    }

    private fun findPhonesByModel(model: String): List<Phone> {
        return phoneRepository.findByModelIgnoreCase(model)
    }
}

