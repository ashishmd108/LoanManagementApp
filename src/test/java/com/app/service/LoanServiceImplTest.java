package com.app.service;

	
	import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.app.Service.LoanServiceImpl;
import com.app.entity.Loan;
import com.app.exception.DateNotValid;
import com.app.exception.NotFoundException;
import com.app.repository.LoanRepository;

	class LoanServiceImplTest {

	    @Mock
	    private LoanRepository loanRepository;

	    @InjectMocks
	    private LoanServiceImpl loanService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testAddLoanValidPaymentDate() {
	    	Loan loan = new Loan("L31", "C31", "LEN31", 310000.0, 130000.0,
	                LocalDate.of(2023, 3, 5), 1.0, LocalDate.of(2023, 4, 5), 0.021, "");
	        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

	        Loan savedLoan = loanService.addLoan(loan);

	        assertNotNull(savedLoan);
	        // Add more assertions
	    }

	    @Test
	    void testAddLoanInvalidPaymentDate() {
	    	Loan loan = new Loan("L41", "C41", "LEN41", 410000.0, 430000.0,
	                LocalDate.of(2054, 4, 3), 1.0, LocalDate.of(2034, 4, 5), 0.021, "");
	        assertThrows(DateNotValid.class, () -> loanService.addLoan(loan));
	    }

	    @Test
	    void testGetAllLoans() {
	        List<Loan> loans = new ArrayList<>();
	        loans.add(new Loan("L23", "C31", "LEN11", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""));
	        
	        loans.add(new Loan("L53", "C51", "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, ""));
	        
	        when(loanRepository.findAll()).thenReturn(loans);

	        List<Loan> resultLoans = loanService.getAllLoans();

	        assertEquals(loans.size(), resultLoans.size());
	       
	    }

	    @Test
	    void testGetLoanByIdExisting() {
	        String loanId = "L1";
	        Loan loan = new Loan(loanId, "C51", "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));

	        Loan resultLoan = loanService.getLoanById(loanId);

	        assertNotNull(resultLoan);
	        assertEquals(loanId, resultLoan.getLoanId());
	      
	    }

	    @Test
	    void testGetLoanByIdNonExisting() {
	        String loanId = "L1";
	        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.empty());

	        assertThrows(NotFoundException.class, () -> loanService.getLoanById(loanId));
	    }

	    @Test
	    void testGetLoansByLenderExisting() {
	        String lenderId = "LEN1";
	        List<Loan> loans = new ArrayList<>();
	        
	        loans.add(new Loan("L23", "C31", "lenderId", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 1.0, LocalDate.of(2034, 4, 4), 0.02, ""));
	        
	        loans.add(new Loan("L53", "C51", "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, ""));
	        
	       
	        when(loanRepository.findByLenderId(lenderId)).thenReturn(loans);

	        List<Loan> resultLoans = loanService.getLoansByLender(lenderId);

	        assertNotNull(resultLoans);
	        assertEquals(loans.size(), resultLoans.size());
	        
	    }

	    @Test
	    void testGetLoansByLenderNonExisting() {
	        String lenderId = "LEN1";
	        when(loanRepository.findByLenderId(lenderId)).thenReturn(new ArrayList<>());

	        assertThrows(NotFoundException.class, () -> loanService.getLoansByLender(lenderId));
	    }

	    @Test
	    void testGetLoansByInterestRateNoLoansFound() {
	        Double interestRate = 1.5;
	        List<Loan> loans = new ArrayList<>();
	        when(loanRepository.findByInterestRatePerDay(interestRate)).thenReturn(loans);

	        assertThrows(NotFoundException.class, () -> loanService.getLoansByInterestRate(interestRate));
	    }
	    
	    @Test
	    void testGetLoansByInterestRateLoansFound() {
	        Double interestRate = 1.5;
	      
	        List<Loan> loans = new ArrayList<>();
	        
	        Loan loan1 = new Loan ("L23", "C31", "lenderId", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), interestRate, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", "C51", "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), interestRate, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", "C11", "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
               loans.add(loan1);
               loans.add(loan2);
               loans.add(loan3);
	        
	        when(loanRepository.findByInterestRatePerDay(interestRate)).thenReturn(loans);

	        List<Loan> resultLoans = loanService.getLoansByInterestRate(interestRate);

	        assertEquals(loans.size(), resultLoans.size());
	        
	        
	        
	        assertEquals(loan1.getLoanId(), resultLoans.get(0).getLoanId());
	        assertEquals(loan2.getLoanId(), resultLoans.get(1).getLoanId());

	     
	        assertEquals(loan1.getAmount(), resultLoans.get(0).getAmount());
	        assertEquals(loan2.getAmount(), resultLoans.get(1).getAmount());

	        assertEquals(loan1.getDueDate(), resultLoans.get(0).getDueDate());
	        assertEquals(loan2.getDueDate(), resultLoans.get(1).getDueDate());
    
	    }
	    @Test
	    void testGetLoansByCustomerIdLoansFound() {
	        String customerId = "C1";
	        
	        List<Loan> loans = new ArrayList<>();
	        
	        Loan loan1 = new Loan ("L23", customerId, "lenderId", 470000.0, 480000.0,
	                LocalDate.of(2034, 2, 2), 2.5, LocalDate.of(2034, 4, 4), 0.02, ""); 
	      
	        Loan loan2 = new Loan ("L53", customerId, "LEN41", 476000.0, 488000.0,
	                LocalDate.of(2034, 1, 1), 2.5, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        Loan loan3 = new Loan ("L43", customerId, "LEN31", 406000.0, 388000.0,
	                LocalDate.of(2034, 1, 1), 1.0, LocalDate.of(2034, 2, 2), 0.03, "");
	    	        
	        
            loans.add(loan1);
            loans.add(loan2);
            loans.add(loan3);
	        
	        
	        when(loanRepository.findByCustomerId(customerId)).thenReturn(loans);

	        List<Loan> resultLoans = loanService.getLoansByCustomerId(customerId);

	        assertEquals(loans.size(), resultLoans.size());

	        assertEquals(loan1.getLoanId(), resultLoans.get(0).getLoanId());
	        assertEquals(loan2.getLoanId(), resultLoans.get(1).getLoanId());

	        // Add more assertions for other properties
	        assertEquals(loan1.getAmount(), resultLoans.get(0).getAmount());
	        assertEquals(loan2.getAmount(), resultLoans.get(1).getAmount());

	        assertEquals(loan1.getDueDate(), resultLoans.get(0).getDueDate());
	        assertEquals(loan2.getDueDate(), resultLoans.get(1).getDueDate());

	       
	    }

	    @Test
	    void testGetLoansByCustomerIdNoLoansFound() {
	        String customerId = "C1";
	        List<Loan> loans = new ArrayList<>(); // Empty list
	        
	        when(loanRepository.findByCustomerId(customerId)).thenReturn(loans);

	        assertThrows(NotFoundException.class, () -> loanService.getLoansByCustomerId(customerId));
	    }
	    
	    @Test
	    void testCalculateRemainingAmountSumByLender() {
	       
	        List<Loan> loans = new ArrayList<>();
	      
	        Loan loan1 = new Loan ();
	        
	        loan1.setLenderId("LEN1");
	        loan1.setRemainingAmount(1000.0);
	        
	        Loan loan2 = new Loan ();
	        loan2.setLenderId("LEN2");
	        loan2.setRemainingAmount(2000.0);
	        
	        Loan loan3 = new Loan();
	        loan3.setLenderId("LEN1");
	        loan3.setRemainingAmount(1500.0);
	        
	        Loan loan4 = new Loan();
	        loan4.setLenderId("LEN3");
	        loan4.setRemainingAmount(500.0);

	        loans.add(loan1);
	        loans.add(loan2);
	        loans.add(loan3);
	        loans.add(loan4);
	        
	        when(loanRepository.findAll()).thenReturn(loans);

	        Map<String, Double> result = loanService.calculateRemainingAmountSumByLender();

	        assertEquals(3, result.size());

	        assertEquals(2500.0, result.get(loan1.getLenderId())); 
	        assertEquals(2000.0, result.get(loan2.getLenderId()));
	        assertEquals(500.0, result.get(loan4.getLenderId()));
	        
	    
	    }
               
	       
	    

	        @Test
	        void testCalculateRemainingAmountSumByCustomerId() {
	            // Create a list of loan objects with different customer IDs and remaining amounts
	            List<Loan> loans = new ArrayList<>();
//	            loans.add(new Loan("L1", "C1", "LEN1", 10000.0, 5000.0));
//	            loans.add(new Loan("L2", "C2", "LEN1", 15000.0, 7000.0));
//	            loans.add(new Loan("L3", "C3", "LEN2", 20000.0, 9000.0, /* other properties */));
//	            loans.add(new Loan("L4", "C1", "LEN2", 12000.0, 6000.0, /* other properties */));

	            Loan loan1 = new Loan ();
	            loan1.setCustomerId("C1");
	            loan1.setRemainingAmount(5000.0);   
	            
	            
                Loan loan2 = new Loan ();
	            loan2.setCustomerId("C2");
	            loan2.setRemainingAmount(7000.0);   
	            
	            Loan loan3 = new Loan ();
	            loan3.setCustomerId("C3");
	            loan3.setRemainingAmount(9000.0);
	            
	            Loan loan4 = new Loan ();
	            loan4.setCustomerId("C1");
	            loan4.setRemainingAmount(6000.0);
	            
	            loans.add(loan1);
	            loans.add(loan2);
	            loans.add(loan3);
	            loans.add(loan4);
	            
	            when(loanRepository.findAll()).thenReturn(loans);

	          
	            Map<String, Double> result = loanService.calculateRemainingAmountSumByCustomerId();

	            
	            assertEquals(3, result.size());
	            assertEquals(11000.0, result.get("C1"));
	            assertEquals(7000.0, result.get("C2"));
	            assertEquals(9000.0, result.get("C3"));
	            
	        }

	        @Test
	        void testCalculateInterestSumByInterestRate() {
	            List<Loan> loans = new ArrayList<>();
	            
	            Loan loan1 = new Loan ("L23", "C1", "LEN3", 7000.0, 8000.0,
		                LocalDate.of(2034, 2, 2), 1.5, LocalDate.of(2034, 4, 4), 0.02, ""); 
		      
		        Loan loan2 = new Loan ("L53", "C2", "LEN41", 6000.0, 1000.0,
		                LocalDate.of(2034, 1, 1), 1.5, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
		        Loan loan3 = new Loan ("L43", "C3", "LEN31", 8000.0, 2000.0,
		                LocalDate.of(2034, 1, 1), 2.0, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
		        Loan loan4 = new Loan ("L4", "C4", "LEN1", 8000.0, 2000.0,
		                LocalDate.of(2034, 1, 1), 2.0, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
	            
		        loans.add(loan1);
		        loans.add(loan2);
		        loans.add(loan3);
		        loans.add(loan4);
		        
	            when(loanRepository.findAll()).thenReturn(loans);

	            Map<Double, Double> result = loanService.calculateInterestSumByInterestRate();

	            assertEquals(2, result.size());
	            assertEquals(expectedInterestSum(1.5, loans), result.get(1.5));
	            assertEquals(expectedInterestSum(2.0, loans), result.get(2.0));
	            
	            
	        }
	        
	        private double expectedInterestSum(Double interestRate, List<Loan> loans) {
	            double sum = 0.0;
	            for (Loan loan : loans) {
	                if (loan.getInterestRatePerDay().equals(interestRate)) {
	                    sum += loanService.calculateInterest(loan);
	                }
	            }
	            return sum;
	        }
	        
	        @Test
	        void testCalculatePenaltySumByLender() {
	            List<Loan> loans = new ArrayList<>();
	            
	            Loan loan1 = new Loan ("L23", "C1", "LEN1", 7000.0, 8000.0,
		                LocalDate.of(2034, 2, 2), 1.5, LocalDate.of(2034, 4, 4), 0.02, ""); 
		      
		        Loan loan2 = new Loan ("L53", "C2", "LEN1", 6000.0, 1000.0,
		                LocalDate.of(2034, 1, 1), 1.5, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
		        Loan loan3 = new Loan ("L43", "C3", "LEN2", 8000.0, 2000.0,
		                LocalDate.of(2034, 1, 1), 2.0, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
		        Loan loan4 = new Loan ("L4", "C4", "LEN2", 8000.0, 2000.0,
		                LocalDate.of(2034, 1, 1), 2.0, LocalDate.of(2034, 2, 2), 0.03, "");
		    	        
		        loans.add(loan1);
		        loans.add(loan2);
		        loans.add(loan3);
		        loans.add(loan4);
	           
	            
	            when(loanRepository.findAll()).thenReturn(loans);

	            Map<String, Double> result = loanService.calculatePenaltySumByLender();

	            assertEquals(2, result.size());
	            assertEquals(expectedPenaltySum("LEN1", loans), result.get("LEN1"));
	            assertEquals(expectedPenaltySum("LEN2", loans), result.get("LEN2"));
	            
	        }

	        private double expectedPenaltySum(String lenderId, List<Loan> loans) {
	            double sum = 0.0;
	            for (Loan loan : loans) {
	                if (loan.getLenderId().equals(lenderId)) {
	                    sum += loanService.calculatePenalty(loan);
	                }
	            }
	            return sum;
	        }

	       
	    }
	    




	        
	        
	    

	     
	    
	



