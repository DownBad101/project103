/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.file;

import entities.Loan;
import entities.Member;
import entities.Vehicle;
import repository.memberManagement;


import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 *
 * @author USER
 */
public class MemberRepositoryFile implements memberManagement {

    private Map<String,Member> members = new HashMap<>();
    private String PATH = "src\\main\\java\\";
    String filename = PATH + "Member.dat";


    public MemberRepositoryFile(){
        File file = new File(filename);
        if (file.exists()){
            try (FileInputStream fi = new FileInputStream(filename);
                 BufferedInputStream bi = new BufferedInputStream(fi);
                 ObjectInputStream oi = new ObjectInputStream(bi)){
                 members = (Map<String,Member>) oi.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            members = new HashMap<>();
        }
    }
    public void writeToFile(){
        try (FileOutputStream fo = new FileOutputStream(filename);
             BufferedOutputStream bo = new BufferedOutputStream(fo);
             ObjectOutputStream oo = new ObjectOutputStream(bo)){
             oo.writeObject(members);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Member addMember(String memberID, String memberName, String memberTel) {
        Member m = new Member(memberID,memberName,memberTel);
        members.put(memberID,m);
        writeToFile();
        return m;
    }

    public Member deleteMember(Member m){
        try {
            members.remove(m.getMemberID(),m);
            writeToFile();
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
        try {
            members.replace(m.getMemberID(),m);
            writeToFile();
        }catch (Exception e){
            return null;
        }
        return m;
    }
    @Override
    public Stream<Member> getAllMember() {
        return members.values().stream();
    }
}
