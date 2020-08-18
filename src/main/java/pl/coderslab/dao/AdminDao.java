package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE	admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE	id = ?;";


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public Admin create(Admin admin){
        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)){
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, hashPassword(admin.getPassword()));
            statement.setInt(5, admin.getSuperAdmin());
            statement.setInt(6, admin.getEnable());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                admin.setId(resultSet.getInt(1));
            }
            return admin;
        }catch (SQLException e){
            e.printStackTrace();
            return admin;
        }
    }

    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()){
                admin.setId(resultSet.getInt("id"));
                admin.setFirstName(resultSet.getString("firs_name"));
                admin.setLastName(resultSet.getString("last_name"));
                admin.setEmail(resultSet.getString("email"));
                admin.setPassword(resultSet.getString("password"));
                admin.setSuperAdmin(resultSet.getInt("superadmin"));
                admin.setEnable(resultSet.getInt("enable"));
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

}
