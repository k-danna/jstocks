
package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

//import src.BackTest;

public class Main {

    static BackTest backtest = new BackTest();
    //static Predict predictor = new Predict();

    public static void main(String args[]) {
        //create user
        String name = "testuser";
        double cash = 10000.00;
        double cost = 7.00;
        double flex = 0.1;
        double risk = 0.3;
        double conf = 0.7;
        double prof = 100.00;
        User user = new User(name, cost, cash, cash, flex, risk, 
                conf, prof);

        //test on historical data
        List<String> tickers = Arrays.asList("AAPl", "FNB", "NEE", "NFLX");
        backtest.test(user, tickers);

    }
}

