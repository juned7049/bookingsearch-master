package com.searchapi.bookingsearch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.searchapi.bookingsearch.DTO.SearchResultDTO;
import com.searchapi.bookingsearch.entity.Booking;
import com.searchapi.bookingsearch.entity.BookingPackages;
import com.searchapi.bookingsearch.entity.BookingServices;
import com.searchapi.bookingsearch.entity.CustomerDetails;
import com.searchapi.bookingsearch.entity.SPServicePackages;
import com.searchapi.bookingsearch.entity.ServiceProviderService;
import com.searchapi.bookingsearch.repository.BookingRepository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;


@Service
public class BookingSearchService {

	@Autowired
	private BookingRepository bookingRepository;
	
	public List<SearchResultDTO> searchBookings(String customerName, Date date, String packageName, String servicename, Long bookingId) {
	    Specification<Booking> specification = (root, query, criteriaBuilder) -> {
	        List<Predicate> predicates = new ArrayList<>();
	        System.out.println(predicates);

	        if (bookingId != null) {
	            predicates.add(criteriaBuilder.equal(root.get("bookingId"), bookingId));
	        }

	        if (customerName != null) {
	            Join<Booking, CustomerDetails> customerJoin = root.join("customer");
	            predicates.add(criteriaBuilder.or(
	                criteriaBuilder.like(criteriaBuilder.lower(customerJoin.get("firstName")),
	                    "%" + customerName.toLowerCase() + "%"),
	                criteriaBuilder.like(criteriaBuilder.lower(customerJoin.get("lastName")),
	                    "%" + customerName.toLowerCase() + "%")
	            ));
	        }

	        if (date != null) {
	            predicates.add(criteriaBuilder.equal(root.get("bookingDate"), date));
	        }

	        if (packageName != null) {
	            Join<Booking, BookingPackages> bookingPackagesJoin = root.join("bookingPackages");
	            Join<BookingPackages, SPServicePackages> spServicePackagesJoin = bookingPackagesJoin.join("spServicePackage");
	            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(spServicePackagesJoin.get("packagename")), "%" + packageName.toLowerCase() + "%"));
	        }

	        if (servicename != null) {
	            Join<Booking, BookingServices> bookingservicesJoin = root.join("bookingservices");
	            Join<BookingServices, ServiceProviderService> serviceproviderserviceJoin = bookingservicesJoin.join("serviceproviderservice");
	            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(serviceproviderserviceJoin.get("servicename")), "%" + servicename.toLowerCase() + "%"));
	        }
	        System.out.println( "predicate" +predicates);

	        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	    };

	    List<Booking> bookings = bookingRepository.findAll(specification);
	    return mapToSearchResultDTO(bookings);
	}

	    
   


	    private List<SearchResultDTO> mapToSearchResultDTO(List<Booking> bookings) {
	        return bookings.stream()
	                .map(booking -> new SearchResultDTO(
	                		booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName(),
	                        
	                        booking.getCustomer().getEmail(),
	                        booking.getCustomer().getMobile(),
	                        booking.getBookingDate(),
	                        booking.getTotalAmount()))
	                .collect(Collectors.toList());
	    }


	  
	}


