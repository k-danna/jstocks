
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
    }

    public void notify(Trade trade) {
        String tmp = this.name + ": BUY " + trade.ticker;
        System.out.println(trade.notifyString());
    }

   public void execute(Trade trade) {
        if (trade.position == "BUY") {
            //invest
            double buyingPower;
            double netWorth;

            this.buyingPower -= trade.totalCost;

            //cost of trade
            this.buyingPower -= tradeCost;
            this.netWorth -= tradeCost;
            //add object to open trades list
            openPositions.add(trade);

            //debug
            System.out.println("[*] bought " + trade.numShares
                    + " at " + trade.sharePrice + " for "
                    + trade.totalCost + " cost");
        }
        else {
            //sell
            Iterator<Trade> iter = openPositions.iterator();
            while (iter.hasNext()) {
                Trade open = iter.next();
                if (open.ticker == trade.ticker) {
                    //close the trade
                    double sold = open.numShares * trade.sharePrice;
                    double profit = open.numShares * trade.sharePrice 
                            - open.numShares * open.sharePrice;
                    this.buyingPower += sold;
                    this.netWorth += profit;
                    
                    //cost of trade
                    this.buyingPower -= tradeCost;
                    this.netWorth -= tradeCost;

                    //remove from list
                    iter.remove();
                    //openPositions.remove(iter.next());
                    
                    //debug
                    String sym = (profit > 0) ? "+" : "-";
                    System.out.println("[" + sym + "] sold " 
                            + open.numShares + " at " + trade.sharePrice 
                            + " for " + profit + " profit");
                }
            }
        }
    }

}

