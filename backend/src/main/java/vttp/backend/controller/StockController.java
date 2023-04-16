package vttp.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.backend.config.JWTService;
import vttp.backend.model.Journal;
import vttp.backend.model.Quotes;
import vttp.backend.service.ImageService;
import vttp.backend.service.JournalService;
import vttp.backend.service.StocksService;
import vttp.backend.service.UserService;

import static vttp.backend.Utilities.*;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(path="/api")
public class StockController {

    @Autowired
    private StocksService stocksService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    //For API Calls
    @GetMapping(path="/quotes/{ticker}")
    @ResponseBody
    public ResponseEntity<String> getQuote(@PathVariable String ticker) {
        System.out.println(ticker);
        Optional<Quotes> quotes = stocksService.quotes(ticker);

        JsonObject results = quotesToJson(quotes.get());

        logger.info("Get Quotes in Controller done");

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }

    //Uploading image to S3
    @PostMapping (path="/uploadimage", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadImage (@RequestHeader HttpHeaders header, @RequestPart MultipartFile image) {

        Optional<Journal> journalOpt = imageService.uploadImage(image);

        if (journalOpt.isEmpty()) {

            JsonObject results = Json.createObjectBuilder()
            .add(">>> Error:","Failed to add image")
            .build();

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(results.toString());
        } else  {
            Journal journal = journalOpt.get();

            JsonObject results = Json.createObjectBuilder()
            .add("uuid", journal.getUuid())
            .add("imageUrl", journal.getImageUrl())
            .build();

            return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(results.toString());

        }

    }

    //Uploading Journal into Mongo and SQL
    @PostMapping(path="/uploadjournal", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> postJournal (@RequestHeader HttpHeaders header, @RequestBody String payload, @RequestParam String day_id) {

        logger.info(">>> Recevied payload. Payload results: " + payload);

        logger.info(">>> day_id: " + day_id);
        logger.info(">>> day_id true or false: " + day_id.isEmpty());

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Uploading Journal. Authenticated email: " + email);

        //Reads and creates the Json object
        JsonObject jsonObject = toJsonFromClient(payload);

        logger.info(">>> JsonObject from Journal is: " + jsonObject.toString());

        //Create the Journal object from json 
        Journal journal = toJournalFromClient(jsonObject);

        //For testing
        Date date = journal.getEntryDate();
        logger.info(">>> Testing. Date entered in journal is: " + date.toString());


        //Insert into Mongo and SQL
        try {
            userService.insertJournal(journal, day_id, email);
            logger.info(">>> run insert ");
        } catch (Exception e) {
            imageService.deleteImage(journal.getUuid());
            logger.info(">>> does not run insert");
    
        }

        JsonObject results = Json.createObjectBuilder().add("day_id", day_id).build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }

    //Get List of Day for the Calendar
    @GetMapping(path="/getdays")
    @ResponseBody
    public ResponseEntity<String> getListOfDays (@RequestHeader HttpHeaders header, @RequestParam String startStr, @RequestParam String endStr) {

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Get List of Days. Authenticated email: " + email);

        Optional<JsonArray> jsonArray = userService.findListOfDayByEmail(email, startStr, endStr);

        if (jsonArray.isEmpty()) {

            JsonObject results = Json.createObjectBuilder().add("error","no days").build();

            return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(results.toString());
        }

        JsonArray array = jsonArray.get();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(array.toString());
    }

    //Find and get the day_id from Days schema in SQL
    @GetMapping(path="/searchday")
    @ResponseBody
    public ResponseEntity<String> getDayId (@RequestHeader HttpHeaders header, @RequestParam String date) {

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Get day_id with email & day.Authenticated email: " + email);

        Optional<String> dayIdOpt = userService.findDayIdByEmailAndDay(email, date);

        String day_id = "";

        if (!dayIdOpt.isEmpty()) {

            day_id = dayIdOpt.get();
        }
        JsonObject results = Json.createObjectBuilder().add("day_id", day_id).build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }

    //Get JsonArray of Journal from Mongo
    @GetMapping(path="/getjournal")
    @ResponseBody
    public ResponseEntity<String> getJsonArrayJournal (@RequestHeader HttpHeaders header, @RequestParam String day_id) {

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Get JasonArray Journal via day_id. Authen email: " + email);

        JsonArray array = userService.getJsonArrayOfJournalByDayId(day_id);

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(array.toString());
    }

    //Delete Day entry from Days SQL
    @DeleteMapping(path="/deleteday")
    @ResponseBody
    public ResponseEntity<String> deleteDay (@RequestHeader HttpHeaders header, @RequestParam String day_id) {

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Deleting Day from Days SQL. Authen email: " + email);

        userService.deleteDay(day_id);

        JsonObject results = Json.createObjectBuilder().add("day_deleted", day_id).build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
    }  
    
    @DeleteMapping(path="/deletejournal")
    @ResponseBody
    public ResponseEntity<String> deleteJournal (@RequestHeader HttpHeaders header, @RequestParam String uuid, 
    @RequestParam Double pnl, @RequestParam String day_id) {

        String value = header.getFirst("Authorization").substring(7);
        String email = jwtService.extractUsername(value);
        logger.info(">>>> Deleting Journal from Mongo. Authen email: " + email);

        userService.deleteJournal(uuid, pnl, day_id);

        JsonObject results = Json.createObjectBuilder().add("journal_deleted", uuid).build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(results.toString());
        

    }


}


