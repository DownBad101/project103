package repository;

import entities.Loan;
import entities.Member;

import java.util.stream.Stream;

public interface loanManagement {
    Loan addLoan(String loanID, String memberID, String vehicleID);
    Loan deleteLoan(Loan l);
    Loan findLoan(String loanID);
    Loan updateLoan(Loan l);
    Stream<Loan> getAllLoan();
}
