package vttp.backend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.backend.model.Journal;
import vttp.backend.repository.MongoJournalRepo;


@Service
public class JournalService {

    @Autowired
    private MongoJournalRepo mongoJournalRepo;

    //Insert Journal into Mongo
    public ObjectId insertPost (Journal journal, String day_id) {

        return mongoJournalRepo.insertPost(journal, day_id);

    }


    
}
