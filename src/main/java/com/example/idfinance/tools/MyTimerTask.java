package com.example.idfinance.tools;

import com.example.idfinance.dao.CurrencyDao;
import com.example.idfinance.dao.UpdateDao;
import com.example.idfinance.entity.Currency;
import com.example.idfinance.entity.User;
import com.example.idfinance.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.TimerTask;

import static java.math.BigDecimal.valueOf;

@Slf4j
public class MyTimerTask extends TimerTask {
    private String username;
    private String symbol;

    public MyTimerTask(String username, String symbol) {
        this.username = username;
        this.symbol = symbol;
    }

    private final CurrencyDao currencyDao=CurrencyDao.getInstance();

    public void run() {
        Currency currency;
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session=sessionFactory.openSession()) {
                session.beginTransaction();

                currency=currencyDao.findPriceByName(session, symbol);

                session.getTransaction().commit();
            }

        }
        User user=User.builder()
                .startPrice(currency.getPrice())
                .username(username)
                .symbol(currency.getSymbol())
                .build();

        UpdateDao.getInstance().saveOrUpdate();
                try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
                    try(Session session=sessionFactory.openSession()) {
                        session.beginTransaction();
                        session.saveOrUpdate(user);
                        BigDecimal percent1= CurrencyDao.getInstance().findPriceByName(session, symbol).getPrice().subtract(user.getStartPrice()).multiply(valueOf(100L));
                        if (Integer.parseInt(percent1.toPlainString()) >= 1 || Integer.parseInt(percent1.toPlainString()) <= -1 ) {
                            log.warn("percentage change",currency.getSymbol(),user.getUsername(),percent1);
                        }
                        session.getTransaction().commit();
                    }
                }

    }
}

