package com.example.idfinance.dao;

import com.example.idfinance.util.HTTPUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;

@Slf4j
public class UpdateDao {
    private static final UpdateDao INSTANCE = new UpdateDao();

    private final CurrencyDao currencyDao=CurrencyDao.getInstance();
    public void saveOrUpdate(Session session){
        var post = HTTPUrlUtil.sendGet(String.valueOf(90));
        var post1 = HTTPUrlUtil.sendGet(String.valueOf(80));
        var post2 = HTTPUrlUtil.sendGet(String.valueOf(48543));
        log.info("Get Json",post,post1,post2);
        if(post!= null && post1 != null && post2 != null) {
            currencyDao.updatePriceByName(session, post.getSymbol(), post.getPrice_usd());
            currencyDao.updatePriceByName(session, post1.getSymbol(), post1.getPrice_usd());
            currencyDao.updatePriceByName(session, post2.getSymbol(), post2.getPrice_usd());

        }

    }
    public static UpdateDao getInstance() {
        return INSTANCE;
    }

}
