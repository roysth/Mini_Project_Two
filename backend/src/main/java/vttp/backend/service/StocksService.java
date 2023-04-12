package vttp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.backend.model.Quotes;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StocksService {

    @Autowired
    private StocksAPIService stocksAPIService;

    private static final Logger logger = LoggerFactory.getLogger(StocksService.class);

    //Get Quotes object from API
    public Optional<Quotes> quotes (String ticker) {

        Optional<Quotes> quotes = stocksAPIService.getQuotes(ticker);

        return quotes;
    }


    
}
