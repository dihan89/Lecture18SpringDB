package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import recipes.Recipe;
import recipes.RecipeImpl;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;


public class RecipeDaoImpl implements RecipeDao{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcOperations parameterJdbcOperations;
    private SimpleJdbcInsertOperations insertOperations;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private RowMapper<Recipe> recipeRowMapper;

    public RecipeDaoImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.parameterJdbcOperations = new NamedParameterJdbcTemplate(dataSource);
        //this.insertOperations = new SimpleJdbcInsert(dataSource)
               // .withTableName("Animal")
               // .usingGeneratedKeyColumns("id");

        this.recipeRowMapper = (resultSet, i) -> {
            Recipe recipe = new RecipeImpl(resultSet.getInt("id"),
                    resultSet.getString("title"));
            recipe.setDescription(resultSet.getString("description"));
            Map<String, Integer> ingredients = new HashMap<>();



            return recipe;
        };

        //jdbcTemplate.setExceptionTranslator(new TableNotExistsException());
    }

    public void insertRecipe(Recipe recipe){
        SimpleJdbcInsertOperations insertOperationsIngrs =
                new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("RECIPESINGRS")
                .usingGeneratedKeyColumns("id");
        SimpleJdbcInsertOperations insertOperationsRecipes =
                new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                        .withTableName("RECIPES")
                        .usingGeneratedKeyColumns("id");

        BeanPropertySqlParameterSource sqlParameterSource =
                new BeanPropertySqlParameterSource(recipe);


        int id_rec = insertOperationsRecipes.executeAndReturnKey
                (sqlParameterSource).intValue();


        for (Map.Entry<String,Integer> entry : recipe.getIngredientsAndAmounts().entrySet()) {
           insertOperationsIngrs.executeAndReturnKey(new HashMap<String,Object>(){{
               put("recipeid",id_rec);
               put("amount", entry.getValue());
               put("ingredient", entry.getKey());
           }}
           );
        }

//            parameterJdbcOperations.update
//                    ("insert into RECIPESINGRS ( recipeid, ingredient, amount) " +
//                                    "values (:recipeid, :ingredient, :amount)",
//                            sqlParameterSource2);
//        }
//        SqlParameterSource[] source =
//                SqlParameterSourceUtils.createBatch(sourcePairs.toArray());

//        int[] i = parameterJdbcOperations.batchUpdate(
//                "insert into RECIPES_INGRS (recipe_id, ingredient, amount) " +
//                        "values(" + recipe.getId()+
//                        ", :ingredient, :amount)", source);
        //String str = "values(1, " + "kllk" + ", 230)";
//        int[] i = parameterJdbcOperations.batchUpdate(
//                "insert into RECIPES_INGRS (recipe_id, ingredient, amount) " +
//                        str,//"values(1, \"kllk\", 230)",
//                source);
      //  System.out.println("ARRAY  " +i);
    }

    public void showRecipes() {
        //jdbcTemplate.queryForList("select * from animal order by type, name").forEach(System.out::println);
    }

    public Recipe getRecipe(String title) {
       Recipe recipe = new RecipeImpl();
       recipe.setTitle(title);
       Map<String, Object> map = jdbcTemplate.queryForMap("select * from RECIPES r" +
               " where r.title = ?", title);
       recipe.setId((Integer)map.get("id"));
       recipe.setDescription((String) map.get("description"));
       recipe.setIngredientsAndAmounts(getRecipeIngrs(recipe.getId()));
       return recipe;

    }

    private Map<String,Integer> getRecipeIngrs(int idRecipe){
        Map<String,Integer> ingredients = new HashMap<>();
//        List<Map<String, Object>> listIngrs = jdbcTemplate.queryForList("select INGREDIENTS.TITLE AS ingr, " +
//                "RECIPES_INGRS.AMOUNT AS amount from RECIPES_INGRS JOIN INGREDIENTS " +
//                "on RECIPES_INGRS.INGREDIENTS_ID = INGREDIENTS.ID where RECIPES_INGRS.INGREDIENTS_ID  = :idRecipe",
//                new HashMap<>(){{put("idRecipe", idRecipe);}}
//        );
        List<Map<String, Object>> listIngrs = jdbcTemplate.queryForList("select *" +
                " from RECIPESINGRS " +
                "where recipeid  = ?", idRecipe
               // new HashMap<>(){{put("idRecipe", idRecipe);}}
        );
        System.out.println("listIngrs.size="+ listIngrs.size());
        listIngrs.stream().forEach(map->{ingredients.put((String) map.get("ingredient"),(Integer) map.get("amount"));});
        return ingredients;
    }



}
