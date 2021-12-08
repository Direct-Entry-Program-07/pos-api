package lk.ijse.dep7.pos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Configuration
public class JPAConfig {

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2DDL;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(ds);
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter);
        lcemfb.setPackagesToScan("lk.ijse.dep7.pos.entity");
        lcemfb.setJpaProperties(jpaProp());
        return lcemfb;
    }

    private Properties jpaProp(){
        Properties props = new Properties();
        props.put("hibernate.hbm2ddl.auto", hbm2DDL);
        return props;
    }

    @Bean
    public DataSource dataSource() {
        return new JndiDataSourceLookup().getDataSource("java:comp/env/jdbc/posCP");
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(@Value("${hibernate.dialect}") String dialect,
                                             @Value("${hibernate.show_sql}") boolean showSql) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.MYSQL);
        adapter.setDatabasePlatform(dialect);
        adapter.setShowSql(showSql);
//        adapter.setGenerateDdl(true);
        return adapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
