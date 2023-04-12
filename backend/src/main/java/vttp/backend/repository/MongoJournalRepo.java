package vttp.backend.repository;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vttp.backend.model.Journal;

import static vttp.backend.Utilities.*;

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


    //Get a Journal from Mongo
    // public Optional<Journal> getJournal (String postId) {

    //     logger.info(">>>Find Mongo using  : " + postId);

    //     Criteria criteria = Criteria.where("postId").is(postId);

    //     Query query = Query.query(criteria);

    //     Document doc = mongoTemplate.findOne(query, Document.class, C_JOURNAL);

    //     logger.info(">>>Results from Mongo: " + doc.toString());

    //     return Optional.of(createPost(doc));
    // }

 






    
}
