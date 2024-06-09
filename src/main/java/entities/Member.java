package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author USER
 */
public class Member implements Serializable {
    private final String memberID;
    private String memberName;
    private String memberTel;


    public Member(String memberID, String memberName, String memberTel) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberTel = memberTel;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }

    @Override
    public String toString() {
        return "Member{" + "memberID=" + memberID + ", memberName=" + memberName + ", memberTel=" + memberTel + '}';
    }


}