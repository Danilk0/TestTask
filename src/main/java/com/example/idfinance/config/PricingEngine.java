package com.example.idfinance.config;

import com.example.idfinance.dao.UpdateDao;
import com.example.idfinance.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PricingEngine {

    private UpdateDao updateDao=UpdateDao.getInstance();

    @Scheduled(fixedDelay = 60000)
    public void computePrice()  {

        @Cleanup Session session= HibernateUtil.buildSessionFactory().openSession();

        session.beginTransaction();
        updateDao.saveOrUpdate(session);

        session.getTransaction().commit();
    }

}
