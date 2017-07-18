
package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class User {

    //user info
    String name;
    double tradeCost;
    double buyingPower;
    double netWorth;
    List <Trade> openPositions = new ArrayList<Trade>();

    //user preferences (all percentages)
    double flex; //accept 12% risk even if maxrisk is 10%
    double maxRisk;
    double minConfidence;
    double minProfit;

    //stats
    double startWorth;
    int goodTrades;
    int numTrades;
    int buys;
    int sells;
    double maxloss;
    double maxgain;
    double avgprofit;

    public User(String name, double tradeCost, double buyingPower,
            double netWorth, double flex, double maxRisk, 
            double minConfidence, double minProfit) {
        this.name = name;
        this.tradeCost = tradeCost;
        this.buyingPower = buyingPower;
        this.netWorth = netWorth;
        this.flex = flex;
        this.maxRisk = maxRisk;
        this.minConfidence = minConfidence;
        this.minProfit = minProfit;

        this.startWorth = buyingPower;
        this.goodTrades = 0;
        this.numTrades = 0;
        this.buys = 0;
        this.sells = 0;
        this.maxloss = 0;
        this.maxgain = 0;
        this.avgprofit = 0;
    }

    public void notify(Trade trade) {
        String tmp = this.name + ": BUY " + trade.ticker;
        System.out.println(trade.notifyString());
    }

    public boolean likesTrade(Trade trade) {
        //enforce user preferences for trade
        //risk, not enough cash, etc
        if (trade.shares < 1.0) return false;

        //FIXME:
            //only if openPositions contains a trade of SAME ticker
            //error for multiple tickers
        
        //if we already enforced a stoploss
        if (trade.action == "SELL" && this.openPositions.size() == 0)
            return false;

        //only one open position per stock at a time
        for (Trade open : this.openPositions) {
            if (open.ticker == trade.ticker) {
                if (trade.action == "BUY") return false;
            }
        }

        return true;
    }

    public void takeloss(String ticker, double price, String date) {
        //force sell any open positions below stoploss
        Iterator<Trade> iter = openPositions.iterator();
        while (iter.hasNext()) {
            Trade open = iter.next();
            if (open.ticker == ticker && price < open.stoploss) {
                double gain = open.shares * price;
                double profit = gain - open.cost;
                
                //gains
                this.buyingPower += gain;
                this.netWorth += profit;
                
                //cost of trade
                this.buyingPower -= this.tradeCost;
                this.netWorth -= this.tradeCost;

                //remove from list
                iter.remove();
                
                //update stats
                if (profit > 0) ++this.goodTrades;
                ++this.numTrades;
                ++this.sells;
                if (profit > this.maxgain) this.maxgain = profit;
                if (profit < this.maxloss) this.maxloss = profit;

                //debug
                String sym = (profit > 0) ? "+" : "-";
                //System.out.println("[" + sym + "] gain " 
                //        + trade.ticker + " "
                //        + open.numShares + " at " + trade.sharePrice 
                //        + " for " + profit + " profit");
                System.out.println("[" + sym + "] STOPLOSS (" + date + ") " 
                        + ticker + " @" + price + ": " + profit);
            }
        }
    }

    void buy(Trade trade, String date) {
            this.buyingPower -= trade.cost;

            //cost of trade
            this.buyingPower -= this.tradeCost;
            this.netWorth -= this.tradeCost;
            //add object to open trades list
            openPositions.add(trade);
            ++this.buys;

            //debug
            //System.out.println("[*] bought " + trade.ticker + " ("
            //        + date + ") " + trade.numShares
            //        + " at " + trade.sharePrice); //+ " for "
            //        //+ trade.totalCost + " cost");
    }

    void sell(Trade trade, String date) {
        //no short selling, only closing positions
        Iterator<Trade> iter = openPositions.iterator();
        while (iter.hasNext()) {
            Trade open = iter.next();
            if (open.ticker == trade.ticker) {
                double gain = open.shares * trade.entry;
                double profit = gain - open.cost;
                
                //gains
                this.buyingPower += gain;
                this.netWorth += profit;
                
                //cost of trade
                this.buyingPower -= this.tradeCost;
                this.netWorth -= this.tradeCost;

                //remove from list
                iter.remove();
                
                //update stats
                ++this.sells;
                if (profit > 0) ++this.goodTrades;
                ++this.numTrades;
                if (profit > this.maxgain) this.maxgain = profit;
                if (profit < this.maxloss) this.maxloss = profit;
                this.avgprofit = (profit + (this.numTrades - 1) 
                    * this.avgprofit) / (this.numTrades);

                //debug
                String sym = (profit > 0) ? "+" : "-";
                //System.out.println("[" + sym + "] gain " 
                //        + trade.ticker + " (" + date + ") "
                //        + open.numShares + " at " + trade.sharePrice 
                //        + " for " + profit + " profit");
                System.out.printf("[%s] %-4s %-10s %f\n", sym, 
                        trade.ticker, date, profit);
            }
        }
    }

    public void execute(Trade trade, String date) {
        //DEBUG
        //System.out.println(trade.notifyString());

        switch (trade.action) {
            case "BUY":     
                this.buy(trade, date);
                break;
            case "SELL":
                this.sell(trade, date);
                break;
            default:
                System.out.println("[-] unknown action");
                //System.exit(1);
        }
    }

    public void printStats() {
        double profit = this.netWorth - this.startWorth;
        double roi = (profit / this.startWorth) * 100;
        double tradePercent = ((double)this.goodTrades / this.numTrades) 
                * 100;
        System.out.println(
                "\nstart networth: " + this.startWorth
                + "\nend networth: " + this.netWorth
                + "\ntotal profit: " + profit
                + "\nreturn on investment: " + roi + "%"
                + "\n"
                + "\nbuys vs sells: " + this.buys + ":" + this.sells
                + "\nmax loss: " + this.maxloss
                + "\nmax gain: " + this.maxgain
                + "\navg profit: " + this.avgprofit
                + "\navg2 profit: " + profit / this.numTrades
                + "\ngood trades: " + this.goodTrades + "/"
                + this.numTrades 
                + "\nsuccess: " + tradePercent + "%");
    }

}

