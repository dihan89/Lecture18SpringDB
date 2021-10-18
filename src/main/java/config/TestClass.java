package config;


import dao.IngredientDao;
import org.h2.tools.Server;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RecipesConfig.class)
public class TestClass {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private IngredientDao ingredientDaoDao;



    public void show() throws SQLException {

        Server server = Server.createTcpServer();
        System.out.println(server.getService());
        server.start();
        System.out.println(server.getURL());
        System.out.println(jdbcTemplate == null);
        List<?> list = jdbcTemplate.queryForList("select * INGREDIENTS");
        System.out.println(list == null);

        System.out.println("SHOW");
        server.stop();




    }
}
