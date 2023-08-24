package com.app.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.entity.Loan;
import com.app.exception.DateNotValid;
import com.app.exception.NotFoundException;
import com.app.repository.LoanRepository;

@Service
public class LoanServiceImpl implements LoanService{
	
	 private final LoanRepository loanRepository;

	 private final Logger logger = (Logger) LoggerFactory.getLogger(LoanServiceImpl.class);
	    
	    public LoanServiceImpl(LoanRepository loanRepository) {
	        this.loanRepository = loanRepository;
	    }

	@Override
	public Loan addLoan(Loan loan) {
		  
		if (loan.getPaymentDate().isAfter(loan.getDueDate())) {
            throw new DateNotValid("Payment date cannot be greater than due date");
        }
				
		Loan savedLoan = loanRepository.save(loan);
		logger.info("Loan added: " + savedLoan.getLoanId());
	     return savedLoan;
    }
	
	@Override
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
		
	}

	@Override
	public Loan getLoanById(String loanId) {
	    return loanRepository.findById(loanId)
	        .orElseThrow(() -> new NotFoundException("Loan with ID " + loanId + " not exist"));
	}

	@Override
	public List<Loan> getLoansByLender(String lenderId) {
	    List<Loan> loans = loanRepository.findByLenderId(lenderId);

	    if (loans.isEmpty()) {
	        throw new NotFoundException("No loans found for lender ID: " + lenderId);
	    }

	    return loans;
	}


	@Override
	public List<Loan> getLoansByInterestRate(Double interestRate) {
		
		 List<Loan> loans = loanRepository.findByInterestRatePerDay(interestRate);
	     if (loans.isEmpty()) {
	    	 throw new NotFoundException("No loans found for : " + interestRate);
	     }
		return loans;
	}

	@Override
	public List<Loan> getLoansByCustomerId(String customerId) {
		
		List<Loan> loans = loanRepository.findByCustomerId(customerId);
		
		 if (loans.isEmpty()) {
	    	 throw new NotFoundException("No loans found for customer ID: " + customerId);
	     }
		
		return loans ;
				}
	
	@Override
	public Map<String, Double> calculateRemainingAmountSumByLender() {
		   
		List<Loan> allLoans = loanRepository.findAll();
		 return allLoans.stream()
	                .collect(Collectors.groupingBy(Loan::getLenderId,
	                        Collectors.summingDouble(Loan::getRemainingAmount))); 
	}

	
	

	@Override
	public Map<String, Double> calculateRemainingAmountSumByCustomerId() {
		List<Loan> allLoans = loanRepository.findAll();
        return allLoans.stream()
                .collect(Collectors.groupingBy(Loan::getCustomerId,
                        Collectors.summingDouble(Loan::getRemainingAmount)));
	}

	@Override
	public Map<Double, Double> calculateInterestSumByInterestRate() {
		  List<Loan> allLoans = loanRepository.findAll();
	        return allLoans.stream()
	                .collect(Collectors.groupingBy(Loan::getInterestRatePerDay,
	                        Collectors.summingDouble(this::calculateInterest)));
	}

	public double calculateInterest(Loan loan) {
	       Double principal = loan.getRemainingAmount();
	        double interestRate = loan.getInterestRatePerDay();
	        long daysRemaining = ChronoUnit.DAYS.between(loan.getPaymentDate(), loan.getDueDate());

	        return principal * interestRate * daysRemaining;
	    }
	
	@Override
	public Map<String, Double> calculatePenaltySumByLender() {
		
		
	        List<Loan> allLoans = loanRepository.findAll();
	        return allLoans.stream()
	                .collect(Collectors.groupingBy(Loan::getLenderId,
	                        Collectors.summingDouble(this::calculatePenalty)));
	    }
	
	public double calculatePenalty(Loan loan) {
        int daysLate = loan.getDueDate().until(LocalDate.now()).getDays();
         Double remainingAmount = loan.getRemainingAmount();
         Double penaltyPerDay = loan.getPenaltyPerDay();
        
    if (daysLate > 0) {
        double penaltyAmount = remainingAmount * (penaltyPerDay / 100) * daysLate;
        return penaltyAmount;
    }
    return 0.0;
}

	
	
}
