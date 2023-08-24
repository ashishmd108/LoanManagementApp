package com.app.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;


public class LoanRequest {

	@NotEmpty(message = "Loan ID field can not be empty")
	@Pattern(regexp = "^L\\d+$", message = "Invalid Loan ID format")
	private String loanId;

	@NotEmpty(message = "Customer ID field can not be empty")
	@Pattern(regexp = "^C\\d+$", message = "Invalid Customer ID format")
	private String customerId;

	@NotEmpty(message = "Lender ID field can not be empty")
	@Pattern(regexp = "^LEN\\d+$", message = "Invalid Lender ID format")
	private String lenderId;

	@Positive(message = "Amount must be a positive value")
	private Double amount;

	@PositiveOrZero(message = "Remaining amount can be zero or positive")
	private Double remainingAmount;

	@NotNull(message = "Payment Date can not be null")
    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    private LocalDate paymentDate;

	@NotNull(message = "Interest rate per day must not be null")
	@Positive(message = "Interest rate per day must be a positive value")
	private Double interestRatePerDay;

	@NotNull(message = "Due Date can not be null")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dueDate;

	@Positive(message = "Penalty per day can be zero or positive")
	private Double penaltyPerDay;

	private String cancel;

	public LoanRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoanRequest(
			 String loanId,
			 String customerId,
			 String lenderId,
			 Double amount,
			 Double remainingAmount,
			 LocalDate paymentDate,
			 Double interestRatePerDay,
			 LocalDate dueDate,
			 Double penaltyPerDay, String cancel) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
		this.lenderId = lenderId;
		this.amount = amount;
		this.remainingAmount = remainingAmount;
		this.paymentDate = paymentDate;
		this.interestRatePerDay = interestRatePerDay;
		this.dueDate = dueDate;
		this.penaltyPerDay = penaltyPerDay;
		this.cancel = cancel;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLenderId() {
		return lenderId;
	}

	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getRemainingAmount() {
		return remainingAmount;
	}

	public void setRemainingAmount(Double remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getInterestRatePerDay() {
		return interestRatePerDay;
	}

	public void setInterestRatePerDay(double interestRatePerDay) {
		this.interestRatePerDay = interestRatePerDay;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Double getPenaltyPerDay() {
		return penaltyPerDay;
	}

	public void setPenaltyPerDay(Double penaltyPerDay) {
		this.penaltyPerDay = penaltyPerDay;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	

}
