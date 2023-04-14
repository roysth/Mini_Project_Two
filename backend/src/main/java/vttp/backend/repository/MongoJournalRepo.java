package vttp.backend.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vttp.backend.model.Journal;

import static vttp.backend.Utilities.*;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class MongoJournalRepo {

    private static final String C_JOURNAL = "journal";

    private static final Logger logger = LoggerFactory.getLogger(MongoJournalRepo.class);

    @Autowired
	private MongoTemplate mongoTemplate;

    //Inserting Journal into Mongo
    public ObjectId insertPost (Journal journal, String day_id) {

        Document doc = new Document();
        doc.put("uuid", journal.getUuid());
        doc.put("symbol", journal.getSymbol());
        doc.put("position", journal.getPosition());
        doc.put("tradeType", journal.getTradeType());
        doc.put("entryPrice", journal.getEntryPrice());
        doc.put("exitPrice", journal.getExitPrice());
        doc.put("entryDate", journal.getEntryDate());
        doc.put("exitDate", journal.getExitDate());
        doc.put("pnl", journal.getPnl());
        doc.put("comments", journal.getComments());
        doc.put("imageUrl", journal.getImageUrl());
        doc.put("day_id", day_id);

        mongoTemplate.insert(doc, C_JOURNAL);

        return doc.getObjectId("_id");
    }


    //Get a Journal from Mongo using day_id
    //*Since need to send to Frontend, just get in JsonArray instead of List<Journal>
    public JsonArray getJsonArrayOfJournalByDayId (String day_id) {

        logger.info(">>>Find Journal in Mongo using  : " + day_id);

        Criteria criteria = Criteria.where("day_id").is(day_id);

        Query query = Query.query(criteria);

        //List<Document> doc = mongoTemplate.find(query, Document.class, C_JOURNAL);

        List<Journal> journalList = mongoTemplate.find(query, Document.class, C_JOURNAL).stream().map(doc -> getJournalFromMongo(doc)).toList();

        JsonArrayBuilder ab = Json.createArrayBuilder();
        for (Journal j: journalList) {
            ab.add(journalToJson(j));
        }

        JsonArray results = ab.build();

        logger.info(">>>Results from Mongo: " + results.toString());

        return results;
    }

 






    
}
