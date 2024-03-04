/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author vdhung
 */
public class Enrollment {
    private Student sid;
    private StudentGroup gid;

    public Student getSid() {
        return sid;
    }

    public void setSid(Student sid) {
        this.sid = sid;
    }

    public StudentGroup getGid() {
        return gid;
    }

    public void setGid(StudentGroup gid) {
        this.gid = gid;
    }
    
}
