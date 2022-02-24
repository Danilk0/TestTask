package com.example.idfinance;

import com.example.idfinance.dao.UpdateDao;
import com.example.idfinance.dto.Post;
import com.example.idfinance.entity.Currency;
import com.example.idfinance.util.HTTPUrlUtil;
import com.example.idfinance.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class IdFinanceApplication {

    public static void main(String[] args) {

        SpringApplication.run(IdFinanceApplication.class, args);
    }



}
