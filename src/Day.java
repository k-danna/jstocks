
package src;

//import java.util.List;
//import java.util.ArrayList;
//import java.util.Iterator;

public class Day {

    String ticker;
    int year;
    int month;
    int day;
    String date;

    double open;
    double high;
    double low;
    double close;
    double adjclose;
    int volume;

    Day prev;

    public Day(String ticker, int year, int month, int day, double open, 
            double high, double low, double close, double adjclose, 
            int volume, Day prev) {
        this.ticker = ticker;
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = year + "-" + month + "-" + day;

        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjclose = adjclose;
        this.volume = volume;

        this.prev = prev;

    }

}

