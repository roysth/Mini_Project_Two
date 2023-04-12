package vttp.backend.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.backend.model.Quotes;

import static vttp.backend.Utilities.*;

import java.io.StringReader;
import java.util.Optional;

@Service
public class StocksAPIService {

    private static final Logger logger = LoggerFactory.getLogger(StocksAPIService.class);


    // //when uploading 
    // private String apiKey = System.getenv("API_KEY");

    //when working locally
    @Value("${STOCKS_API_KEY}")
    private String apiKey;

    private static String qURL = "https://api.tdameritrade.com/v1/marketdata/quotes";

    public Optional<Quotes> getQuotes (String ticker){

        //Build the URL
        String quotesUrl = UriComponentsBuilder.fromUriString(qURL)
            .queryParam("apikey", apiKey)
            .queryParam("symbol", ticker)
            .toUriString();

        logger.info(">>> Quotes URL API address  : " + quotesUrl);

        //Create the GET request to API
        RequestEntity<Void> request = RequestEntity
            .get(quotesUrl)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        //Make the GET request and receive the response as String
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(request, String.class);

        //Convert the String to Json
        JsonReader reader = Json.createReader(new StringReader(response.getBody()));

        //Get the Jsonobject
        JsonObject object = reader.readObject();

        //Create the Quotes object
        Quotes quotes = createQuotesFromJson(object, ticker);

        return Optional.of(quotes);
    }
    
}
