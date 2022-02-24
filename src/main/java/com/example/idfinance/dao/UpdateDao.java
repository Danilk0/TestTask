package com.example.idfinance.dao;

import com.example.idfinance.entity.Currency;
import com.example.idfinance.util.HTTPUrlUtil;
import com.example.idfinance.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

@Slf4j
public class UpdateDao {
    private static final UpdateDao INSTANCE = new UpdateDao();

    public void saveOrUpdate(){
        var post = HTTPUrlUtil.sendGet(String.valueOf(90));
        var post1 = HTTPUrlUtil.sendGet(String.valueOf(80));
        var post2 = HTTPUrlUtil.sendGet(String.valueOf(48543));
        log.info("Get Json");
        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            try(Session session=sessionFactory.openSession()) {
                session.beginTransaction();

                session.saveOrUpdate(Currency.builder()
                        .currency_id(post.getId())
                        .price(post.getPrice_usd())
                        .symbol(post.getSymbol())
                        .build());
                session.saveOrUpdate(Currency.builder()
                        .currency_id(post1.getId())
                        .price(post1.getPrice_usd())
                        .symbol(post1.getSymbol())
                        .build());
                session.saveOrUpdate(Currency.builder()
                        .currency_id(post2.getId())
                        .price(post2.getPrice_usd())
                        .symbol(post2.getSymbol())
                        .build());

                session.getTransaction().commit();

            }
        }
    }
    public static UpdateDao getInstance() {
        return INSTANCE;
    }

}
