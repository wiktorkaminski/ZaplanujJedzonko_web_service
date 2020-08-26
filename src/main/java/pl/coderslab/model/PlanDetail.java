package pl.coderslab.model;

public class PlanDetail extends RecentPlanDetail {
    private int recipeId;

    public PlanDetail(String dayName, String mealName, String recipeName, String recipeDescription, int recipeId) {
        super(dayName, mealName, recipeName, recipeDescription);
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return super.toString() +
                "PlanDetail{" +
                "recipeId=" + recipeId +
                '}';
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
