package vttp.backend.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    //TO INCLUDE TIME??? KIV

    private String uuid; //this is to identify which to delete or edit 
    private String symbol;
    private Integer quantity;
    private String position;//long or short
    private String tradeType;
    private double entryPrice;
    private double exitPrice;
    private Date entryDate; 
    private Date exitDate;
    private double pnl;
    private String comments;
    private String imageUrl;


    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public double getEntryPrice() {
        return entryPrice;
    }
    public void setEntryPrice(double entryPrice) {
        this.entryPrice = entryPrice;
    }
    public double getExitPrice() {
        return exitPrice;
    }
    public void setExitPrice(double exitPrice) {
        this.exitPrice = exitPrice;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public Date getExitDate() {
        return exitDate;
    }
    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
    public double getPnl() {
        return pnl;
    }
    public void setPnl(double pnl) {
        this.pnl = pnl;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    
}


// export interface Journal {

//     uuid: string
//     symbol: string
//     position: string //long or short
//     tradeType: string //day, swing, position
//     entryPrice: number
//     exitPrice: number
//     entryDate: Date //use date or number?
//     exitDate: Date
//     pnl: number
//     comments: string
    
// }
