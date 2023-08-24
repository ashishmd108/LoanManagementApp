package com.app.Service;

import java.util.List;
import java.util.Map;

import com.app.entity.Loan;

public interface LoanService {
	
	    Loan addLoan(Loan loan);

	    List<Loan> getAllLoans();

	    Loan getLoanById(String loanId);
	    
	    List<Loan> getLoansByLender(String lenderId);

	    List<Loan> getLoansByInterestRate(Double interestRate);
	    
	    List<Loan> getLoansByCustomerId(String customerId);
	    
	    Map<String, Double> calculateRemainingAmountSumByLender();

	    Map<Double, Double> calculateInterestSumByInterestRate();

	    Map<String, Double> calculateRemainingAmountSumByCustomerId();

		Map<String, Double> calculatePenaltySumByLender();

		


}
