/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Loan implements Serializable {
    private final String loanID;
    private String memberID;
    private String vehicleID;

    public Loan(String loanID, String memberID, String vehicleID) {
        this.loanID = loanID;
        this.memberID = memberID;
        this.vehicleID = vehicleID;
    }

    public String getLoanID() {
        return loanID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanID='" + loanID + '\'' +
                ", memberID='" + memberID + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                '}';
    }
}
