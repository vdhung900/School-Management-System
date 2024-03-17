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
import org.eclipse.jdt.internal.compiler.lookup.Scope;

/**
 *
 * @author vdhung
 */
public class ScoreDBContext extends DBContext<Score> {

    public ArrayList<Score> getScores(int sid, int suid) {
        ArrayList<Score> scores = new ArrayList<>();
        try {
            String sql = "select sc.scoreid,sc.value,p.pid,p.pname\n"
                    + "from Point p\n"
                    + "inner join Category c on p.cid = c.cid\n"
                    + "inner join Subject su on su.suid = p.suid\n"
                    + "left join Score sc on sc.pid = p.pid\n"
                    + "left join Student s on s.sid =sc.sid\n"
                    + "where su.suid =" + suid + " and s.sid = " + sid;
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                do {
                    Score sc = new Score();
                    sc.setId(rs.getInt("scoreid"));
                    sc.setValue(rs.getDouble("value"));

                    Point p = new Point();
                    p.setId(rs.getInt("pid"));
                    p.setName(rs.getString("pname"));
                    sc.setPoint(p);

                    scores.add(sc);
                } while (rs.next());
            } else {
                scores = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ScoreDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scores;
    }

    public ArrayList<Category> getCategoryByStudentIDandSubject(int sid, int suid) {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            String sql_getcategory = "select c.cid,c.cname,MIN(p.pid) AS pid\n"
                    + "from Category c\n"
                    + "inner join Point p on p.cid = c.cid\n"
                    + "inner join Subject su on su.suid = p.suid\n"
                    + "where su.suid = "+suid+"\n"
                    + "GROUP BY c.cid, c.cname\n"
                    + "order by MIN(p.pid)";
            PreparedStatement stm = connection.prepareStatement(sql_getcategory);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("cid"));
                c.setName(rs.getString("cname"));

                ArrayList<Point> points = new ArrayList<>();
                String sql_getpoint = "select p.pid,p.pname,p.weight\n"
                        + "from Point p\n"
                        + "inner join Category c on p.cid = c.cid\n"
                        + "left join Subject su on su.suid = p.suid\n"
                        + "where su.suid = " + suid + " and c.cid = " + c.getId();
                PreparedStatement stm_getpoint = connection.prepareStatement(sql_getpoint);
                ResultSet rs_getpoint = stm_getpoint.executeQuery();
                while (rs_getpoint.next()) {
                    Point p = new Point();
                    p.setId(rs_getpoint.getInt("pid"));
                    p.setName(rs_getpoint.getString("pname"));
                    String formattedValue = String.format("%.2f", rs_getpoint.getDouble("weight"));
                    formattedValue = formattedValue.replace(",", ".");
                    double result = Double.parseDouble(formattedValue);
                    p.setWeight(result);
                    points.add(p);
                }
                c.setPoints(points);
                categories.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimeSlotDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
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
