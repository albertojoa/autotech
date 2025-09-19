package com.autotech.autotech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class AutotechApplication implements CommandLineRunner {

    private final DataSource dataSource;

    public AutotechApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(AutotechApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Conexión a la base de datos exitosa: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.err.println("❌ Error de conexión a la base de datos: " + e.getMessage());
        }
    }
}
