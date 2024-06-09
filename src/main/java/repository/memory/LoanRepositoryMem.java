/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.memory;

import entities.Loan;
import entities.Member;
import repository.loanManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author USER
 */
public class LoanRepositoryMem implements loanManagement {
    private Map<String, Loan> Loans = new HashMap<>();

    @Override
    public Loan addLoan(String loanID, String memberID, String vehicleID) {
        return Loans.put(loanID,new Loan(loanID,memberID,vehicleID));
    }

    @Override
    public Loan deleteLoan(Loan l) {
        try {
            Loans.remove(l.getLoanID(),l);
        }catch (Exception e){
            return null;
        }
        return l;
    }

    @Override
    public Loan findLoan(String loanID) {
        return Loans.get(loanID);
    }

    @Override
    public Loan updateLoan(Loan l) {
        return Loans.replace(l.getLoanID(),l);
    }

    @Override
    public Stream<Loan> getAllLoan() {
        return Loans.values().stream();
    }
}
