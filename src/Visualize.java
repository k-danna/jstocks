
package src;

//import java.util.List;
//import java.util.ArrayList;
//import java.util.Collections;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Visualize {

    ResultSet rs;

    public Visualize(String ticker) {
        System.out.println(ticker);

        //query the data
        Database db = new Database();
        this.rs = db.query("select * from " + ticker);

        try {
            while (rs.next()) {
                //get all fields

                //store in better format (arraylist?)
            }
        } 
        catch (SQLException e) { return; }

        db.disconnect();

    }

    void plot(double[][] lines) {
        //plot specified lines
    }

}

