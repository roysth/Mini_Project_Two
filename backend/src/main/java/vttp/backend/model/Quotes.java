package vttp.backend.model;

import java.math.BigDecimal;

public class Quotes {

    private String symbol;
    private BigDecimal lastPrice;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal netChange;
    private BigDecimal totalVolume;
    private BigDecimal fiftyTwoWkHigh;
    private BigDecimal fiftyTwoWkLow;

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public BigDecimal getLastPrice() {
        return lastPrice;
    }
    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }
    public BigDecimal getOpenPrice() {
        return openPrice;
    }
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }
    public BigDecimal getClosePrice() {
        return closePrice;
    }
    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }
    public BigDecimal getHighPrice() {
        return highPrice;
    }
    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }
    public BigDecimal getLowPrice() {
        return lowPrice;
    }
    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }
    public BigDecimal getNetChange() {
        return netChange;
    }
    public void setNetChange(BigDecimal netChange) {
        this.netChange = netChange;
    }
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }
    public BigDecimal getFiftyTwoWkHigh() {
        return fiftyTwoWkHigh;
    }
    public void setFiftyTwoWkHigh(BigDecimal fiftyTwoWkHigh) {
        this.fiftyTwoWkHigh = fiftyTwoWkHigh;
    }
    public BigDecimal getFiftyTwoWkLow() {
        return fiftyTwoWkLow;
    }
    public void setFiftyTwoWkLow(BigDecimal fiftyTwoWkLow) {
        this.fiftyTwoWkLow = fiftyTwoWkLow;
    }
    
}

// RETURNED JSON FILE:
// https://api.tdameritrade.com/v1/marketdata/quotes?apikey=TQO4MGZI7HYDZTGD0LUU3OLCACFIFZM7&symbol=AAPL
// {
//     "AAPL": {
//       "assetType": "EQUITY",
//       "assetMainType": "EQUITY",
//       "cusip": "037833100",
//       "assetSubType": "",
//       "symbol": "AAPL",
//       "description": "Apple Inc. - Common Stock",
//       "bidPrice": 165.21,
//       "bidSize": 400,
//       "bidId": "Q",
//       "askPrice": 165.4,
//       "askSize": 1000,
//       "askId": "Q",
//       "lastPrice": 165.3,
//       "lastSize": 0,
//       "lastId": "P",
//       "openPrice": 166.595,
//       "highPrice": 166.84,
//       "lowPrice": 165.11,
//       "bidTick": " ",
//       "closePrice": 166.17,
//       "netChange": -0.87,
//       "totalVolume": 16210,
//       "quoteTimeInLong": 1680684120216,
//       "tradeTimeInLong": 1680684123878,
//       "mark": 165.4,
//       "exchange": "q",
//       "exchangeName": "NASD",
//       "marginable": true,
//       "shortable": true,
//       "volatility": 0.0253,
//       "digits": 4,
//       "52WkHigh": 178.49,
//       "52WkLow": 124.17,
//       "nAV": 0,
//       "peRatio": 28.2322,
//       "divAmount": 0.92,
//       "divYield": 0.55,
//       "divDate": "2023-02-10 00:00:00.000",
//       "securityStatus": "Normal",
//       "regularMarketLastPrice": 165.63,
//       "regularMarketLastSize": 12,
//       "regularMarketNetChange": -0.54,
//       "regularMarketTradeTimeInLong": 1680638400497,
//       "netPercentChangeInDouble": -0.5236,
//       "markChangeInDouble": -0.77,
//       "markPercentChangeInDouble": -0.4634,
//       "regularMarketPercentChangeInDouble": -0.325,
//       "delayed": true,
//       "realtimeEntitled": false
//     }
//   }
