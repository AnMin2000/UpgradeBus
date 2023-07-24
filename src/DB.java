import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DB {
    PreparedStatement pstmt;
    Connection conn = null;

    DB() throws ClassNotFoundException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/bus";
        String dbID = "root";
        String dbPassword = "0000";
        conn = DriverManager.getConnection(dbUrl, dbID, dbPassword);
    }

    public void insert(String name, int number, String[] PrName) throws SQLException, ParseException {
        //name : 테이블 이름, number : 해당 디비 컬럼 갯수, PrName : 디비에 입력 할 값들
        String sql;
        String ValuesVar = "?";

        for (int i = 0; i < number - 1; i++) {
            ValuesVar += ", ?";
        }

        sql = "INSERT INTO " + name + " VALUES (" + ValuesVar + ") ";

        pstmt = conn.prepareStatement(sql);


        for (int i = 0; i < PrName.length; i++) {
            pstmt.setString(i + 1, PrName[i]);
        }
        pstmt.executeUpdate();
    }

    public ResultSet print(String selectName, String tableName, String sqlName, String data, String sqlName2, String data2) throws SQLException {

        String ValuesVar = "?";

        String sql;
        if (sqlName.equals("Null")) {
            sql = "SELECT " + selectName + " FROM " + tableName;
            pstmt = conn.prepareStatement(sql);
        } else if (sqlName2.equals("Null")) {
            sql = "SELECT " + selectName + " FROM " + tableName + " WHERE " + sqlName + " = " + "?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, data);
        } else {
            sql = "SELECT " + selectName + " FROM " + tableName +
                    " WHERE " + sqlName + " =  +  ? and " + sqlName2 + " =  + ?";
//            " = " +  "? and " + sqlName2 + " = " + "?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, data);
            pstmt.setString(2, data2);
        }
        ResultSet rs = pstmt.executeQuery();

        return rs;
    }

    public boolean Overlap(String ID) throws SQLException {
        try {
            String sql = "select * from client where userID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ID);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            //System.out.println(rs.getString(2));
            if (rs.getString(1).equals(ID)) {
                return false;
            }
        } catch (SQLException ex) {
            return true;
        }
        return false;
    }


    public boolean Login(String Id, String Pw) throws SQLException {

            String sql = "select * from client where userID = ? and pwd = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Id);
            pstmt.setString(2, Pw);
            ResultSet rs = pstmt.executeQuery();
            //rs.next();

        if (rs.next()) {
            // Success
            return true;
        } else {
            // Failure
            return false;
        }
    }

    public boolean AdminLogin(String Id, String Pw) throws SQLException {
        try {
            String sql = "select * from admin where adminID = ? and pwd = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Id);
            pstmt.setString(2, Pw);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (!(rs.getString(1)).equals("Null")) {
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "로그인 실패");
            return false;
        }
        return false;
    }

    public boolean checkReserve(int Id) throws SQLException {
        String sql = "select reserveID from reserve where reserveID = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Id);
        ResultSet rs = pstmt.executeQuery();

        int search = -1;
        while (rs.next()) {
            search = rs.getInt(1);
        }
        if (search == -1) {
            return true;
        }
        return false;
    }

    public void updateSeat(String reserveID, String seatID, String busID) throws SQLException {
        String sql = "update seat set reserveID = ? where seatID = ? and busID = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, reserveID);
        pstmt.setString(2, seatID);
        pstmt.setString(3, busID);
        pstmt.executeUpdate();
    }

    public List<String> getReservedSeats() throws SQLException {
        List<String> reservedSeats = new ArrayList<>();

        String query = "SELECT seatID FROM seat WHERE reserveID IS NOT NULL ";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String seatNumber = rs.getString("seatID");
            reservedSeats.add(seatNumber);
        }

        rs.close();
        pstmt.close();

        return reservedSeats;
    }

    public ResultSet join(String vUserID) throws SQLException {
        String sql = " SELECT r.reserveID, startRegion, endRegion, startTime, endTime, STUFF((SELECT ',' + seatID " +
                " FROM seat s " +
                " WHERE s.reserveID = r.reserveID " +
                " FOR XML PATH('')), 1, 1, '') AS seatIDs, CAST(SUM(CAST(price AS INT)) AS VARCHAR) AS total_price " +
                " FROM reserve r " +
                " INNER JOIN timetable t ON r.reserveBusID = t.timeID " +
                " INNER JOIN seat s ON r.reserveID = s.reserveID " +
                " WHERE userID = '" + vUserID + "'" +
                " GROUP BY r.reserveID, startRegion, endRegion, startTime, endTime;";
        System.out.println(sql);


        pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public void Delete(int reserveID) throws SQLException {
        String sql = " DELETE FROM reserve WHERE reserveID = ? ";
        pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, reserveID);
        pstmt.executeUpdate();
    }

    public void timeDelete(String timeId, String busNum, String startRe, String endRe, String startT, String endT) throws SQLException {
        String sql = " Delete From timetable where timeId = ? and busNum = ? and startRegion = ? and endRegion = ? and startTime = ? and endTime = ? ";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, timeId);
        pstmt.setString(2, busNum);
        pstmt.setString(3, startRe);
        pstmt.setString(4, endRe);
        pstmt.setString(5, startT);
        pstmt.setString(6, endT);

        pstmt.executeUpdate();
    }

    public void userDelete(String userId) throws SQLException {
        String sql = " Delete From client where userId = ? ";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, userId);
        pstmt.executeUpdate();
    }

    public Ticket ticket_load(String start, String end, String date) throws SQLException {
        ResultSet rs = null;
        Ticket t = new Ticket();
        String SQL = "SELECT starttime FROM timetable WHERE start = '" + start + "' ";
        System.out.println(SQL);
        pstmt = conn.prepareStatement(SQL);
//            pstmt.setString(0, start);
//            pstmt.setString(0, end);
        rs = pstmt.executeQuery(SQL);

        while (rs.next()) {
            String starttime = rs.getString(1);


            t.insertTicket(starttime);

            return t;
        }
        return null;
    }
}
