package io.github.navjotsrakhra.urlshortner.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * The DataSourceConfig class is responsible for configuring the data source for the application.
 */
@Configuration
public class DataSourceConfig {
    /**
     * Configure the data source for the application.
     *
     * @return A DataSource for the application.
     */
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url(System.getenv("db_url"));
        dataSourceBuilder.username(System.getenv("dn_user"));
        dataSourceBuilder.password(System.getenv("db_pass"));
        return dataSourceBuilder.build();
    }
}