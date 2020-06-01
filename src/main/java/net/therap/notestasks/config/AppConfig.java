package net.therap.notestasks.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Objects;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan({"net.therap.notestasks"})
public class AppConfig {

    @Bean(name = "entityManagerFactory")
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean localEmfBean =
                new LocalEntityManagerFactoryBean();
        localEmfBean.setPersistenceUnitName("notestasks");
        return localEmfBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }


    @Bean
    @Scope("prototype")
    Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(Objects.requireNonNull(injectionPoint.getField()).getDeclaringClass());
    }
}
