package com.rybka.currencygame.gui;

import com.rybka.currencygame.entity.CurrencyRank;
import com.rybka.currencygame.repo.CurrencyRankRepo;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("rank")
public class Rank extends VerticalLayout {

    @Autowired
    public Rank(CurrencyRankRepo currencyRankRepo) {
        Grid<CurrencyRank> grid = new Grid<>(CurrencyRank.class);
        grid.setItems(currencyRankRepo.findAll());
        add(grid);
    }
}
