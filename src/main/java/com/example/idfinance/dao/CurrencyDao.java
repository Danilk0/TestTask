package com.example.idfinance.dao;

import com.example.idfinance.entity.Currency;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyDao {
    private static final CurrencyDao INSTANCE = new CurrencyDao();

    public List<Currency> findAll(Session session){
        return session.createQuery("select c from Currency c",Currency.class).list();
    }

    public Currency findPriceByName(Session session,String symbol){
        return session.createQuery("select c from Currency c where c.symbol = :symbol",Currency.class)
                .setParameter("symbol",symbol)
                .uniqueResult();
    }

    public void updatePriceByName(Session session, String symbol, BigDecimal price){
        var query = session.createQuery("update Currency c set c.price = :price where c.symbol= :symbol")
                .setParameter("price",price)
                .setParameter("symbol",symbol);
        query.executeUpdate();
    }


    public static CurrencyDao getInstance() {
        return INSTANCE;
    }



}
