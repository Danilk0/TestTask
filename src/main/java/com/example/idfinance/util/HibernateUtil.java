package com.example.idfinance.util;

import com.example.idfinance.entity.Currency;
import com.example.idfinance.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory(){

        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(Currency.class);
        configuration.addAnnotatedClass(User.class);
//        configuration.addAnnotatedClass(User.class);
//        configuration.addAttributeConverter(new BirthDateConverter());
//        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        return configuration.buildSessionFactory();
    }

}
