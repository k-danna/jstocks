
package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Analyze {

    public Analyze() {
    }

    void backtest(User user, List<String> tickers) {
        //FIXME: days dont line up for multiple tickers
            //yes they do?

        for (int year = 2002; year <= 2017; ++year) 
            for (int month = 0; month <= 12; ++month) 
                for (int day = 0; day <= 31; ++day) 
                    for (String ticker : tickers) 
                        feed(user, ticker, year, month, day);
    }

    void feed(User user, String ticker, int year, int month, int day) {
        //FIXME:
            //goal (this can also be incorporated into alternate)
                //calc all indicators each day
                //backtest to create data/train the net
                //total movement/confidence is predicted
                //lstm predictor also
            //alternate
                //calc all indicators each day
                //backtest each day
                    //trade for each indicator
                        //also for each combination
                    //sum_n (indicator_i * weight_i)
                    //adjust weights based on profit/indicators used
                //each day predict confidence function
                    //predict up, down
                //higher confidence = better trade

        //FIXME: update db to current day
        //LoadData loader = new LoadData(); //connect to db
        //loader.update();

        //FIXME:
            //update user networth with current stock prices
            //enforce stoploss

        //calc all indicators for the day
            //inserts calculations into db
        Calculate calc = new Calculate(year, month, day, ticker);
        if (calc.dne() || calc.today.prev == null) return;

        double price = calc.today.adjclose;
        //double prevprice = calc.today.prev.adjclose;

        //System.out.println(ticker + ": " + year + "-" 
        //        + month + "-" + day);

        double[] macd_info = calc.reverseMACD();
        double macd = macd_info[0];
        double macdpred = macd_info[1];
        double macdprev = macd_info[2];

        //up
            //macd crosses up over 9ema
            //macd below zero
        //down
            //macd crosses down over 9ema
            //macd rises dramatically (overbought)
            //macd above zero
        //end of trend
            //price diverges from macd


        //evaluate the stock independent of user
            //quantify calculations, percentage confidence value, etc
            //indicators sum to percent chance up/down
            //give predicton
                //timeframe and price gain/drop
                //distribution over the timeframe instead of set value?

        //read news
        
        //predict
        //design optimal trade
            //apply user info
        //if it meets criteria
            //notify user of good trade
        //double diff = macdpred - price;
        double nshares = Math.floor((user.buyingPower * user.maxRisk) 
                / price);
        if (nshares < 1.0) nshares = 0.0;

        //macd crossover indicator
        if (macd < 0 && macdprev > 0 && nshares != 0) {
            //invest 10% of total 
            double stoploss = price - 1;
            double totalCost = price * nshares;
            double expProfit = macdpred * nshares - price * nshares;
            double maxLoss = price * nshares - stoploss * nshares;
            double riskReward = 0.0;
            double confidence = 0.0;
            Trade trade = new Trade(ticker, "BUY", nshares, price, 
                    stoploss, totalCost, expProfit, maxLoss, 
                    riskReward, confidence);
            //user.notify(trade);
            user.execute(trade);
        }
        else if (macd > 0 && macdprev < 0 && nshares != 0 
                && user.openPositions.size() > 0) {
            //sell current position
            double stoploss = price - 1;
            double totalCost = price * nshares;
            double expProfit = macdpred * nshares - price * nshares;
            double maxLoss = price * nshares - stoploss * nshares;
            double riskReward = 0.0;
            double confidence = 0.0;
            Trade trade = new Trade(ticker, "SELL", nshares, price, 
                    stoploss, totalCost, expProfit, maxLoss, 
                    riskReward, confidence);
            //user.notify(trade);
            user.execute(trade);
        }

    }

}

