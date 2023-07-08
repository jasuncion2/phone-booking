package com.phone.booking


import com.phone.booking.controllers.PhoneBookingController
import com.phone.booking.models.Phone
import com.phone.booking.repository.PhoneRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@SpringBootTest
class PhoneBookingControllerTest {

	@Mock
	private lateinit var phoneRepository: PhoneRepository

	@InjectMocks
	private lateinit var phoneBookingController: PhoneBookingController

	@BeforeEach
	fun setup() {
		MockitoAnnotations.openMocks(this)
	}

	@Test
	fun testGetAllPhones() {
		// Mock the phone repository
		val phones = listOf(
			Phone(1L, "Samsung Galaxy S9", true),
			Phone(2L, "Samsung Galaxy S8", true),
			Phone(3L, "Motorola Nexus 6", true)
		)
		`when`(phoneRepository.findAll()).thenReturn(phones)

		// Call the controller method
		val result: List<Phone> = phoneBookingController.getAllPhones()

		// Verify the result
		assertEquals(phones.size, result.size)
		assertEquals(phones, result)
	}

	@Test
	fun testBookPhoneSuccess() {
		// Mock the phone repository
		val phoneModel = "Samsung Galaxy S9"
		val bookedBy = "John Doe"
		val phone = Phone(1L, phoneModel, true)
		`when`(phoneRepository.findByModelIgnoreCase(phoneModel)).thenReturn(listOf(phone))
		`when`(phoneRepository.save(phone)).thenReturn(phone)

		// Call the controller method
		val response: ResponseEntity<String> = phoneBookingController.bookPhone(phoneModel, bookedBy)

		// Verify the response
		assertEquals(HttpStatus.OK, response.statusCode)
		assertEquals("Phone $phoneModel has been booked by $bookedBy", response.body)
		assertEquals(false, phone.available)
		assertEquals(bookedBy, phone.bookedBy)
		assertEquals(LocalDateTime::class.java, phone.bookedTime?.javaClass)
	}

	@Test
	fun testBookPhoneNotFound() {
		// Mock the phone repository
		val phoneModel = "Samsung Galaxy S9"
		`when`(phoneRepository.findByModelIgnoreCase(phoneModel)).thenReturn(emptyList())

		// Call the controller method
		val response: ResponseEntity<String> = phoneBookingController.bookPhone(phoneModel, "John Doe")

		// Verify the response
		assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
		assertEquals("Phone $phoneModel not found.", response.body)
	}

	@Test
	fun testReturnPhoneSuccess() {
		// Mock the phone repository
		val phoneModel = "Samsung Galaxy S9"
		val bookedBy = "John Doe"
		val phone = Phone(1L, phoneModel, false)
		phone.bookedBy = bookedBy
		`when`(phoneRepository.findByModelIgnoreCase(phoneModel)).thenReturn(listOf(phone))
		`when`(phoneRepository.save(phone)).thenReturn(phone)

		// Call the controller method
		val response: ResponseEntity<String> = phoneBookingController.returnPhone(phoneModel, bookedBy)

		// Verify the response
		assertEquals(HttpStatus.OK, response.statusCode)
		assertEquals("Phone $phoneModel has been returned.", response.body)
		assertEquals(true, phone.available)
		assertEquals(null, phone.bookedBy)
		assertEquals(null, phone.bookedTime)
	}

	@Test
	fun testReturnPhoneAlreadyAvailable() {
		// Mock the phone repository
		val phoneModel = "Samsung Galaxy S9"
		val bookedBy = "John Doe"
		val phone = Phone(1L, phoneModel, true)
		`when`(phoneRepository.findByModelIgnoreCase(phoneModel)).thenReturn(listOf(phone))

		// Call the controller method
		val response: ResponseEntity<String> = phoneBookingController.returnPhone(phoneModel, bookedBy)

		// Verify the response
		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
		assertEquals("Phone $phoneModel is already available.", response.body)
	}

	// Add more test cases for other controller methods

}

