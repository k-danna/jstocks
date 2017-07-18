
package src;

public class Trade {

    String ticker;
    String action;
    double shares;
    double entry;
    double exit;
    double confidence;
    double stoploss;
    double cost;
    double maxloss;
    double profit;
    double riskreward;

    /*
    String ticker;
    String position;
    double numShares;
    double sharePrice;
    double stopLoss;
    double totalCost;
    double expectedProfit;
    double maxLoss;
    double riskReward;
    double confidence;
    */

    public Trade(String ticker, String action, double shares,
            double entry, double exit, double confidence) {
        this.ticker = ticker;
        this.action = action;
        this.shares = shares;
        this.entry = entry;
        this.stoploss = 0.0;
        this.cost = shares * entry;
        this.maxloss = (entry - this.stoploss) * shares;
        this.profit = (exit - entry) * shares;
        this.riskreward = this.profit / this.cost;
        this.confidence = confidence;
    }

    /*
    public Trade(String ticker, String position, double numShares,
            double sharePrice, double stopLoss, double totalCost, 
            double expectedProfit, double maxLoss, double riskReward, 
            double confidence) {
        this.ticker = ticker;
        this.position = position;
        this.numShares = numShares;
        this.sharePrice = sharePrice;
        this.stopLoss = stopLoss;
        this.totalCost = totalCost;
        this.expectedProfit = expectedProfit;
        this.maxLoss = maxLoss;
        this.riskReward = riskReward;
        this.confidence = confidence;
    }
    */

    public String notifyString() {
        //FIXME: move to string formatter
        return this.action + " " + this.ticker + "\n"
                + "shares: " + this.shares + "\n"
                + "price: " + this.entry + "\n"
                + "stoploss: " + this.stoploss + "\n"
                + "cost: $" + this.cost + "\n"
                + "expected profit: $" + this.profit + "\n"
                + "max loss: $" + this.maxloss + "\n"
                + "risk/reward: " + this.riskreward + "\n"
                + "confidence: " + this.confidence + "%\n";
    }

}

