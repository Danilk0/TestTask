package com.example.idfinance.web;

import com.example.idfinance.dao.CurrencyDao;
import com.example.idfinance.dao.UpdateDao;
import com.example.idfinance.dto.Post;
import com.example.idfinance.entity.Currency;
import com.example.idfinance.entity.User;
import com.example.idfinance.tools.MyTimerTask;
import com.example.idfinance.util.HibernateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;

import static java.math.BigDecimal.*;

@RestController
@RequestMapping(value = "/api/account")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final CurrencyDao currencyDao=CurrencyDao.getInstance();


    @GetMapping("getCurrency")
    public ResponseEntity<List<Currency>> getCurrency(){
        UpdateDao.getInstance().saveOrUpdate();
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
    public void  register(@PathVariable String username,@PathVariable String symbol){
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(username,symbol), 60 * 1000);

    }


}
