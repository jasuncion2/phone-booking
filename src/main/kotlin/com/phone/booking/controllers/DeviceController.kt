package com.phone.booking.controllers

import com.phone.booking.models.Device
import com.phone.booking.repository.DeviceRespository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/device")
@Tags(Tag(name = "Device API"))
class DeviceController {

    @Autowired
    private lateinit var deviceRespository: DeviceRespository

    @GetMapping("/get/{name}")
    @Operation(summary = "Retrieve device details")
    fun getDeviceByName(
        @Parameter(name = "name", description = "Device/Phone name")
        @PathVariable("name") name: String
    ): List<Device> {
        return deviceRespository.findByNameIgnoreCase(name)
    }

    @GetMapping("/list/{name}")
    @Operation(summary = "List all models having the specified name")
    fun listDevicesHavingName(
        @Parameter(name = "name", description = "Device/Phone name")
        @PathVariable("name") name: String
    ): List<Device> {
        return deviceRespository.findByNameContainingIgnoreCase(name)
    }

    @GetMapping("/get/id/{id}")
    @Operation(summary = "Retrieve device by Id")
    fun getDeviceById(
        @Parameter(name = "id", description = "Device/Phone id")
        @PathVariable("id") id: Long
    ): Optional<Device> {
        return deviceRespository.findById(id)
    }
}
