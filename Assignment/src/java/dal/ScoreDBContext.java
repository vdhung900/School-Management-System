/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Category;
import entity.Point;
import entity.Score;
import entity.Student;
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
public class ScoreDBContext extends DBContext<Score> {

    public ArrayList<Score> getScoreByStudentIDandSubject(int sid, int suid) {
        ArrayList<Score> scores = new ArrayList<>();
        try {
            String sql = "select c.cid,c.cname,p.pid,p.pname,p.weight,sc.scoreid,sc.value,su.suid,su.suname,g.gid,g.gname,s.sid,s.sname,s.member,s.img\n"
                    + "from Score sc\n"
                    + "inner join Point p on p.pid = sc.pid\n"
                    + "inner join Category c on c.cid = p.cid\n"
                    + "inner join Subject su on su.suid = p.suid\n"
                    + "inner join StudentGroup g on g.gid = sc.gid\n"
                    + "inner join Enrollment e on e.gid = g.gid\n"
                    + "inner join Student s on s.sid = e.sid and s.sid = sc.sid\n"
                    + "where s.sid = ? and su.suid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sid);
            stm.setInt(2, suid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Point p = new Point();
                Category c = new Category();
                Subject su = new Subject();
                StudentGroup g = new StudentGroup();
                Student s = new Student();
                Score score = new Score();

                c.setId(rs.getInt("cid"));
                c.setName(rs.getString("cname"));

                su.setId(rs.getInt("suid"));
                su.setName(rs.getString("suname"));

                p.setId(rs.getInt("pid"));
                p.setName(rs.getString("pname"));
                p.setWeight(rs.getDouble("weight"));
                p.setCategory(c);
                p.setSubject(su);

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));

                s.setId(rs.getInt("sid"));
                s.setName(rs.getString("sname"));
                s.setMember(rs.getString("member"));

                score.setId(rs.getInt("scoreid"));
                score.setValue(rs.getDouble("value"));
                score.setGroup(g);
                score.setPoint(p);
                score.setStudent(s);
                
                scores.add(score);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return scores;
    }

    @Override
    public ArrayList<Score> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Score entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Score entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Score entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Score get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
