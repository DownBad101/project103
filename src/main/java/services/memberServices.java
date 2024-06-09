package services;

import entities.Loan;
import entities.Member;
import entities.Vehicle;
import repository.loanManagement;
import repository.memberManagement;
import repository.vehicleManagement;

import java.util.stream.Stream;

public class memberServices {
    private final memberManagement memberRepo;
    private final loanManagement loanRepo;
    private final vehicleManagement vehicleRepo;

    public memberServices(memberManagement memberRepo, loanManagement loanRepo, vehicleManagement vehicleRepo) {
        this.memberRepo = memberRepo;
        this.loanRepo = loanRepo;
        this.vehicleRepo = vehicleRepo;
    }

    public Member addMember(String memberID, String memberName, String memberTel){
        if (memberID != null || memberName != null || memberTel != null){
            return memberRepo.addMember(memberID,memberName,memberTel);
        }
        return null;
    }
    public Member deleteMember(String memberID){
       Member member = memberRepo.findMember(memberID);
       if(member != null || memberID != null){
           return memberRepo.deleteMember(member);
       }
       return null;
    }
    public Member findMember(String memberID){
        if (memberID != null){
            return memberRepo.findMember(memberID);
        }
        return null;
    }
    public Member updateMember(String memberID,String memberName,String memberTel){
        Member member = memberRepo.findMember(memberID);
        if (member != null || memberID != null || memberName != null || memberTel != null){
            Member m = new Member(memberID,memberName,memberTel);
            return memberRepo.updateMember(m);
        }
        return null;
    }
    public Stream<Member> getAllMember(){
        return memberRepo.getAllMember();
    }
    public Loan addLoan(String loanID, String memberID, String vehicleID){
        if (loanID != null || memberID != null || vehicleID != null){
            return loanRepo.addLoan(loanID,memberID,vehicleID);
        }
        return null;
    }
    public Loan deleteLoan(String loanID){
        Loan loan = loanRepo.findLoan(loanID);
        if (loan != null || loanID != null){
            return loanRepo.deleteLoan(loan);
        }
        return null;
    }
    public Loan findLoan(String loanID){
        if (loanID != null){
            return loanRepo.findLoan(loanID);
        }
        return null;
    }
    public Loan updateLoan(String loanID, String memberID, String vehicleID){
        Loan loan = loanRepo.findLoan(loanID);
        if (loan != null || loanID != null || memberID != null || vehicleID != null){
            Loan l = new Loan(loanID,memberID,vehicleID);
            return loanRepo.updateLoan(l);
        }
        return null;
    }
    public Stream<Loan> getAllLoan(){
        return loanRepo.getAllLoan();
    }
    public Vehicle addVehicle(String vehicleId, String vehicleName, String vehicleType){
        if (vehicleId != null || vehicleName != null || vehicleType != null){
            return vehicleRepo.addVehicle(vehicleId,vehicleName,vehicleType);
        }
        return null;
    }
    public Vehicle deleteVehicle(String vehicleID){
        Vehicle vehicle = vehicleRepo.findByVehicleID(vehicleID);
        if (vehicle != null || vehicleID != null){
            return vehicleRepo.deleteVehicle(vehicle);
        }
        return null;
    }
    public Vehicle findByVehicleID(String vehicleID){
        if (vehicleID != null){
            return vehicleRepo.findByVehicleID(vehicleID);
        }
        return null;
    }

    public Vehicle updateVehicle(String vehicleId, String vehicleName, String vehicleType){
        Vehicle vehicle = vehicleRepo.findByVehicleID(vehicleId);
        if (vehicle != null || vehicleId != null || vehicleName != null || vehicleType != null){
            Vehicle v = new Vehicle(vehicleId,vehicleName,vehicleType);
            return vehicleRepo.updateVehicle(v);
        }
        return null;
    }
    public Stream<Vehicle> getAllVehicle(){
        return vehicleRepo.getAllVehicle();
    }


}
