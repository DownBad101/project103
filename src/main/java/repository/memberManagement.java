package repository;

import entities.Loan;
import entities.Member;
import entities.Vehicle;

import java.util.Collection;
import java.util.stream.Stream;

public interface memberManagement {
    Member addMember(String memberID, String memberName, String memberTel);
    Member deleteMember(Member m);
    Member findMember(String memberID);
    Member updateMember(Member m);
    Stream<Member> getAllMember();
}