package config;

import dao.IngredientDao;
import dao.IngredientDaoImpl;
import dao.RecipeDao;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;
import java.util.List;




@ContextConfiguration(classes = RecipesConfig.class)
public class Lecture18DB {




    public static void main(String[] args) throws SQLException {

      TestClass test = new TestClass();
      test.show();




    }
}
