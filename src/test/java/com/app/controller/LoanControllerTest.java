package com.app.controller;

	
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.Service.LoanService;
import com.app.dto.LoanRequest;
import com.app.entity.Loan;
import com.app.exception.NotFoundException;

	public class LoanControllerTest {
		
		 @Mock
		    private LoanService loanService;

		    @InjectMocks
		    private LoanController loanController;

		    @BeforeEach
		    void setUp() {
		        MockitoAnnotations.openMocks(this);
		    }
		    
		    
		    @Test
		    void testAddLoan() {
		        LoanRequest loanRequest = new LoanRequest("L1", "C31", "LEN1", 470000.0, 480000.0,
		                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, "");
		        
		        Loan loan = new Loan();
		        loan.setLoanId("L1");
		        loan.setCustomerId("C31");
		        loan.setLenderId("LEN1");
		        loan.setAmount(470000.0);
		        loan.setRemainingAmount(480000.0);
		        loan.setPaymentDate(LocalDate.of(2034, 2, 2));
		        loan.setInterestRatePerDay(1.0);
		        loan.setDueDate(LocalDate.of(2034, 4, 4));
		        loan.setPenaltyPerDay(0.02);
		        loan.setCancel("");
		        
		        when(loanService.addLoan(any(Loan.class))).thenReturn(loan);

		        ResponseEntity<Loan> responseEntity = loanController.addLoan(loanRequest);
		        Loan savedLoan = responseEntity.getBody();

		        assertEquals("L1", savedLoan.getLoanId());
		        assertEquals("C31", savedLoan.getCustomerId());
		        assertEquals("LEN1", savedLoan.getLenderId());
		        assertEquals(470000.0, savedLoan.getAmount());
		        assertEquals(480000.0, savedLoan.getRemainingAmount());
		        assertEquals(LocalDate.of(2034, 2, 2), savedLoan.getPaymentDate());
		        assertEquals(1.0, savedLoan.getInterestRatePerDay());
		        assertEquals(LocalDate.of(2034, 4, 4), savedLoan.getDueDate());
		        assertEquals(0.02, savedLoan.getPenaltyPerDay());
		        assertEquals("", savedLoan.getCancel());
		    }
		
		

	    @Test
	    void testGetAllLoans() {
	        // Create sample loans
	        List<Loan> loans = new ArrayList<>();

	        Loan loan1 = new Loan ("L23", "C31", "lenderId", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
               loans.add(loan1);
               loans.add(loan2);
               loans.add(loan3);

	        // Mock the behavior of the loanService
	        when(loanService.getAllLoans()).thenReturn(loans);

	        // Call the controller method
	        ResponseEntity<List<Loan>> response = loanController.getAllLoans();

	        // Assert the response
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(loans, response.getBody());
	        // Add more assertions if needed
	    }
	    
	    @Test
	    void testGetLoanByIdExisting() {
	        String loanId = "L1";
	       
	        Loan loan = new Loan ("L1", "C31", "LEN1", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        
	        when(loanService.getLoanById(loanId)).thenReturn(loan);

	        ResponseEntity<Loan> response = loanController.getLoanById(loanId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(loan, response.getBody());
	    }
	    
	    @Test
	    void testGetLoanByIdNonExisting() {
	        String loanId = "L1";

	        when(loanService.getLoanById(loanId)).thenThrow(new NotFoundException("Loan not found"));

	        assertThrows(NotFoundException.class, () -> loanController.getLoanById(loanId));
	    }
	    
	    @Test
	    void testGetLoansByLenderExisting() {
	        String lenderId = "LEN1";
	        List<Loan> loans = new ArrayList<>();
	       
	        Loan loan1 = new Loan ("L23", "C31", lenderId, 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", lenderId, 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
               loans.add(loan1);
               loans.add(loan2);
               loans.add(loan3);
	        
	        when(loanService.getLoansByLender(lenderId)).thenReturn(loans);

	        ResponseEntity<List<Loan>> response = loanController.getLoansByLender(lenderId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(loans, response.getBody());
	    }
	    
	    @Test
	    void testGetLoansByLenderNonExisting() {
	        String lenderId = "LEN1";

	        when(loanService.getLoansByLender(lenderId)).thenThrow(new NotFoundException("Loans not found for lender"));

	        assertThrows(NotFoundException.class, () -> loanController.getLoansByLender(lenderId));
	    }
	    
	    @Test
	    void testGetLoansByInterestRateExisting() {
	        double interestRate = 1.5;
	        List<Loan> loans = new ArrayList<>();
	        Loan loan1 = new Loan ("L23", "C31", "LEN3", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", "LEN6", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
               loans.add(loan1);
               loans.add(loan2);
               loans.add(loan3);
	        
	        when(loanService.getLoansByInterestRate(interestRate)).thenReturn(loans);

	        ResponseEntity<List<Loan>> response = loanController.getLoansByInterestRate(interestRate);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(loans, response.getBody());
	    }
	    
	    @Test
	    void testGetLoansByInterestRateNonExisting() {
	        double interestRate = 2.0;

	        when(loanService.getLoansByInterestRate(interestRate)).thenThrow(new NotFoundException("Loans not found for interest rate"));

	        assertThrows(NotFoundException.class, () -> loanController.getLoansByInterestRate(interestRate));
	    }
	    
	    @Test
	    void testGetLoansByCustomerIdExisting() {
	        String customerId = "C1";
	        List<Loan> loans = new ArrayList<>();
	        
	        Loan loan1 = new Loan ("L23", "C1", "LEN3", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", "LEN6", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
               loans.add(loan1);
               loans.add(loan2);
               loans.add(loan3);
	        
	        when(loanService.getLoansByCustomerId(customerId)).thenReturn(loans);

	        ResponseEntity<List<Loan>> response = loanController.getLoansByCustomerId(customerId);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(loans, response.getBody());
	    }
	    
	    @Test
	    void testGetLoansByCustomerIdNonExisting() {
	        String customerId = "C2";

	        when(loanService.getLoansByCustomerId(customerId)).thenThrow(new NotFoundException("Loans not found for customer ID"));

	        assertThrows(NotFoundException.class, () -> loanController.getLoansByCustomerId(customerId));
	    }
	    
	    @Test
	    void testCalculateRemainingAmountSumByLender() {
	        Map<String, Double> expectedResult = new HashMap<>();
	        expectedResult.put("LEN1", 5000.0); 
	        expectedResult.put("LEN2", 7000.0);
	        
	        when(loanService.calculateRemainingAmountSumByLender()).thenReturn(expectedResult);

	        ResponseEntity<Map<String, Double>> response = loanController.calculateRemainingAmountSumByLender();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(expectedResult, response.getBody());
	    }
	    
	    @Test
	    void testCalculateRemainingAmountSumByCustomerId() {
	        Map<String, Double> expectedResult = new HashMap<>();
	       
	        
	        expectedResult.put("C99", 5000.0); 
	        expectedResult.put("C98", 7000.0);
	        
	        when(loanService.calculateRemainingAmountSumByCustomerId()).thenReturn(expectedResult);

	        ResponseEntity<Map<String, Double>> response = loanController.calculateRemainingAmountSumByCustomerId();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(expectedResult, response.getBody());
	    }
	    
	    @Test
	    void testCalculateInterestSumByInterestRate() {
	        // Create a list of loan objects with different interest rates
	        List<Loan> loans = new ArrayList<>();
	        
	        Loan loan1 = new Loan ("L23", "C31", "LEN3", 4000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 2, 3), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", "LEN6", 4000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 1, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 6000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 2.0, LocalDate.of(2034, 1, 2), 0.03, "");
	    	        
	        loans.add(loan1);
            loans.add(loan2);
            loans.add(loan3);
	        
	        when(loanService.calculateInterestSumByInterestRate()).thenReturn(calculateExpectedResult(loans));

	        ResponseEntity<Map<Double, Double>> responseEntity = loanController.calculateInterestSumByInterestRate();
	        Map<Double, Double> result = responseEntity.getBody();

	        assertEquals(2, result.size());
	        assertEquals(9680.0, result.get(1.0)); // Example: Total interest for interest rate 1.5 is 50.0
	        assertEquals(7760.0, result.get(2.0)); // Example: Total interest for interest rate 2.0 is 0.0
	    }
	    
	    private Map<Double, Double> calculateExpectedResult(List<Loan> loans) {
	        return loans.stream()
	                .collect(Collectors.groupingBy(Loan::getInterestRatePerDay,
	                        Collectors.summingDouble(this::calculateInterest)));
	    }
                 
	    private double calculateInterest(Loan loan) {
	        Double principal = loan.getRemainingAmount();
	        double interestRate = loan.getInterestRatePerDay();
	        long daysRemaining = ChronoUnit.DAYS.between(loan.getPaymentDate(), loan.getDueDate());

	        return (principal * interestRate * daysRemaining)/100;
	    }
	    
	    @Test
	    void testCalculatePenaltySumByLender() {
	        Map<String, Double> expectedResult = new HashMap<>();
	        // Add lender IDs and corresponding sum values to the map
	        expectedResult.put("LEN1", 150.0);
	        expectedResult.put("LEN2", 100.0);

	        when(loanService.calculatePenaltySumByLender()).thenReturn(expectedResult);

	        ResponseEntity<Map<String, Double>> responseEntity = loanController.calculatePenaltySumByLender();
	        Map<String, Double> result = responseEntity.getBody();

	        assertEquals(2, result.size());
	        assertEquals(150.0, result.get("LEN1"));
	        assertEquals(100.0, result.get("LEN2"));
	    }
	    
	}



