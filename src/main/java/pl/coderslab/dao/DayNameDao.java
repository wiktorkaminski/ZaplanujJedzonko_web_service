package pl.coderslab.dao;

import pl.coderslab.model.DayName;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DayNameDao {
    public static final String FIND_ALL_DAY_NAMES_QUERY = "SELECT * FROM day_name";
    public static final String FIND_DAY_ID_BY_NAME_QUERY = "SELECT * FROM day_name WHERE name = ?";

    public List<DayName> findAll() {
        List<DayName> dayNameList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DAY_NAMES_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while(resultSet.next()){
                DayName dayNameToAdd = new DayName();
                dayNameToAdd.setId(resultSet.getInt("id"));
                dayNameToAdd.setName(resultSet.getString("name"));
                dayNameToAdd.setDisplayOrder(resultSet.getInt("display_order"));
                dayNameList.add(dayNameToAdd);

            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return dayNameList;
    }

    public int findDayIdByName(String name) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DAY_ID_BY_NAME_QUERY);
             ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
