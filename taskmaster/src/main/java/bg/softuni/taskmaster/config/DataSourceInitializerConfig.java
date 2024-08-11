package bg.softuni.taskmaster.config;

import bg.softuni.taskmaster.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceInitializerConfig {

    @Value("${spring.sql.init.platform}")
    private String sqlPlatform;

    private final RoleRepository roleRepository;


    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource, ResourceLoader resourceLoader) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        if (roleRepository.count() == 0) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

            if (sqlPlatform.equals("all")) {
                sqlPlatform = "";
            }
            populator.addScript(resourceLoader.getResource("classpath:data-" + sqlPlatform + ".sql"));
            initializer.setDatabasePopulator(populator);
        }
        return initializer;
    }
}
