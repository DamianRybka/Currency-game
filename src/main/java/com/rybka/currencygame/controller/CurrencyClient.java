package com.rybka.currencygame.controller;

import com.rybka.currencygame.model.CurrencyModel;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class CurrencyClient {

    @EventListener(ApplicationReadyEvent.class)
    public CurrencyModel getCurrencyRates() {

        RestTemplate restTemplate = new RestTemplate(); // łączenie się z API - pozwala na to
        CurrencyModel forObject = restTemplate.getForObject("https://api.exchangeratesapi.io/latest?base=PLN", CurrencyModel.class);
        return forObject;
    }
}
