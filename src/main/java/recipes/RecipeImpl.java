package recipes;

import java.util.HashMap;
import java.util.Map;

public class RecipeImpl implements Recipe{
    private int id;
    private String title;
    private String description;
    private Map<String, Integer> ingredientsAndAmounts;

    public RecipeImpl(int id, String title, String description, Map<String, Integer> ingredientsAndAmounts) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredientsAndAmounts = ingredientsAndAmounts;
    }

    public RecipeImpl( String title, String description, Map<String, Integer> ingredientsAndAmounts) {
        this.id = -1;
        this.title = title;
        this.description = description;
        this.ingredientsAndAmounts = ingredientsAndAmounts;
    }

    public RecipeImpl(int id, String title) {
        this(id, title, null, new HashMap<>());
    }

    public RecipeImpl() {
        this(-1, null, null, new HashMap<>());
    }



    @Override
    public String toString() {
        return "RecipeImpl{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredientsAndAmounts=" + ingredientsAndAmounts +
                '}';
    }


    @Override
    public void addOrChangeIngredient(String ingredient, int amount){
        ingredientsAndAmounts.put(ingredient, amount);
    }

    @Override
    public int getAmount(String ingredient){
        return ingredientsAndAmounts.containsKey(ingredient) ?
                ingredientsAndAmounts.get(ingredient) : 0;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Map<String, Integer> getIngredientsAndAmounts() {
        return ingredientsAndAmounts;
    }

    @Override
    public void setIngredientsAndAmounts(Map<String, Integer> ingredientsAndAmounts) {
        this.ingredientsAndAmounts = ingredientsAndAmounts;
    }
}
