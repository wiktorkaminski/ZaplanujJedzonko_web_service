package pl.coderslab.dao;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.exception.NotFoundException;
import pl.coderslab.model.Admin;
import pl.coderslab.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
    private static final String CREATE_ADMIN_QUERY = "INSERT INTO admins(first_name, last_name, email, password, superadmin, enable) VALUES (?,?,?,?,?,?);";
    private static final String DELETE_ADMIN_QUERY = "DELETE FROM admins where id = ?;";
    private static final String FIND_ALL_ADMINS_QUERY = "SELECT * FROM admins;";
    private static final String READ_ADMIN_QUERY = "SELECT * from admins where id = ?;";
    private static final String UPDATE_ADMIN_QUERY = "UPDATE	admins SET first_name = ?, last_name = ?, email = ?, password = ?, superadmin = ?, enable = ? WHERE	id = ?;";
    public static final String CHECK_EMAIL_PASS = "SELECT * FROM admins WHERE email = ?";
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Admin create(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Admin read(Integer adminId) {
        Admin admin = new Admin();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_ADMIN_QUERY)) {
            statement.setInt(1, adminId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    admin.setId(resultSet.getInt("id"));
                    admin.setFirstName(resultSet.getString("first_name"));
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

    public void update(Admin admin) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADMIN_QUERY);
             PreparedStatement statement1 = connection.prepareStatement(FIND_ALL_ADMINS_QUERY)) {
            ResultSet resultSet = statement1.executeQuery();
            statement.setInt(7, admin.getId());
            statement.setString(1, admin.getFirstName());
            statement.setString(2, admin.getLastName());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getPassword());
            statement.setInt(5, admin.getSuperAdmin());
            statement.setInt(6, admin.getEnable());
            while (resultSet.next()) {
                if (resultSet.getInt("id") == admin.getId()) {
                    if (admin.getPassword().equals(resultSet.getString("password"))) {  //sprawdzam czy hasło zostało zmienione, jeżeli nie,
                        statement.setString(4, admin.getPassword());                 // nie będzie zasolone
                        break;
                    }
                    else{
                        statement.setString(4, hashPassword(admin.getPassword()));
                        break;
                    }
                }
            }
            statement.setInt(7, admin.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Admin> findAll(){
        List<Admin> adminList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_ADMINS_QUERY)){
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            Admin admin = new Admin();
            admin.setId(resultSet.getInt("id"));
            admin.setFirstName(resultSet.getString("first_name"));
            admin.setLastName(resultSet.getString("last_name"));
            admin.setEmail(resultSet.getString("email"));
            admin.setPassword(resultSet.getString("password"));
            admin.setSuperAdmin(resultSet.getInt("superadmin"));
            admin.setEnable(resultSet.getInt("enable"));
            adminList.add(admin);

        }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return adminList;
    }

    public void delete(Integer adminId){
        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_ADMIN_QUERY)){
        statement.setInt(1, adminId);
        statement.executeUpdate();

            boolean deleted = statement.execute();
            if (!deleted) {
                throw new NotFoundException("Admin not found");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean checkEmailAndPassword(String email, String password){
        try (Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_PASS)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (email.equals(resultSet.getString("email")) &&
                        BCrypt.checkpw(password, resultSet.getString("password"))){
                    System.out.println("zalogowany");
                    return true;
                }
            }
            System.out.println("nie ma takiego użytkownika");
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public int returnId(String email, String password){
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_PASS)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if (email.equals(resultSet.getString("email")) &&
                        BCrypt.checkpw(password, resultSet.getString("password"))){
                    System.out.println("ID= " + resultSet.getInt("id"));
                    return resultSet.getInt("id");
                }
            }
            return 0;

        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
}
