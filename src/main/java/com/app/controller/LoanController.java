package com.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Service.LoanService;
import com.app.dto.LoanRequest;
import com.app.entity.Loan;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping
	public ResponseEntity<Loan> addLoan(@Valid @RequestBody  LoanRequest loanRequest) {

		Loan loan = new Loan();

		BeanUtils.copyProperties(loanRequest, loan);

		Loan savedLoan = loanService.addLoan(loan);
		return ResponseEntity.ok(savedLoan);
	}

	@GetMapping
	public ResponseEntity<List<Loan>> getAllLoans() {
		List<Loan> loans = loanService.getAllLoans();
		return ResponseEntity.ok(loans);
	}

	@GetMapping("/{loanId}")
	public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
		Loan loan = loanService.getLoanById(loanId);
		return ResponseEntity.ok(loan);
	}

	@GetMapping("/lender/{lenderId}")
	public ResponseEntity<List<Loan>> getLoansByLender(@PathVariable String lenderId) {
		List<Loan> loans = loanService.getLoansByLender(lenderId);
		return ResponseEntity.ok(loans);
	}

	@GetMapping("/interestRate/{interestRate}")
	public ResponseEntity<List<Loan>> getLoansByInterestRate(@PathVariable double interestRate) {
		List<Loan> loans = loanService.getLoansByInterestRate(interestRate);
		return ResponseEntity.ok(loans);
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable String customerId) {
		List<Loan> loans = loanService.getLoansByCustomerId(customerId);
		return ResponseEntity.ok(loans);
	}

	@GetMapping("/remainingAmountSumByLender")
	public ResponseEntity<Map<String, Double>> calculateRemainingAmountSumByLender() {
		Map<String, Double> result = loanService.calculateRemainingAmountSumByLender();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/remainingAmountSumByCustomerId")
	public ResponseEntity<Map<String, Double>> calculateRemainingAmountSumByCustomerId() {
		Map<String, Double> result = loanService.calculateRemainingAmountSumByCustomerId();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/interestSumByInterestRate")
	public ResponseEntity<Map<Double, Double>> calculateInterestSumByInterestRate() {
		Map<Double, Double> result = loanService.calculateInterestSumByInterestRate();
		return ResponseEntity.ok(result);
	}

	@GetMapping("/penaltySumByLender")
	public ResponseEntity<Map<String, Double>> calculatePenaltySumByLender() {
		Map<String, Double> result = loanService.calculatePenaltySumByLender();
		return ResponseEntity.ok(result);
	}
}
