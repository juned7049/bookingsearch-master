package com.searchapi.bookingsearch.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.searchapi.bookingsearch.DTO.SearchResultDTO;
import com.searchapi.bookingsearch.service.BookingSearchService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingSearchService bookingSearchService;

	@GetMapping("/search")
	public ResponseEntity<List<SearchResultDTO>> searchBookings(@RequestParam(name = "customername", required = false) String customerName,
	        @RequestParam(name="date" ,required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
	        @RequestParam(name="packagename" ,required = false) String packagename,
	        @RequestParam(name="servicename", required = false) String servicename,
	        @RequestParam(name="bookingid",required = false) Long bookingId) {

	    List<SearchResultDTO> searchResults = bookingSearchService.searchBookings(customerName, date, packagename, servicename, bookingId);

	    return ResponseEntity.ok(searchResults);
	}

}