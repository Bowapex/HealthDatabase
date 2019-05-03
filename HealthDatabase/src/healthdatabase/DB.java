
package healthdatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


class DB {
    
    Connection conn = null;
    Statement createStatement;
    DatabaseMetaData dbmd;
    
    final String URL = "jdbc:derby:healthDB;create=true";
    final String PASSWORD = "";
    final String USERNAME = "";

    public DB() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött!");
        } catch (SQLException ex) {
            System.out.println("Valami gond van a híd létrehozásánál!" +ex);
        }
        
        if(conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami gond van a createstatement létrehozásánál!" +ex);
            }
        }
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami gond van a metaadatok lekérdezésénél!" +ex);
        }
        try {
            ResultSet rs = dbmd.getTables("null", "APP", "RECORDS", null);
            if(!rs.next()){
                createStatement.execute("create table records (id INT not null primary key GENERATED ALWAYS AS IDENTITY "
                        + "(START WITH 1, INCREMENT BY 1), date varchar(12), time varchar(10), glucose varchar(5), "
                        + "mealType varchar(20), bloodPress varchar(10), pulse varchar(4), weight varchar(4))");
                System.out.println("A tábla sikeresen létrehozva!");
            }
        } catch (SQLException ex) {
            System.out.println("Valami gond van a tábla létrehozásánál!" +ex);
        }
    }
    
    public ArrayList<Records> getAllRecords() {
        String sql = "select * from records";
        ArrayList<Records> getRecords = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            getRecords = new ArrayList<>();

            while (rs.next()) {
                Records actualRecords = new Records(rs.getInt("id"), rs.getString("date"), rs.getString("time"),
                        rs.getString("glucose"), rs.getString("mealType"), rs.getString("bloodPress"),
                        rs.getString("pulse"), rs.getString("weight"));

                getRecords.add(actualRecords);

            }
        } catch (SQLException ex) {
            System.out.println("Valami gond van a rekordok lekérdezésekor!" + ex);
        }
        return getRecords;

    }
    
    public void addRecord(Records record){
        
        String sql ="insert into records (date, time, glucose, mealType, bloodPress, pulse, weight) values"
                + "(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, record.getDate());
            pstm.setString(2, record.getTime());
            pstm.setString(3, record.getGlucose());
            pstm.setString(4, record.getMealType());
            pstm.setString(5, record.getBloodPress());
            pstm.setString(6, record.getPulse());
            pstm.setString(7, record.getWeight());
            pstm.execute();
        } catch (SQLException ex) {
            System.out.println("Valami gond van a rekord hozzáadásakor" + ex);
        }
    
    }
    
    public void updateRecord(Records record){
      try {
            String sql = "update records set date = ?, time = ?, glucose = ?, mealType = ?, "
                    + "bloodPress = ?, pulse = ?, weight = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, record.getDate());
            preparedStatement.setString(2, record.getTime());
            preparedStatement.setString(3, record.getGlucose());
            preparedStatement.setString(4, record.getMealType());
            preparedStatement.setString(5, record.getBloodPress());
            preparedStatement.setString(6, record.getPulse());
            preparedStatement.setString(7, record.getWeight());
            preparedStatement.setInt(8, Integer.parseInt(record.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact update-kor");
            System.out.println(""+ex);
        }
    }
    
    public void removeRecords(Records record){
      try {
            String sql = "delete from records where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(record.getId()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }
    
    public ResultSet chartResult() {

        String sql = "select * from records";
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Valami gond van a chartResult lekérdezésekor" +ex);
        }

        return rs;
    }
}
