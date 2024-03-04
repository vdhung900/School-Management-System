/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author vdhung
 */
public class StudentGroup {
    private int id;
    private String name;
    private Lecturer lid;
    private Subject suid;
    private ArrayList<Student> students = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lecturer getLid() {
        return lid;
    }

    public void setLid(Lecturer lid) {
        this.lid = lid;
    }

    public Subject getSuid() {
        return suid;
    }

    public void setSuid(Subject suid) {
        this.suid = suid;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
    
}
