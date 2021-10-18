package recipes;

import java.util.Map;

public interface Recipe {

    void addOrChangeIngredient(String ingredient, int amount);

    int getAmount(String ingredient);

    String getTitle();

    void setTitle(String title) ;

    String getDescription();

    void setDescription(String description);

    Map<String, Integer> getIngredientsAndAmounts() ;

    void setIngredientsAndAmounts(Map<String, Integer> ingredientsAndAmounts);

    public int getId();

    public void setId(int id);

}
