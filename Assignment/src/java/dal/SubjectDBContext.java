/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.StudentGroup;
import entity.Subject;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vdhung
 */
public class SubjectDBContext extends DBContext<Subject> {

    public ArrayList<StudentGroup> getSubjectByLecturerID(int lid) {
        ArrayList<StudentGroup> groups = new ArrayList<>();
        try {
            String sql = "select su.suid,su.suname,g.gid,g.gname\n"
                    + "from Subject su\n"
                    + "inner join StudentGroup g on g.suid = su.suid\n"
                    + "inner join Lecturer l on l.lid = g.lid\n"
                    + "where l.lid = "+lid;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject su = new Subject();
                su.setId(rs.getInt("suid"));
                su.setName(rs.getString("suname"));
                
                StudentGroup g = new StudentGroup();
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                g.setSuid(su);
                
                groups.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }

    public ArrayList<Subject> getSubjectByStudentID(int sid) {
        ArrayList<Subject> subjects = new ArrayList<>();
        try {
            String sql = "select su.suid,su.suname\n"
                    + "from Subject su\n"
                    + "inner join StudentGroup g on g.suid = su.suid\n"
                    + "inner join Enrollment e on e.gid = g.gid\n"
                    + "inner join Student s on s.sid = e.sid\n"
                    + "where s.sid = " + sid;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Subject su = new Subject();
                su.setId(rs.getInt("suid"));
                su.setName(rs.getString("suname"));
                subjects.add(su);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SubjectDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjects;
    }

    @Override
    public ArrayList<Subject> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Subject entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Subject entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Subject entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Subject get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
