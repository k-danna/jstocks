
package src;

//import src.Trade;

class User {

    //user info
    String name;
    double tradeCost;
    double buyingPower;
    double netWorth;

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

}

class Trade {

    String ticker;
    String position;
    int numShares;
    double sharePrice;
    double stopLoss;
    double totalCost;
    double expectedProfit;
    double maxLoss;
    double riskReward;
    double confidence;

    public Trade(String ticker, String position, int numShares,
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

class Day {
    String ticker;
    int year;
    int month;
    int day;
    double open;
    double high;
    double low;
    double close;
    double adjClose;
    int volume;

    public Day(String ticker, int year, int month, int day, double open, 
            double high, double low, double close, double adjClose, 
            int volume) {
        this.ticker = ticker;
        this.year = year;
        this.month = month;
        this.day = day;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjClose = adjClose;
        this.volume = volume;
    }   

    @Override
    public String toString() {
        return "" + ticker + " Day [year=" + year + ", month=" + month
                + ", day=" + day + ", open=" + open + ", high=" + high 
                + ", low=" + low + ", close=" + close + ", adjClose=" 
                + adjClose + ", volume=" + volume + "]";
    }
}

