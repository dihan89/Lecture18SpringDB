import config.RecipesConfig;
import dao.IngredientDao;
import dao.RecipeDao;
import dao.RecipeDaoImpl;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import recipes.Recipe;
import recipes.RecipeImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RecipesConfig.class)
public class TestRecipe {
    private static Server server;


    @BeforeAll
    public static void startServer() throws SQLException {

       server = Server.createTcpServer();
       System.out.println(server.getService());
       server.start();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RecipeDaoImpl recipeDaoImpl;

    @AfterEach
    public void clear() {
       JdbcTestUtils.deleteFromTables(jdbcTemplate,"RECIPESINGRS",
                 "RECIPES");
   }

   @AfterAll
   public  static void close(){
        System.out.println("STOP");
        server.stop();
   }

   private Map<String, Integer> createIngrs(String[] ingrs, int[] amounts){
        Map<String, Integer> result = new HashMap<>();
        int count = Math.min(ingrs.length, amounts.length);
        for(int i = 0; i < count; ++i){
            result.put(ingrs[i], amounts[i]);
        }
        return result;

   }
   private Recipe getRecipe(String title, String description, String[] ingrs,
                            int[] amounts ){
        return new RecipeImpl(title, description, createIngrs(ingrs, amounts));

   };


   // @Autowired
    //private IngredientDao ingredientDaoDao;

   // @Autowired
   // private RecipeDao ingredientDao;

    @Test
    public void showRecipes() {
        System.out.println(server.getURL());
        System.out.println(jdbcTemplate == null);
        //List<?> list = jdbcTemplate.queryForList("select * from INGREDIENTS");
        ///System.out.println(list.get(0));

        System.out.println("SHOW");


    }

    @Test
    public void addRecipes() {
        System.out.println(server.getURL());
        Recipe recipe1 = getRecipe("pizza", "bake",new String[] {
                "tomato", "flour", "cheese"},
                new int[] {5, 10, 20});
        Recipe recipe2 = getRecipe("salad", "cut",new String[] {
                        "tomato", "cucumber", "onion"},
                new int[] {3, 2, 1});
        Recipe recipe3 = getRecipe("salad2", "cut",new String[] {
                        "tomato", "pepper", "onion"},
                new int[] {4, 5, 2});
        recipeDaoImpl.insertRecipe(recipe1);
        recipeDaoImpl.insertRecipe(recipe2);
        recipeDaoImpl.insertRecipe(recipe3);

        System.out.println("SHOW SALAD2");
        Recipe recipe4 = recipeDaoImpl.getRecipe("salad2");
        System.out.println(recipe4.toString());



        System.out.println(jdbcTemplate == null);
        List<?> list = jdbcTemplate.queryForList("select * from INGREDIENTS");
        //System.out.println(list.get(0));

        System.out.println("SHOW");


    }


}