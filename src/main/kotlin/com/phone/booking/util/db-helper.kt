package com.phone.booking.util

import com.phone.booking.models.Device
import com.phone.booking.models.Phone
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    val models = listOf(
        "Samsung Galaxy S9", "Samsung Galaxy S8",
        "Samsung Galaxy S8", "Motorola Nexus 6", "Oneplus 9",
        "Apple iPhone 13", "Apple iPhone 12", "Apple iPhone 11",
        "iPhone X", "Nokia 3310"
    )
    models.forEach { model ->
        val device = retrieveDevice(model)
        var specifications: JSONObject? = JSONObject()
        if (device != null) {
            specifications = JSONParser().parse(device.specifications) as JSONObject?
        } else {
            println("Device not found")
        }
        val technology = specifications?.get("Technology") ?: "NA"
        val bands2g = specifications?.get("2G bands") ?: "NA"
        val bands3g = specifications?.get("3G bands") ?: "NA"
        val bands4g = specifications?.get("4G bands") ?: "NA"

        val phone = Phone(
            model = model,
            technology = technology as String,
            bands2g = bands2g as String,
            bands3g = bands3g as String,
            bands4g = bands4g as String
        )
        savePhone(phone)
    }
}

fun retrieveDevice(name: String): Device? {
    val connection = getConnection()
    val query = "SELECT * FROM device WHERE name = ?"
    val preparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, name)

    val resultSet = preparedStatement.executeQuery()
    return if (resultSet.next()) {
        val device = Device(
            id = resultSet.getLong("id"),
            urlHash = resultSet.getString("url_hash"),
            brandId = resultSet.getInt("brand_id"),
            name = resultSet.getString("name"),
            picture = resultSet.getString("picture"),
            releaseAt = resultSet.getString("released_at"),
            body = resultSet.getString("body"),
            os = resultSet.getString("os"),
            storage = resultSet.getString("storage"),
            displaySize = resultSet.getString("display_size"),
            displayResolution = resultSet.getString("display_resolution"),
            cameraPixels = resultSet.getString("camera_pixels"),
            videoPixels = resultSet.getString("video_pixels"),
            ram = resultSet.getString("ram"),
            chipset = resultSet.getString("chipset"),
            batterySize = resultSet.getString("battery_size"),
            batteryType = resultSet.getString("battery_type"),
            specifications = resultSet.getString("specifications"),
            deletedAt = resultSet.getTimestamp("deleted_at")?.toLocalDateTime(),
            createdAt = resultSet.getTimestamp("created_at")?.toLocalDateTime(),
            updatedAt = resultSet.getTimestamp("updated_at")?.toLocalDateTime()
        )
        device
    } else {
        null
    }
}

fun savePhone(phone: Phone) {
    val connection = getConnection()

    val insertQuery =
        "INSERT INTO phone (model, available, booked_time, booked_by,technology, bands2g, bands3g, bands4g) VALUES ( ?, ?, ?, ?, ?,?,?,?)"
    val insertPreparedStatement = connection.prepareStatement(insertQuery)
    insertPreparedStatement.setString(1, phone.model)
    insertPreparedStatement.setBoolean(2, phone.available)
    insertPreparedStatement.setObject(3, null)
    insertPreparedStatement.setString(4, phone.bookedBy)
    insertPreparedStatement.setString(5, phone.technology)
    insertPreparedStatement.setString(6, phone.bands2g)
    insertPreparedStatement.setString(7, phone.bands3g)
    insertPreparedStatement.setString(8, phone.bands4g)

    insertPreparedStatement.executeUpdate()
    insertPreparedStatement.close()
    println("Phone saved successfully")
    connection.close()
}


fun getConnection(): Connection {
    val url = "jdbc:mysql://localhost:3306/phone"
    val username = "admin"
    val password = "admin"

    return DriverManager.getConnection(url, username, password)
}

