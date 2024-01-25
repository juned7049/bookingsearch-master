package com.searchapi.bookingsearch.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SearchResultDTO {
	private String customerName;
	private String email;
	private String mobile;
	private String bookingDate; 
	private BigDecimal totalAmount;

	public SearchResultDTO(String customerName, String email, String mobile, Date bookingDate, BigDecimal totalAmount) {
		this.customerName = customerName;
		this.email = email;
		this.mobile = mobile;

		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		this.bookingDate = dateFormatter.format(bookingDate);

		this.totalAmount = totalAmount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "SearchResultDTO{" + "customerName='" + customerName + '\'' + ", email='" + email + '\'' + ", mobile='"
				+ mobile + '\'' + ", bookingDate='" + bookingDate + '\'' + ", totalAmount=" + totalAmount + '}';
	}
}
