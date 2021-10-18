package recipes;

public class IngredientImpl implements Ingredient{
    private int id;
    private String title;

    public IngredientImpl(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public IngredientImpl() {
        this(-1,null);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
