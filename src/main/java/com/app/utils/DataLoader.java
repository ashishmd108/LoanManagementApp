package com.app.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.entity.Loan;
import com.app.repository.LoanRepository;


@Component
public class DataLoader implements CommandLineRunner {

	private final LoanRepository loanRepository;

    public DataLoader(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) {
    	Loan loan1 = new Loan("L1", "C1", "LEN1", 10000.0, 10000.0,
                LocalDate.of(2023, 5, 6), 1.0, LocalDate.of(2023, 5, 7), 0.01, "");
        
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000.0, 5000.0,
                LocalDate.of(2023, 1, 6), 1.0, LocalDate.of(2023, 5, 8), 0.01, "");
        
        Loan loan3 = new Loan("L3", "C2", "LEN2", 50000.0, 30000.0,
                LocalDate.of(2023, 4, 4), 2.0, LocalDate.of(2023, 4, 5), 0.02, "");
        
        Loan loan4 = new Loan("L4", "C3", "LEN2", 50000.0, 30000.0,
                LocalDate.of(2023, 4, 4), 2.0, LocalDate.of(2023, 4, 5), 0.02, "");

        loanRepository.saveAll(List.of(loan1, loan2, loan3, loan4));
    }
	

}
