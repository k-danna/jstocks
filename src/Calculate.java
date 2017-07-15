
package src;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Calculate {

    //TODO:
        //add indicators
            //macd other signals
            //relative strength index
            //on-balance volume
            //bollinger bands
            //average true range

    Database db;
    Day today;
    int id;
    int year;
    int month;
    int day;

    public Calculate(int year, int month, int day, String ticker) {
        this.year = year;
        this.month = month;
        this.day = day;
        //get the id for current day
        this.db = new Database();
        ResultSet rsid = this.db.query("select id from " + ticker 
                + " where "
                + "year = " + year + " and "
                + "month = " + month + " and "
                + "day = " + day); 

        //make sure we have data for the day
        try {
            this.id = rsid.getInt("id");
        }
        catch (SQLException e) { this.id = -1; return; }

        //query all historical data before today
        ResultSet rs = this.db.query("select * from " + ticker
                + " where id <= " + this.id);

        //create reverse linked list
        try {
            Day prev = null;
            while (rs.next()) {
                Day temp = new Day(ticker, rs.getInt("year"), 
                        rs.getInt("month"), rs.getInt("day"), 
                        rs.getDouble("open"), rs.getDouble("high"),
                        rs.getDouble("low"), rs.getDouble("close"),
                        rs.getDouble("adjclose"), rs.getInt("volume"), 
                        prev);
                prev = temp;

            }
            this.today = prev; //today is last day in resultset
        }
        //for now fail if any data is missing
        catch (SQLException e) { this.id = -1; return; }

        //DEBUG:
        //printlist();
    
    }

    void printlist() {
        System.out.println("printing list for " + this.id + ": " + this.year
                + "-" + this.month + "-" + this.day);
        Day curr = this.today;
        while (curr != null) {
        System.out.println("    " + curr.date + ": " + curr.adjclose);
            curr = curr.prev;
        }
    }

    boolean dne() {
        return this.id == -1;
    }

    double ema(Day day, double weight) {
        if (day.prev == null) return day.adjclose;
        return day.adjclose * weight + ema(day.prev, weight) * (1 - weight);
    }

    //http://edmond.mires.co/GES816/25-Reversing%20MACD.pdf
    double[] reverseMACD() {
        //FIXME: plot 9 day ema of macd
            //signal to buy
                //macd crosses up over the 9ema
            //signal to sell
                //macd crosses down over the 9ema
            //signal end of current trend
                //price diverges from macd
            //signal of overbought (will return to normal levels)
                //macd rises dramatically (short ema pulls away from long)
            //signal upward momentum
                //macd below zero
            //signal downward momentum
                //macd above zero

        //calc 12, 24, 9 day emas
        //double w9 = 2 / (1 + 9.0);
        double w12 = 2 / (1 + 12.0);
        double w24 = 2 / (1 + 24.0);
        //double ema9 = ema(this.today, w9);
        double ema12 = ema(this.today, w12);
        double ema24 = ema(this.today, w24);

        double macd = ema12 - ema24;
        double pred = (ema12 * w12 - ema24 * w24) / (w12 - w24);
        double prev = macd;

        //DEBUG: verify prediction works
        if (this.today.prev != null) {
            prev = ema(this.today.prev, w12) - ema(this.today.prev, w24);
            double today = this.today.adjclose;
            double yesterday = this.today.prev.adjclose;

            double w11 = 2 / (1 + 11.0);
            double w23 = 2 / (1 + 23.0);
            double price = (ema(this.today.prev, w11) * w12 - 
                ema(this.today.prev, w23) * w24) / (w12 - w24);
            double diff = this.today.adjclose - price;
            double error = (diff / this.today.adjclose) * 100;
            if (error < 0) { error *= -1; }
            if (diff < 0) { diff *= -1; }

            //is prediction in correct direction
            if (pred > yesterday && today > yesterday) {
                //System.out.println(this.today.date);
                //System.out.println("    " + this.today.adjclose + " ?= "
                //        + price);
                //System.out.println("    " + diff + "%");
            }
            else if (pred < yesterday && today < yesterday) {
                //System.out.println(this.today.date);
                //System.out.println("    " + this.today.adjclose + " ?= "
                //        + price);
                //System.out.println("    " + diff + "%");
            }
            else {
                //System.out.println("wrong pred: " + this.today.date);
            }
        }

        //return: macd, macdpred, prevmacd, macdslope
        double[] rtn = {macd, pred, prev};

        /*
        try {
            //insert in db
            this.db.update("update " + this.ticker + " set "
                    + "avg=" + price
                    + ", macd=" + macd
                    + ", ema12=" + emafast
                    + ", ema24=" + emaslow
                    + ", macdpred=" + macdpred
                    + " where id=" + this.id);

            //close connection
            this.db.disconnect();
        }
        catch (SQLException e) { return null; }
        */

        return rtn;
    }

}

