package vttp.backend;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.backend.model.Journal;
import vttp.backend.model.Quotes;
import vttp.backend.model.Role;
import vttp.backend.model.User;

public class Utilities {

    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    //Creating the Quotes object from Json #Used in StocksAPIService
    public static Quotes createQuotesFromJson (JsonObject json, String ticker) {
        Quotes quotes = new Quotes();

        quotes.setSymbol(json.getJsonObject(ticker).getString("symbol"));
        quotes.setLastPrice(json.getJsonObject(ticker).getJsonNumber("lastPrice").bigDecimalValue());
        quotes.setOpenPrice(json.getJsonObject(ticker).getJsonNumber("openPrice").bigDecimalValue());
        quotes.setClosePrice(json.getJsonObject(ticker).getJsonNumber("closePrice").bigDecimalValue());
        quotes.setHighPrice(json.getJsonObject(ticker).getJsonNumber("highPrice").bigDecimalValue());
        quotes.setLowPrice(json.getJsonObject(ticker).getJsonNumber("lowPrice").bigDecimalValue());
        quotes.setNetChange(json.getJsonObject(ticker).getJsonNumber("netChange").bigDecimalValue());
        quotes.setTotalVolume(json.getJsonObject(ticker).getJsonNumber("totalVolume").bigDecimalValue());
        quotes.setFiftyTwoWkHigh(json.getJsonObject(ticker).getJsonNumber("52WkHigh").bigDecimalValue());
        quotes.setFiftyTwoWkLow(json.getJsonObject(ticker).getJsonNumber("52WkLow").bigDecimalValue());

        logger.info(">>>>>>> Quotes object created. 52WkLow price is: " + quotes.getFiftyTwoWkLow());
        return quotes;        
    }

    //Convert Quotes object to Json (To send to frontend) #Used in Controller
    public static JsonObject quotesToJson (Quotes quotes) {
        JsonObjectBuilder ob = Json.createObjectBuilder();

        ob.add("symbol", quotes.getSymbol());
        ob.add("lastPrice", quotes.getLastPrice());
        ob.add("openPrice", quotes.getOpenPrice());
        ob.add("closePrice", quotes.getClosePrice());
        ob.add("highPrice", quotes.getHighPrice());
        ob.add("lowPrice", quotes.getLowPrice());
        ob.add("netChange", quotes.getNetChange());
        ob.add("totalVolume", quotes.getTotalVolume());
        ob.add("FiftyTwoWkHigh", quotes.getFiftyTwoWkHigh());
        ob.add("FiftyTwoWkLow", quotes.getFiftyTwoWkLow());
        
        JsonObject object = ob.build();
        logger.info(">>>>>>> Json Quotes object created. Json object is: " + object.toString());
        return object;
    }

    //---------------------------JOURNAL UTILITIES--------------------------------\\


    //Read the Json from Client #Used in Controller
    public static JsonObject toJsonFromClient (String payload) {
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject object = reader.readObject();
    
        return object;
    }


    //Convert Journal Json object from Client to Journal Object #Used in Controller
    public static Journal toJournalFromClient (JsonObject jsonObject) {

        Journal journal = new Journal();

        //Specifcally for Angular Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        /* 
        >>> JsonObject from client is: 
        {"journal":
            {"symbol":"BIDU",
            "position":"short",
            "tradeType":"swing",
            "entryPrice":"153",
            "exitPrice":144,
            "entryDate":"2023-04-07",
            "exitDate":"2023-04-12",
            "pnl":465,
            "comments":"TEST"
            }
        }
        */
        JsonObject json = jsonObject.getJsonObject("journal");

        journal.setUuid(json.getString("uuid"));
        journal.setSymbol(json.getString("symbol"));
        journal.setQuantity(json.getInt("quantity"));
        journal.setPosition(json.getString("position"));
        journal.setTradeType(json.getString("tradeType"));
        journal.setEntryPrice(json.getJsonNumber("entryPrice").doubleValue());
        journal.setExitPrice(json.getJsonNumber("entryPrice").doubleValue());
        try {
            journal.setEntryDate(sdf.parse(json.getString("entryDate")));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            journal.setEntryDate(sdf.parse(json.getString("exitDate")));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        journal.setPnl(json.getInt("pnl"));
        journal.setComments(json.getString("comments"));
        journal.setImageUrl(json.getString("imageUrl"));

        return journal;
    }

    //To get the Journal from Mongo #Used in MongoJournalRepo
    public static Journal journal (Document doc) {

        Journal journal = new Journal();
        journal.setUuid(doc.getString("uuid"));
        journal.setSymbol(doc.getString("symbol"));
        journal.setPosition(doc.getString("position"));
        journal.setTradeType(doc.getString("tradeType"));
        journal.setEntryPrice(doc.getDouble("entryPrice"));
        journal.setExitPrice(doc.getDouble("exitPrice"));
        journal.setEntryDate(doc.getDate("entryDate"));
        journal.setExitDate(doc.getDate("exitDate"));
        journal.setPnl(doc.getDouble("pnl"));
        journal.setComments(doc.getString("comments"));
        journal.setImageUrl(doc.getString("imageUrl"));
        
        return journal;
        
    }

    //Create the User object from SQL #Used in User
    public static User createUser (SqlRowSet rs) {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setRole(Role.USER);

        return user;
    }



   


    

}

