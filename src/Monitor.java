
package src;

//import src.Day;

public class Monitor {

    //static db;
        //db tables
            //full trade history
            //user info
            //ticker info incl. historical, flagged bool
            //open positions

    static Indicator indicator = new Indicator();

    public Monitor() {
    }

    void feed(User user, Day day) {
        //add info to db
            //insert day into sql db

        //do all calculations
            //calc reverse macd, resistance levels

        //update db with the day's calculations

        //evaluate the stock independent of user
            //quantify calculations, percentage confidence value, etc
            //indicators sum to percent chance up/down
            //give predicton
                //timeframe and price gain/drop
                //distribution over the timeframe instead of set value?

        //design optimal trade
            //apply user info
        //if it meets criteria
            //notify user of good trade
        Trade trade = new Trade(day.ticker, "BUY", 42, 33.50, 32.00, 
                42 * 33.50, 12.11, (33.50-32.0) * 42, 12.11/63.0, 0.0);
        if (day.year == 2017 && day.month == 06 && day.day == 30)
            user.notify(trade);
    }

}

