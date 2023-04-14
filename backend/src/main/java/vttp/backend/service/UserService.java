package vttp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp.backend.model.User;
import vttp.backend.repository.SQLRepo;

import org.bson.types.ObjectId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import vttp.backend.model.Day;
import vttp.backend.model.Journal;
import vttp.backend.repository.MongoJournalRepo;

import static vttp.backend.Utilities.*;


@Service
public class UserService {

    @Autowired
    private SQLRepo sqlRepo;

    @Autowired
    private MongoJournalRepo mongoJournalRepo;

    private static final Logger logger = LoggerFactory.getLogger(SQLRepo.class);

    //Find user by email in SQL
    public Optional<User> findUserByEmail(String email) {
        return sqlRepo.findUserByEmail(email);
    }

    //Register user in SQL
    public void registerUser(User user) {
        sqlRepo.registerUser(user);
    }

    //Insert Journal and Day object into Mongo and SQL respectively
    //Mongo: Journal + day_id
    //SQL: User
    @Transactional
    public void insertJournal (Journal journal, String day_id, String email) throws Exception{

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        System.out.println("Formatted date: " + formattedDate);
        Date parsedDate = dateFormat.parse(formattedDate);

        //Create the day_id to reference the daily trades by an user
        if (day_id.isEmpty()) {
            day_id = UUID.randomUUID().toString().substring(0, 8);
            sqlRepo.registerDay(day_id, email, journal.getPnl(), parsedDate);

            logger.info(">>> day_id inserted: " + day_id);
        } else {
            double initialPnl = sqlRepo.findPnl(day_id);
            double updatedPnl = sqlRepo.updatePnl(initialPnl, journal.getPnl(), day_id);

            //TODO SEND EMAIL ON DAILY PNL/UPDATED PNL
        }
        ObjectId objectId = mongoJournalRepo.insertPost(journal, day_id);

        logger.info("Journal added to Mongo. ObjectId: " + objectId);
    }

    //This is to fill the calender. Used in controller /getdays
    public Optional<JsonArray> findListOfDayByEmail (String email, String start, String end) {

        Optional<List<Day>> listOfDayOpt = sqlRepo.findListOfDayByEmail(email, start, end);

        if (listOfDayOpt.isEmpty()) {
            return Optional.empty();
        }
        List<Day> listOfDay = listOfDayOpt.get();

        JsonArrayBuilder ab = Json.createArrayBuilder();

        for(Day d: listOfDay) {
            ab.add(dayToJson(d));
        }
        JsonArray array = ab.build();

        return Optional.of(array);
    }


    //Find day_id by email and day
    public Optional<String> findDayIdByEmailAndDay (String email, String day) {

        return sqlRepo.findDayIdByEmailAndDay(email, day);
    }

    //Get JsonArray from Mongo
    public JsonArray getJsonArrayOfJournalByDayId (String day_id) {

        return mongoJournalRepo.getJsonArrayOfJournalByDayId(day_id);
    }
    
}
