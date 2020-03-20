package com.example.configrefresh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication
public class ConfigRefreshApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigRefreshApplication.class, args);
    }


    @Configuration
    static class CustomerDataSource {


        private final ObjectMapper objectMapper;

        CustomerDataSource(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Bean
        @RefreshScope
        public HikariDataSource h2DataSource() throws IOException {
            FileSystemResource fileSystemResource = new FileSystemResource("config.json");
            FileDataSourceProperties fileDataSourceProperties = objectMapper.readValue(fileSystemResource.getFile(), FileDataSourceProperties.class);
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.setUrl(fileDataSourceProperties.getUrl());
            dataSourceProperties.setUsername(fileDataSourceProperties.getUsername());
            dataSourceProperties.setPassword(fileDataSourceProperties.getPassword());
            return dataSourceProperties.initializeDataSourceBuilder()
                    .type(HikariDataSource.class)
                    .build();
        }
    }


    static class FileDataSourceProperties {
        private String url;
        private String username;
        private String password;

        public FileDataSourceProperties() {
        }

        public FileDataSourceProperties(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


    @RestController
    public static class Index {

        private final HikariDataSource dataSource;

        public Index(HikariDataSource dataSource) {
            this.dataSource = dataSource;
        }

        @GetMapping(value = "/getJdbcUrl")
        public String getValue() throws SQLException {
            return dataSource.getJdbcUrl();
        }
    }

}
