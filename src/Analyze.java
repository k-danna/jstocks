
package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Analyze {

    public Analyze() {
    }

    void backtest(User user, List<String> tickers) {
        //FIXME: train correct weights
            //backtest over and over
            //slightly alter weights
            //repeat until max roi is found

        for (int year = 2002; year <= 2017; ++year) 
            for (int month = 0; month <= 12; ++month) 
                for (int day = 0; day <= 31; ++day) 
                    for (String ticker : tickers) 
                        feed(user, ticker, year, month, day);
    }

    void feed(User user, String ticker, int year, int month, int day) {
        String date = year + "-" + month + "-" + day;
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

        //get data
        Calculate calc = new Calculate(year, month, day, ticker);
        if (calc.dne() || calc.today.prev == null) return;

        double price = calc.today.adjclose;
        //double prevprice = calc.today.prev.adjclose;
        //System.out.println(ticker + ": " + year + "-" 
        //        + month + "-" + day);

        //FIXME:
            //update user networth with current stock prices
            //add bollinger bands indicator
        
        //enforce stoploss if needed
        user.takeloss(ticker, price, date);

        //calc all indicators for the day
            //inserts calculations into db
        double[] macd_info = calc.reverseMACD();
        double macd = macd_info[0];
        double macdpred = macd_info[1];
        double macdprev = macd_info[2];
        double macdsignal = macd_info[3];

        //evaluate the stock based on indicators
        double up = 0.0;
        double down = 0.0;

        //macd zero crossover indicator
        if (macd < 0 && macdprev > 0) ++up;
        else if (macd > 0 && macdprev < 0) ++down;

        //macd zero line trend indicator
        if (macd < 0) ++up;
        else if (macd > 0) ++down;

        //macd rises dramatically (overbought indicator)
        if (macd / macdprev > 0) ++up;
        else if (macd / macdprev < 0) ++down;

        //macd signal (9ema) crossover indicator
        if (macd < macdsignal && macdprev > macdsignal) ++up;
        else if (macd > macdsignal && macdprev < macdsignal) ++down;

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

        //max investment
        double shares = Math.floor((user.buyingPower * user.maxRisk) 
                / price);

        //make the trade if it meets user criteria
        double confidence = (up - down) / (up + down);
        double threshold = 0.3;
        //System.out.println(calc.today.date + " " + up + "/" 
        //        + down + ": " + confidence);
        if (confidence > threshold) {
            //create buy trade
            Trade trade = new Trade(ticker, "BUY", shares, price, 
                    macdpred, confidence);

            //enforce user limits
            if (user.likesTrade(trade)) {
                //user.notify(trade);
                user.execute(trade, date);
            }
        }
        if (confidence < -threshold) {
            //create sell trade (no shorting, only sells open positions)
            Trade trade = new Trade(ticker, "SELL", shares, price, 
                    macdpred, confidence);
            
            //enforce user limite
            if (user.likesTrade(trade)) {
                //user.notify(trade);
                user.execute(trade, date);
            }
        }

    }

}

