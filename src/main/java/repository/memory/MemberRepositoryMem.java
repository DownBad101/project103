/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.memory;

import entities.Loan;
import entities.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import entities.Vehicle;
import repository.memberManagement;
import services.memberServices;

/**
 *
 * @author USER
 */
public class MemberRepositoryMem implements memberManagement {

    private Map<String,Member> members = new HashMap<>();


    @Override
    public Member addMember(String memberID, String memberName, String memberTel) {
       return members.put(memberID,new Member(memberID,memberName,memberTel));
    }
    public Member deleteMember(Member m){
        try {
            members.remove(m.getMemberID(),m);
        }catch (Exception e){
            return null;
        }
        return m;
    }

    @Override
    public Member findMember(String memberID) {
        return members.get(memberID);
    }

    @Override
    public Member updateMember(Member m) {
        return members.replace(m.getMemberID(),m);
    }
    @Override
    public Stream<Member> getAllMember() {
        return members.values().stream();
    }

    

}
