package com.app.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LoanTest {

	    @Test
	    public void testGettersAndSetters() {
	        Loan loan = new Loan();
	        
	        loan.setLoanId("L1");
	        loan.setCustomerId("C31");
	        loan.setLenderId("LEN1");
	        loan.setAmount(10000.0);
	        loan.setRemainingAmount(10000.0);
	        LocalDate paymentDate = LocalDate.of(2023, 5, 6);
	        loan.setPaymentDate(paymentDate);
	        loan.setInterestRatePerDay(8.0);
	        LocalDate dueDate = LocalDate.of(2023, 5, 7);
	        loan.setDueDate(dueDate);
	        loan.setPenaltyPerDay(0.01);
	        loan.setCancel("");

	        assertEquals("L1", loan.getLoanId());
	        assertEquals("C31", loan.getCustomerId());
	        assertEquals("LEN1", loan.getLenderId());
	        assertEquals(10000.0, loan.getAmount());
	        assertEquals(10000.0, loan.getRemainingAmount());
	        assertEquals(paymentDate, loan.getPaymentDate());
	        assertEquals(8.0, loan.getInterestRatePerDay());
	        assertEquals(dueDate, loan.getDueDate());
	        assertEquals(0.01, loan.getPenaltyPerDay());
	        assertEquals("", loan.getCancel());
	    }
	}


