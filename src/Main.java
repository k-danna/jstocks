
package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    static User getUser() {
        //query db and return new user obj
        
        //or
        //create new user table
        //insert data into table

        //DEBUG: create user for testing
        String name = "testuser";
        double cash = 10000.00;
        double cost = 7.00;
        double risk = 0.3;
        double conf = 0.7;
        double prof = 100.00;
        double flex = 0.1;
        return new User(name, cost, cash, cash, flex, risk, 
                conf, prof);
    }

    public static void main(String args[]) {

        //get the user state from db or create new
        User user = getUser();

        //take any commands (add money, change params, etc)

        //test on historical data
        //FIXME: multiple tickers fail --> multiple sells/buys
        List<String> tickers = Arrays.asList("AAPl","NFLX", "NEE", "FNB");
        //List<String> tickers = Arrays.asList("NFLX");
        //List<String> tickers = Arrays.asList("AAPL");
        //List<String> tickers = Arrays.asList("FNB");
        //List<String> tickers = Arrays.asList("NEE");

        //DEBUG: this is for backtest testing
            //should auto update in feed
        //LoadData loader = new LoadData();
        //loader.update(tickers);

        //test with historical data
        Analyze analyzer = new Analyze();
        analyzer.backtest(user, tickers);

        //output some info
        user.printStats();

        //plot specified calculations and price data for a ticker
        //Visualize visualizer = new Visualize("NFLX");

    }
}

