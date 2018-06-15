
package src;

//import java.util.List;
//import java.util.ArrayList;
//import java.util.Collections;

//https://stackoverflow.com/questions/18789343/draw-a-multiple-plot-with-jfreechart-bar-xy

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.JFreeChart;

public class Visualize {

    String ticker;

    public Visualize(String ticker) {
        System.out.println("[*] visualizing: " + ticker);
        this.ticker = ticker;
        //create blank graph object
    }

    void add(String col) {
        System.out.println("[*] adding: " + col);

        //query the data
        Database db = new Database();
        ResultSet rs = db.query("select " + col + " from " + this.ticker);
        try {
            while (rs.next()) {
                //get all fields
                //add to dataset
            }
        } 
        catch (SQLException e) { return; }

        db.disconnect();

        //add to graph
    }

    void render() {
        //render the graph
    }

}

