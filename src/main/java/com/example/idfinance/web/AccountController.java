package com.example.idfinance.web;

import com.example.idfinance.dao.CurrencyDao;
import com.example.idfinance.entity.Currency;
import com.example.idfinance.entity.User;
import com.example.idfinance.util.HibernateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;

@RestController
@RequestMapping(value = "/api/account")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final CurrencyDao currencyDao=CurrencyDao.getInstance();


    @GetMapping("getCurrency")
    public ResponseEntity<List<Currency>> getCurrency(){

        List<Currency> currencyList;
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session=sessionFactory.openSession()) {
                session.beginTransaction();

                currencyList=currencyDao.findAll(session);

                session.getTransaction().commit();
            }

        }
        return ResponseEntity.ok(currencyList);
    }

    @GetMapping("getCurrency/{curName}")
    public ResponseEntity<Currency> getCurrency(@PathVariable String curName){
        Currency currency;

        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session=sessionFactory.openSession()) {
                session.beginTransaction();

                currency=currencyDao.findPriceByName(session,curName);

                session.getTransaction().commit();
            }catch (Exception e){
                return ResponseEntity.notFound().build();
            }

        }
        return ResponseEntity.ok(currency);

    }

    @GetMapping("register/{username}/{symbol}")
    public void  register(@PathVariable String username,@PathVariable String symbol) throws InterruptedException {
        Currency currency;
        User user;
        try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session=sessionFactory.openSession()) {
                session.beginTransaction();

                currency=currencyDao.findPriceByName(session, symbol);
                user=User.builder()
                        .startPrice(currency.getPrice())
                        .username(username)
                        .symbol(currency.getSymbol())
                        .build();
                session.save(user);

                session.getTransaction().commit();
            }

        }

        while (true){
            log.warn("Start timer");

            try(SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
                try(Session session=sessionFactory.openSession()) {
                    session.beginTransaction();

                    BigDecimal percent1= CurrencyDao.getInstance().findPriceByName(session, symbol).getPrice().subtract(user.getStartPrice()).divide(user.getStartPrice()).multiply(valueOf(100L));
                    if (Double.parseDouble(percent1.toPlainString()) >= 1 || Double.parseDouble(percent1.toPlainString()) <= -1) {
                        log.warn("percentage change",symbol,user.getUsername(),percent1);
                    }
                    session.getTransaction().commit();
                }
            }
            Thread.sleep(60000);
        }

    }


}
