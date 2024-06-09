/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.file;

import entities.Loan;
import entities.Member;
import repository.loanManagement;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author USER
 */
public class LoanRepositoryFile implements loanManagement {
    private Map<String, Loan> Loans = new HashMap<>();
    private String PATH = "src\\main\\java\\";
    String filename = PATH + "Loan.dat";

    public LoanRepositoryFile(){
        File file = new File(filename);
        if (file.exists()){
            try (FileInputStream fi = new FileInputStream(filename);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)){
                 Loans = (Map<String, Loan>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            Loans = new HashMap<>();
        }
    }
    public void writeToFile(){
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)){
             oo.writeObject(Loans);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Loan addLoan(String loanID, String memberID, String vehicleID) {
        Loan l = new Loan(loanID,memberID,vehicleID);
        Loans.put(loanID,new Loan(loanID,memberID,vehicleID));
        writeToFile();
        return l;
    }

    @Override
    public Loan deleteLoan(Loan l) {
        try {
            Loans.remove(l.getLoanID(),l);
            writeToFile();
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
        Loans.replace(l.getLoanID(),l);
        writeToFile();
        return l;
    }

    @Override
    public Stream<Loan> getAllLoan() {
        return Loans.values().stream();
    }
}
