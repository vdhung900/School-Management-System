/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Attendance;
import entity.Lecturer;
import entity.Lesson;
import entity.Room;
import entity.Student;
import entity.StudentGroup;
import entity.Subject;
import entity.TimeSlot;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vdhung
 */
public class LessonDBContext extends DBContext<Lesson> {

    public ArrayList<Lesson> getLessonByDateLecturer(int lid, LocalDate from, LocalDate to) {
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
            stm.setDate(2, Date.valueOf(from));
            stm.setDate(3, Date.valueOf(to));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson le = new Lesson();
                StudentGroup g = new StudentGroup();
                Subject sub = new Subject();
                Lecturer l = new Lecturer();
                Room r = new Room();
                TimeSlot slot = new TimeSlot();
                le.setId(rs.getInt("leid"));
                le.setDate(rs.getDate("date").toLocalDate());
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

    public ArrayList<Attendance> getAttendencesByLesson(int leid) {
        ArrayList<Attendance> atts = new ArrayList<>();
        try {
            String sql = "SELECT s.sid,s.sname,s.img,s.member,a.aid,a.isPresent,a.description,a.capturetime\n"
                    + "FROM Student s INNER JOIN Enrollment e ON s.sid = e.sid\n"
                    + "INNER JOIN StudentGroup g ON g.gid = e.gid\n"
                    + "INNER JOIN Lesson les ON les.gid = g.gid\n"
                    + "LEFT JOIN Attendance a ON les.leid = a.leid AND a.sid = s.sid\n"
                    + "WHERE les.leid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, leid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();
                Student s = new Student();
                Lesson les = new Lesson();
                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                s.setMember(rs.getString("member"));
                s.setImg(rs.getString("img"));
                att.setStudent(s);

                les.setId(leid);
                att.setLesson(les);

                att.setId(rs.getInt("aid"));
                if (att.getId() != 0) {
                    att.setDescription(rs.getString("description"));
                    att.setPresent(rs.getBoolean("isPresent"));
                    att.setTime(rs.getTimestamp("capturetime"));
                }
                atts.add(att);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atts;
    }

    public boolean checkAttendance(int sid, int leid) {
        boolean isAttendance = false;
        try {
            String sql = "SELECT a.isPresent\n"
                    + "FROM Student s \n"
                    + "INNER JOIN Enrollment e ON s.sid = e.sid\n"
                    + "INNER JOIN StudentGroup g ON g.gid = e.gid\n"
                    + "INNER JOIN Lesson les ON les.gid = g.gid\n"
                    + "LEFT JOIN Attendance a ON les.leid = a.leid AND a.sid = s.sid\n"
                    + "WHERE les.leid = ? and s.sid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, leid);
            stm.setInt(2, sid);
            ResultSet rs = stm.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAttendance;
    }

    public void takeAttendance(int leid, ArrayList<Attendance> atts) {
        try {
            connection.setAutoCommit(false);
            String sql_remove_atts = "DELETE from Attendance WHERE leid = ?";
            PreparedStatement stm_remove_atts = connection.prepareStatement(sql_remove_atts);
            stm_remove_atts.setInt(1, leid);
            stm_remove_atts.executeUpdate();

            String sql_insert_att = "INSERT INTO [Attendance]\n"
                    + "           ([leid]\n"
                    + "           ,[sid]\n"
                    + "           ,[description]\n"
                    + "           ,[isPresent]\n"
                    + "           ,[capturetime])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,GETDATE())";
            for (Attendance att : atts) {
                PreparedStatement stm_insert_att = connection.prepareStatement(sql_insert_att);
                stm_insert_att.setInt(1, leid);
                stm_insert_att.setInt(2, att.getStudent().getId());
                stm_insert_att.setString(3, att.getDescription());
                stm_insert_att.setBoolean(4, att.isPresent());
                stm_insert_att.executeUpdate();
            }

            String sql_update_less = "UPDATE Lesson SET isAttended = 1 WHERE leid = ?";
            PreparedStatement stm_update_less = connection.prepareStatement(sql_update_less);
            stm_update_less.setInt(1, leid);
            stm_update_less.executeUpdate();
            connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(LessonDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(LessonDBContext.class
                        .getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);

            } catch (SQLException ex) {
                Logger.getLogger(LessonDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Lesson> getLessonByDateStudent(int sid, LocalDate from, LocalDate to) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            String sql = "select ls.leid,ls.date,ls.isAttended,l.lid,l.lname,g.gid,g.gname,r.rid,r.rname,t.tid,t.tname,t.tperiod,su.suid,su.suname,s.sid,s.sname,s.img,s.member\n"
                    + "from Lesson ls\n"
                    + "inner join Lecturer l on ls.lid = l.lid\n"
                    + "inner join StudentGroup g on ls.gid = g.gid\n"
                    + "inner join Room r on ls.rid = r.rid\n"
                    + "inner join TimeSlot t on ls.tid = t.tid\n"
                    + "inner join Subject su on g.suid = su.suid\n"
                    + "inner join Enrollment e on e.gid = g.gid\n"
                    + "inner join Student s on s.sid = e.sid\n"
                    + "where s.sid = ? and ls.date >= ? and ls.date <= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            stm.setDate(2, Date.valueOf(from));
            stm.setDate(3, Date.valueOf(to));
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Lesson le = new Lesson();
                StudentGroup g = new StudentGroup();
                Subject sub = new Subject();
                Lecturer l = new Lecturer();
                Room r = new Room();
                TimeSlot slot = new TimeSlot();
                Student s = new Student();
                le.setId(rs.getInt("leid"));
                le.setDate(rs.getDate("date").toLocalDate());
                le.setAttended(rs.getBoolean("isAttended"));

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                sub.setId(rs.getInt("suid"));
                sub.setName(rs.getString("suname"));
                g.setSuid(sub);
                le.setGroup(g);

                slot.setId(rs.getInt("tid"));
                slot.setName(rs.getString("tname"));
                slot.setPeriod(rs.getString("tperiod"));
                le.setSlot(slot);

                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                le.setRoom(r);

                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                le.setLecturer(l);

                s.setId(sid);
                s.setName(rs.getString("sname"));
                s.setMember(rs.getString("member"));
                s.setImg(rs.getString("img"));

                lessons.add(le);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
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
