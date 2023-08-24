package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entity.Loan;

public interface LoanRepository extends JpaRepository <Loan , String>{

	
	List<Loan> findByLenderId(String lenderId);

	List<Loan> findByCustomerId(String customerId);


	List<Loan> findByInterestRatePerDay(Double interestRate);

}
