/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Lecturer;
import entity.Lesson;
import entity.Room;
import entity.StudentGroup;
import entity.Subject;
import entity.TimeSlot;
import java.util.ArrayList;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vdhung
 */
public class LessonDBContext extends DBContext<Lesson> {

    public ArrayList<Lesson> getLessonBy(int lid, Date from, Date to) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            String sql = "select ls.leid,ls.date,ls.isAttended,l.lid,l.lname,g.gid,g.gname,r.rid,r.rname,t.tid,t.tname,s.suid,s.suname\n"
                    + "from Lesson ls\n"
                    + "inner join Lecturer l on ls.lid = l.lid\n"
                    + "inner join StudentGroup g on ls.gid = g.gid\n"
                    + "inner join Room r on ls.rid = r.rid\n"
                    + "inner join TimeSlot t on ls.tid = t.tid\n"
                    + "inner join Subject s on g.suid = s.suid\n"
                    + "where l.lid = ? and ls.date >= ? and ls.date <= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lid);
            stm.setDate(2, from);
            stm.setDate(3, to);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson le = new Lesson();
                StudentGroup g = new StudentGroup();
                Subject sub = new Subject();
                Lecturer l = new Lecturer();
                Room r = new Room();
                TimeSlot slot = new TimeSlot();
                le.setId(rs.getInt("leid"));
                le.setDate(rs.getDate("date"));
                le.setAttended(rs.getBoolean("isAttended"));

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                sub.setId(rs.getInt("suid"));
                sub.setName(rs.getString("suname"));
                g.setSuid(sub);
                le.setGroup(g);

                slot.setId(rs.getInt("tid"));
                slot.setName(rs.getString("tname"));
                le.setSlot(slot);

                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                le.setRoom(r);

                l.setId(lid);
                l.setName(rs.getString("lname"));
                le.setLecturer(l);

                lessons.add(le);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lessons;
    }

    @Override
    public ArrayList<Lesson> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Lesson entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Lesson entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Lesson entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Lesson get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
