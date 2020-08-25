package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao {
    // SQL QUERIES
    private static final String CREATE_RECIPE_QUERY = "INSERT INTO recipe (name, ingredients, description, preparation_time, preparation, admin_id, created) VALUES (?,?,?,?,?,?,NOW());";
    private static final String DELETE_RECIPE_QUERY = "DELETE FROM recipe where id = ?;";
    private static final String FIND_ALL_RECIPES_QUERY = "SELECT * FROM recipe;";
    private static final String READ_RECIPE_QUERY = "SELECT * from recipe where id = ?;";
    private static final String UPDATE_RECIPE_QUERY =
            "UPDATE recipe SET " +
                    "name = ? , " +
                    "ingredients = ?, " +
                    "description = ?, " +
                    "preparation_time = ?, " +
                    "updated = NOW(), " +
                    "preparation = ? " +
                    "WHERE id = ?;";
    private static final String COUNT_RECIPES_BY_ADMIN_ID_QUERY = "SELECT COUNT(*) AS numOfRecipes FROM recipe WHERE admin_id = ?;";
    private static final String FIND_RECIPES_BY_ADMIN_ID_QUERY = "SELECT * FROM recipe WHERE admin_id = ?;";

    /**
     * Get recipe by id
     *
     * @param recipeId
     * @return
     */
    public Recipe read(Integer recipeId) {
        Recipe recipe = new Recipe();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(READ_RECIPE_QUERY)
        ) {
            preparedStatement.setInt(1, recipeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                resultSet.next();
                recipe.setId(resultSet.getInt("id"));
                recipe.setName(resultSet.getString("name"));
                recipe.setIngredients(resultSet.getString("ingredients"));
                recipe.setDescription(resultSet.getString("description"));
                recipe.setCreated(resultSet.getString("created"));
                recipe.setUpdated(resultSet.getString("updated"));
                recipe.setPreparationTime(resultSet.getInt("preparation_time"));
                recipe.setPreparation(resultSet.getString("preparation"));
                recipe.setAdminId(resultSet.getInt("admin_id"));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipe;
    }

    /**
     * Return all recipes
     *
     * @return
     */
    public List<Recipe> findAll() {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_RECIPES_QUERY)) {

            while (resultSet.next()) {
                Recipe tempRecipe = new Recipe();
                tempRecipe.setId(resultSet.getInt("id"));
                tempRecipe.setName(resultSet.getString("name"));
                tempRecipe.setIngredients(resultSet.getString("ingredients"));
                tempRecipe.setDescription(resultSet.getString("description"));
                tempRecipe.setCreated(resultSet.getString("created"));
                tempRecipe.setUpdated(resultSet.getString("updated"));
                tempRecipe.setPreparationTime(resultSet.getInt("preparation_time"));
                tempRecipe.setPreparation(resultSet.getString("preparation"));
                tempRecipe.setAdminId(resultSet.getInt("admin_id"));
                recipeList.add(tempRecipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    /**
     * Create recipe
     *
     * @param recipe
     * @return
     */
    public Recipe create(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_RECIPE_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, recipe.getName());
            insertStm.setString(2, recipe.getIngredients());
            insertStm.setString(3, recipe.getDescription());
            insertStm.setInt(4, recipe.getPreparationTime());
            insertStm.setString(5, recipe.getPreparation());
            insertStm.setInt(6, recipe.getAdminId());

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipe.setId(generatedKeys.getInt(1));
                    return recipe;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update recipe
     * private static final String UPDATE_RECIPE_QUERY = "UPDATE recipe SET name = ? , ingredients = ?, description = ?, updated = NOW(), preparation_time = ?, preparation = ? WHERE id = ?;";
     *
     * @param recipe
     */
    public void update(Recipe recipe) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECIPE_QUERY)) {
            statement.setInt(6, recipe.getId());
            statement.setString(1, recipe.getName());
            statement.setString(2, recipe.getIngredients());
            statement.setString(3, recipe.getDescription());
            statement.setInt(4, recipe.getPreparationTime());
            statement.setString(5, recipe.getPreparation());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Remove recipe by id
     *
     * @param recipeId
     */
    public void delete(Integer recipeId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RECIPE_QUERY)) {
            statement.setInt(1, recipeId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer countRecipesByAdminId(Integer adminId) {
        int recipesCounter = 0;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(COUNT_RECIPES_BY_ADMIN_ID_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                recipesCounter = resultSet.getInt("numOfRecipes");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipesCounter;
    }

    public List<Recipe> findRecipesByAdminId(int adminId) {
        List<Recipe> recipeList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_RECIPES_BY_ADMIN_ID_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Recipe tempRecipe = new Recipe();
                    tempRecipe.setId(resultSet.getInt("id"));
                    tempRecipe.setName(resultSet.getString("name"));
                    tempRecipe.setIngredients(resultSet.getString("ingredients"));
                    tempRecipe.setDescription(resultSet.getString("description"));
                    tempRecipe.setCreated(resultSet.getString("created"));
                    tempRecipe.setUpdated(resultSet.getString("updated"));
                    tempRecipe.setPreparationTime(resultSet.getInt("preparation_time"));
                    tempRecipe.setPreparation(resultSet.getString("preparation"));
                    tempRecipe.setAdminId(resultSet.getInt("admin_id"));
                    recipeList.add(tempRecipe);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

}
