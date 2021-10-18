package config;

import dao.RecipeDao;
import dao.RecipeDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class RecipesConfig {
    @Bean
    public DataSource dataSource() {
        //"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", ""
        //return new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        return new DriverManagerDataSource("jdbc:h2:~/testD", "sa", "");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public RecipeDaoImpl recipeDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate){
        return new RecipeDaoImpl(dataSource, jdbcTemplate);
    }
//    @Bean
//    public LobHandler lobHandler() {
//        return new DefaultLobHandler();
//    }

    @PostConstruct
    public void makeScript() throws SQLException {
        System.out.println(dataSource()==null);


        try {
            System.out.println(dataSource().getConnection());
        }catch(Exception exc){
            System.out.println("PROBLEM WITH CONNECTION");
            throw new RuntimeException();
        }
        try {
            ScriptUtils.executeSqlScript(dataSource().getConnection(),
                    new ClassPathResource("/data.sql"));
        } catch (SQLException exc){
            System.out.println("SQL PROBLEM");
            throw new RuntimeException();
        }

    }
}
