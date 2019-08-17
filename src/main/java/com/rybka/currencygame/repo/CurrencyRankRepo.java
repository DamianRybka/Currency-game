package com.rybka.currencygame.repo;

import com.rybka.currencygame.entity.CurrencyRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRankRepo extends JpaRepository<CurrencyRank, Long> {
}
