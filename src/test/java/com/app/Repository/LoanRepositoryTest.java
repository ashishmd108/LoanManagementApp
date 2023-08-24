package com.app.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.app.entity.Loan;
import com.app.repository.LoanRepository;

@DataJpaTest
public class LoanRepositoryTest {

	@Autowired
	private LoanRepository loanRepository;
	
	
	@Test
	public void testFindByLenderId() {

		Loan loan = new Loan("L11", "C11", "LEN11", 10000.0, 9000.0, LocalDate.of(2023, 5, 6), 2.5,
				LocalDate.of(2023, 5, 7), 0.01, "");
		
		

		loanRepository.save(loan);

		List<Loan> loans = loanRepository.findByLenderId("LEN11");
		assertEquals(1, loans.size());

		Loan retrievedLoan = loans.get(0);
		assertEquals("L11", retrievedLoan.getLoanId());
		assertEquals("C11", retrievedLoan.getCustomerId());
		assertEquals("LEN11", retrievedLoan.getLenderId());
		assertEquals(10000.0, retrievedLoan.getAmount(), 0.001);
		assertEquals(9000.0, retrievedLoan.getRemainingAmount(), 0.001);
		assertEquals(LocalDate.of(2023, 5, 6), retrievedLoan.getPaymentDate());
		assertEquals(2.5, retrievedLoan.getInterestRatePerDay(), 0.001);
		assertEquals(LocalDate.of(2023, 5, 7), retrievedLoan.getDueDate());
		assertEquals(0.01, retrievedLoan.getPenaltyPerDay(), 0.001);
		assertEquals("", retrievedLoan.getCancel());

	}

	@Test
	public void testFindByCustomerId() {

		
		
		Loan loan1 = new Loan("L22", "C1", "LEN22", 11000.0, 9100.0, LocalDate.of(2024, 5, 6), 1.5,
				LocalDate.of(2024, 5, 7), 0.01, "");

		Loan loan2 = new Loan("L33", "C1", "LEN44", 11000.0, 9100.0, LocalDate.of(2024, 5, 6), 1.5,
				LocalDate.of(2024, 5, 7), 0.01, "");

		
		loanRepository.save(loan1);
		loanRepository.save(loan2);

		List<Loan> loans = loanRepository.findByCustomerId("C1");
		assertEquals(2, loans.size());

	}

	@Test
	public void testFindByInterestRatePerDay() {
		
		
		
		Loan loan1 = new Loan("L22", "C22", "LEN22", 11000.0, 9100.0, LocalDate.of(2024, 5, 6), 1.5,
				LocalDate.of(2024, 5, 7), 0.01, "");

		Loan loan2 = new Loan("L33", "C44", "LEN44", 11000.0, 9100.0, LocalDate.of(2024, 5, 6), 1.5,
				LocalDate.of(2024, 5, 7), 0.01, "");

		loanRepository.save(loan1);
		loanRepository.save(loan2);

	   
	    List<Loan> loans = loanRepository.findByInterestRatePerDay(1.5);
	    assertEquals(2, loans.size());

	  
	}

	
	
	
	
	
}
