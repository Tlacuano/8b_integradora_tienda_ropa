package mx.edu.utez.services_clothing_shop.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.logging.Logger;

@Configuration
public class DatabaseInitializer {

    private final ResourceLoader resourceLoader;

    private final DataSource dataSource;

    public DatabaseInitializer(ResourceLoader resourceLoader, DataSource dataSource) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
    }

    @Bean
    CommandLineRunner initDatabase(){
        return args -> {
            Resource resource = resourceLoader.getResource("classpath:data_base/data.sql");

            try(Connection connection = dataSource.getConnection()){
                ScriptUtils.executeSqlScript(connection, resource);
            }catch (Exception e){
                Logger.getLogger(DatabaseInitializer.class.getName())
                        .severe("Error al inicializar la base de datos: " + e.getMessage());
            }
        };
    }
}
