package com.app.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LoanRequestTest {

    @Test
    public void testGetterAndSetterMethods() {
        LoanRequest loanRequest = new LoanRequest();
        
        loanRequest.setLoanId("L123");
        loanRequest.setCustomerId("C456");
       
        
        assertThat(loanRequest.getLoanId()).isEqualTo("L123");
        assertThat(loanRequest.getCustomerId()).isEqualTo("C456");
        
    }
    
    @Test
    public void testConstructor() {
        LoanRequest loanRequest = new LoanRequest(
            "L123", "C456", "LEN789", 1000.0, 500.0, 
            LocalDate.of(2023, 8, 24), 0.05, 
            LocalDate.of(2023, 8, 30), 0.02, "No"
        );
        
        assertThat(loanRequest.getLoanId()).isEqualTo("L123");
        assertThat(loanRequest.getCustomerId()).isEqualTo("C456");
       
    }
    
   
    
   
}
