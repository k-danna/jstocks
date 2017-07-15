
package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/*
 * https://github.com/xerial/sqlite-jdbc/blob/master/Usage.md
 *
 * DB tables on a per-ticker bases
 *      historical data for a ticker
 *          date, 
 *          prices (open, close, etc),
 *          indicators (macd, etc)
 *      user info
 *          fields in user obj
 *          open positions
 *      trade history
 *          all trades made by user
 *      open positions
 *
 */

public class Database {

    int timeout;
    Connection conn = null;

    Database() {
        String path = "temp.db";
        //String path = "memory"; //DEBUG: dont write to disk
        this.timeout = 5;
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + path);
        }
        catch (SQLException e) {
            //out of mem error = db not found
            System.out.println(e);
        }
    }

    public ResultSet query(String statement) {
        //System.out.println(statement);
        try {
            Statement s = this.conn.createStatement();
            s.setQueryTimeout(this.timeout);
            ResultSet rs = s.executeQuery(statement);
            return rs;
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void update(String statement) {
        //System.out.println(statement);
        try {
            Statement s = this.conn.createStatement();
            s.executeUpdate(statement);
            s.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void disconnect() {
        try {
            this.conn.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

}

