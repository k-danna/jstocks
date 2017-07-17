
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
    }

    public void notify(Trade trade) {
        String tmp = this.name + ": BUY " + trade.ticker;
        System.out.println(trade.notifyString());
    }

    public boolean likesTrade(Trade trade) {
        //enforce user preferences for trade
        //risk, not enough cash, etc
        if (trade.shares < 1.0) return false;
        return true;
    }

    public void takeloss(String ticker, double price) {

    }

    void buy(Trade trade) {
            this.buyingPower -= trade.cost;

            //cost of trade
            this.buyingPower -= this.tradeCost;
            this.netWorth -= this.tradeCost;
            //add object to open trades list
            openPositions.add(trade);

            //debug
            //System.out.println("[*] bought " + trade.ticker + " "
            //        + trade.numShares
            //        + " at " + trade.sharePrice); //+ " for "
            //        //+ trade.totalCost + " cost");
    }

    void sell(Trade trade) {
        //no short selling, only closing positions
        //close the trade
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
                this.buyingPower -= tradeCost;
                this.netWorth -= tradeCost;

                //remove from list
                iter.remove();
                
                if (profit > 0) ++this.goodTrades;
                ++this.numTrades;

                //debug
                String sym = (profit > 0) ? "+" : "-";
                //System.out.println("[" + sym + "] gain " 
                //        + trade.ticker + " "
                //        + open.numShares + " at " + trade.sharePrice 
                //        + " for " + profit + " profit");
                System.out.println("[" + sym + "] " 
                        + trade.ticker + " " + profit);
            }
        }
    }

    public void execute(Trade trade) {
        //DEBUG
        //System.out.println(trade.notifyString());

        switch (trade.action) {
            case "BUY":     
                this.buy(trade);
                break;
            case "SELL":
                this.sell(trade);
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
        System.out.println("\nstart networth: " + this.startWorth
                + "\nend networth: " + this.netWorth
                + "\ntotal profit: " + profit
                + "\nreturn on investment: " + roi + "%"
                + "\ngood trades: " + this.goodTrades + "/"
                + this.numTrades 
                + "\nsuccess: " + tradePercent + "%");
    }

}

