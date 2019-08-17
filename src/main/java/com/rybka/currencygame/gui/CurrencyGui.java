package com.rybka.currencygame.gui;

import com.rybka.currencygame.controller.CurrencyClient;
import com.rybka.currencygame.entity.CurrencyRank;
import com.rybka.currencygame.repo.CurrencyRankRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Route("game")
public class CurrencyGui extends VerticalLayout {

    @Autowired
    public CurrencyGui(CurrencyClient currencyClient, CurrencyRankRepo currencyRankRepo) {
        AtomicInteger counter = new AtomicInteger();
        Label labelFinnalResult = new Label("Liczba podejść:");

        Double eur = currencyClient.getCurrencyRates().getRates().getEUR();
        BigDecimal bigDecimalRawCurrency = new BigDecimal(eur);
        BigDecimal bigDecimalPlnValue = new BigDecimal(1);
        BigDecimal bigDecimalToGuess = bigDecimalPlnValue.divide(bigDecimalRawCurrency, 2, RoundingMode.CEILING);

        Label labelCurrencyFrom = new Label("Z waluty");
        labelCurrencyFrom.setText("Z EUR");
        Label labelCurrencyTo = new Label("Na walute");
        labelCurrencyTo.setText("NA PLN");
        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.add(labelCurrencyFrom, labelCurrencyTo);

        TextField textFieldUserValue = new TextField("Podaj wartość");
        Label labelResult = new Label("Wynik");
        Button button = new Button("Click");
        button.addClickListener(clickEvent -> {
            BigDecimal userValue = new BigDecimal(textFieldUserValue.getValue());
            int result = userValue.compareTo(bigDecimalToGuess);
            String winMessage = "gratulacje";
            String smallMessage = "za dużo";
            String greaterMessage = "za mało";
            if (result == 0) {
                labelResult.setText(winMessage);
                labelFinnalResult.setText("GRATULACJE! udało się za " + counter.incrementAndGet());

                add(new Image("https://media1.giphy.com/media/2sXf9PbHcEdE1x059I/giphy.gif", "super!"));

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //informacja kto się zalogował
                CurrencyRank currencyRank = new CurrencyRank(principal.toString(), counter.get(), LocalDate.now());
                currencyRankRepo.save(currencyRank);

            } else if (result >= 1) {
                labelResult.setText(smallMessage);
                labelFinnalResult.setText("Liczba podejść: " + counter.incrementAndGet());
            } else {
                labelResult.setText(greaterMessage);
                labelFinnalResult.setText("Liczba podejść: " + counter.incrementAndGet());
            }
        });

        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.add(textFieldUserValue, button, labelResult);


        add(hl1, hl2, labelFinnalResult);


    }
}