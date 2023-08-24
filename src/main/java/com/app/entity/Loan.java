package com.app.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Loan {

	@Id
	private String loanId;
	private String customerId;
	private String lenderId;
	private Double amount;
	private Double remainingAmount;
	private LocalDate paymentDate;
	private Double interestRatePerDay;
	private LocalDate dueDate;
	private Double penaltyPerDay;
	private String cancel;

	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Loan(String loanId, String customerId, String lenderId, Double amount, Double remainingAmount,
			LocalDate paymentDate, Double interestRatePerDay, LocalDate dueDate, Double penaltyPerDay, String cancel) {
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

	public Double getInterestRatePerDay() {
		return interestRatePerDay;
	}

	public void setInterestRatePerDay(Double interestRatePerDay) {
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
