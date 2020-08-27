package pl.coderslab.dao;

import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetail;
import pl.coderslab.model.RecentPlanDetail;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlanDao {
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name, description,created, admin_id) VALUES (?,?,NOW(),?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan WHERE id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * FROM plan WHERE id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ?,  admin_id = ? WHERE id = ?;";
    private static final String COUNT_PLANS_BY_ADMIN_ID_QUERY = "SELECT COUNT(*) AS numOfPlans FROM plan WHERE admin_id = ?;";
    private static final String FIND_RECENT_PLAN_QUERY =
            "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id \n" +
                    "FROM `recipe_plan`\n" +
                    "JOIN day_name on day_name.id=day_name_id\n" +
                    "JOIN recipe on recipe.id=recipe_id WHERE\n" +
                    "recipe_plan.plan_id = (SELECT MAX(id) from plan WHERE admin_id = ?)\n" +
                    "ORDER by day_name.display_order, recipe_plan.display_order;";
    private static final String FIND_RECENT_PLAN_NAME_QUERY =
            "SELECT plan.name AS plan_name FROM plan\n" +
                    "WHERE id = (SELECT MAX(id) FROM plan WHERE admin_id = ?);";
    private static final String FIND_PLANS_BY_ADMIN_ID_QUERY = "SELECT * FROM plan WHERE admin_id = ?;";
    private static final String ADD_RECIPE_TO_PLAN_QUERY = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?,?,?);";
    private static final String FIND_MEALS_IN_PLAN_QUERY =
            "SELECT day_name.name as day_name, meal_name, recipe.name as recipe_name, recipe.description as recipe_description, recipe.id as recipe_id\n" +
                    "FROM `recipe_plan`\n" +
                    "JOIN day_name on day_name.id=day_name_id\n" +
                    "JOIN recipe on recipe.id=recipe_id WHERE plan_id = ? " +
                    "ORDER by day_name.display_order, recipe_plan.display_order;";




    public Plan create(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_PLAN_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setString(1, plan.getName());
            insertStm.setString(2, plan.getDescription());
            insertStm.setInt(3, plan.getAdminId());

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = insertStm.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_PLAN_QUERY)
        ) {
            statement.setInt(1, planId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                    plan.setAdminId(resultSet.getInt("admin_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;
    }

    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_PLANS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planToAdd.setAdminId(resultSet.getInt("admin_id"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public void update(Plan plan) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PLAN_QUERY)) {
            statement.setInt(4, plan.getId());
            statement.setString(1, plan.getName());
            statement.setString(2, plan.getDescription());
            statement.setInt(3, plan.getAdminId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PLAN_QUERY)) {
            statement.setInt(1, planId);
            statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Plan not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer countPlansByAdminId(Integer adminId) {
        int plansCounter = 0;
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(COUNT_PLANS_BY_ADMIN_ID_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                plansCounter = resultSet.getInt("numOfPlans");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plansCounter;
    }

    public List<PlanDetail> findRecentPlan(int adminId) {
        List<PlanDetail> planDetails = new LinkedList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_RECENT_PLAN_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PlanDetail tempPlanDetails = new PlanDetail(
                        resultSet.getString("day_name"),            // setting value in constructor
                        resultSet.getString("meal_name"),           // setting value in constructor
                        resultSet.getString("recipe_name"),         // setting value in constructor
                        resultSet.getString("recipe_description"),  // setting value in constructor
                        resultSet.getInt("recipe_id")               // setting value in constructor
                );
                planDetails.add(tempPlanDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planDetails;
    }

    public String finRecentPlanName(int adminId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_RECENT_PLAN_NAME_QUERY)
        ) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("plan_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Dodaj swój pierwszy plan aby zobaczyć szczegóły.";
    }

    public List<Plan> findPlansByAdminId(int adminId) {
        List<Plan> planList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PLANS_BY_ADMIN_ID_QUERY)
        ) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Plan planToAdd = new Plan();
                    planToAdd.setId(resultSet.getInt("id"));
                    planToAdd.setName(resultSet.getString("name"));
                    planToAdd.setDescription(resultSet.getString("description"));
                    planToAdd.setCreated(resultSet.getString("created"));
                    planToAdd.setAdminId(resultSet.getInt("admin_id"));
                    planList.add(planToAdd);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

    public void addRecipeToPlan(int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_RECIPE_TO_PLAN_QUERY)) {
            statement.setInt(1, recipeId);
            statement.setString(2, mealName);
            statement.setInt(3, displayOrder);
            statement.setInt(4, dayNameId);
            statement.setInt(5, planId);

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PlanDetail> findMealsInPlan (int planId) {
        List <PlanDetail> planDetails = new LinkedList<>();
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(FIND_MEALS_IN_PLAN_QUERY)
        ) {
            preparedStatement.setInt(1, planId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PlanDetail tempPlanDetails = new PlanDetail(
                        resultSet.getString("day_name"),            // setting value in constructor
                        resultSet.getString("meal_name"),           // setting value in constructor
                        resultSet.getString("recipe_name"),         // setting value in constructor
                        resultSet.getString("recipe_description"),  // setting value in constructor
                        resultSet.getInt("recipe_id")               // setting value in constructor
                );
                planDetails.add(tempPlanDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planDetails;
    }

}
