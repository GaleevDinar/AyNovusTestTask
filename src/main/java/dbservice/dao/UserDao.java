package dbservice.dao;

import dbservice.DBHelper;
import dbservice.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.h2.jdbc.JdbcSQLException;

import java.sql.*;

public class UserDao {
    Logger logger = LogManager.getLogger(UserDao.class);

    public void addUser(User user) {
        Connection connection = DBHelper.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (LOGIN, PASSWORD) VALUES (?,?)")) {
            statement.setString(1, user.getLogin().toLowerCase());
            statement.setString(2, user.getPass());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Ошибка вставки пользователя");
        }
    }

    public User getUserByLogin(String login) {
        Connection connection = DBHelper.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS WHERE LOGIN = (?)")) {
            statement.setString(1, login.toLowerCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                User user = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4));
                return user;
            } catch (JdbcSQLException e) {
                logger.warn("В базе данных нет пользователя");
            }
        } catch (SQLException e) {
            logger.warn("Ошибка запроса с базы данных");
        }
        return null;
    }

    public void changeLastTime(long time, String login){
        Connection connection = DBHelper.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE USERS SET LASTTIME = (?) WHERE LOGIN = (?)")) {
            statement.setLong(1, time);
            statement.setString(2, login.toLowerCase());
            statement.execute();
        } catch (SQLException e) {
            logger.warn("Ошибка запроса изменения последнего времени");
        }
    }

    public void createTable() {
        Connection connection = DBHelper.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE USERS\n " +
                    "(\n" +
                    "    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
                    "    login VARCHAR(45) NOT NULL,\n" +
                    "    password VARCHAR(15) NOT NULL,\n" +
                    "    lastTime BIGINT DEFAULT NULL\n" +
                    ");\n" +
                    "CREATE UNIQUE INDEX users_id_uindex ON users (id);\n" +
                    "CREATE UNIQUE INDEX users_login_uindex ON users (login);");
        } catch (SQLException e) {
            logger.info("База данных не создана");
        }
    }
}
