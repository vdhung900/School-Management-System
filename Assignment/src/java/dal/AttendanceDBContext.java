/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Attendance;
import entity.Lesson;
import entity.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vdhung
 */
public class AttendanceDBContext extends DBContext<Attendance> {

    public ArrayList<Attendance> getAttendanceByStudent(int sid) {
        ArrayList<Attendance> atts = new ArrayList<>();
        try {
            String sql = "select s.sid,s.sname,le.date,le.leid,a.aid,a.capturetime,a.description,a.isPresent\n"
                    + "from Lesson le\n"
                    + "inner join StudentGroup g on le.gid = g.gid\n"
                    + "inner join Enrollment e on e.gid = g.gid\n"
                    + "inner join Student s on s.sid = e.sid\n"
                    + "left join Attendance a on a.leid = le.leid and a.sid = s.sid\n"
                    + "where s.sid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                Attendance att = new Attendance();
                Lesson le = new Lesson();
                
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                
                le.setId(rs.getInt("leid"));
                le.setDate(rs.getDate("date").toLocalDate());
                
                att.setId(rs.getInt("aid"));
                att.setTime(rs.getTime("capturetime"));
                att.setDescription(rs.getString("description"));
                att.setPresent(rs.getBoolean("isPresent"));
                att.setLesson(le);
                att.setStudent(s);
                
                atts.add(att);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atts;

    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendance entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
