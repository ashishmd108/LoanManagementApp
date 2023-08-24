# LoanManagementApp

-  This app is develped using  Java8 and springBoot . Also JUNIT test cases are provided for necessary and important methods.  

- As soon as application starts 4 records are saved in h2 data base . To see h2 data base 
    http://localhost:3644/h2-console  
    can be used.

- Following URLs are given to test application in springboot

1. Add a Loan:
   - Method: POST
   - Endpoint: http://localhost:3644/loans
  
   -  JSON object can be sent using postman
       {
        "loanId": "L11",
        "customerId": "C11",
        "lenderId": "LEN11",
        "amount": 10000.0,
        "remainingAmount": 10000.0,
        "paymentDate": "2023-05-06",
        "interestRatePerDay": 1.0,
        "dueDate": "2023-05-07",
        "penaltyPerDay": 0.01,
        "cancel": ""
    }

   1.a    to test that payment date should not be greater than due date following JSON object is provided.
            {
        "loanId": "L11",
        "customerId": "C11",
        "lenderId": "LEN11",
        "amount": 10000.0,
        "remainingAmount": 10000.0,
        "paymentDate": "2023-06-06",
        "interestRatePerDay": 1.0,
        "dueDate": "2023-05-07",
        "penaltyPerDay": 0.01,
        "cancel": ""
    }
 After passing this exception is thrown by app " payment date is not greater than due date"
   

3. Get All Loans:
   - Method: GET
   - Endpoint: http://localhost:3644/loans

4. Get Loan By ID:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/{loanId}
   - Example: http://localhost:3644/loans/L1

5. Get Loans By Lender ID:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/lender/{lenderId}
   - Example: http://localhost:3644/loans/lender/LEN1

6. Get Loans By Interest Rate:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/interestRate/{interestRate}
   - Example: http://localhost:3644/loans/interestRate/1

7. Get Loans By Customer ID:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/customer/{customerId}`
   - Example: http://localhost:3644/loans/customer/C2

8. Calculate Remaining Amount Sum By Lender:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/remainingAmountSumByLender

9. Calculate Remaining Amount Sum By Customer ID:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/remainingAmountSumByCustomerId

10. Calculate Interest Sum By Interest Rate:
   - Method: GET
   - Endpoint: http://localhost:3644/loans/interestSumByInterestRate

11. Calculate Penalty Sum By Lender:
    - Method: GET
    - Endpoint: http://localhost:3644/loans/penaltySumByLender

12. Also JUNIT test cases are also written


Application is developed for following requirement

  Problem Statement
There is a scenario where thousands of loans are flowing into one store, assume any way of transmission of Loans. We need to create a one loan store, which store the loans in the following order
Loan ID	Customer Id	Lender Id	Amount	Remaining Amount	Payment Date	Interest Per Day(%)	Due Date	Penalty/Day
(%Day)	Cancel
L1	C1	LEN1	10000	10000	05/06/2023	1	05/07/2023	0.01%	
L2	C1	LEN1	20000	5000	01/06/2023	1	05/08/2023	0.01%	
L3	C2	LEN2	50000	30000	04/04/2023	2	04/05/2023	0.02 %	
L4	C3	LEN2	50000	30000	04/04/2023	2	04/05/2023	0.02 %	

There are couple of requirement/validation
1.	The payment date can’t be greater than the Due Date. If its greater we have to reject the Loan and thrown the exception
2.	We need to write an aggregation on the remaining amount, Interest and Penalty Group by Lender, Group by Interest and Group by Customer ID.
3.	If the Loan crosses the due date, it should write an alert in the log message.




