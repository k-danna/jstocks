
package src;

public class Trade {

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

    public String notifyString() {
        //FIXME: move to string formatter
        return this.position + " " + this.ticker + "\n"
                + "shares: " + this.numShares + "\n"
                + "price: " + this.sharePrice + "\n"
                + "stoploss: " + this.stopLoss + "\n"
                + "cost: $" + this.totalCost + "\n"
                + "expected profit: $" + this.expectedProfit + "\n"
                + "max loss: $" + this.maxLoss + "\n"
                + "risk/reward: " + this.riskReward + "\n"
                + "confidence: " + this.confidence + "%\n";
    }

}

