package by.gaponenko.restaurant.dao.impl;

import by.gaponenko.restaurant.bean.RegistrationUserData;
import by.gaponenko.restaurant.bean.User;
import by.gaponenko.restaurant.dao.DaoException;
import by.gaponenko.restaurant.dao.UserDao;
import by.gaponenko.restaurant.dao.pool.ConnectionPool;
import by.gaponenko.restaurant.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String STATUS = "status";

    private static final String FIND_AUTHORIZED_USER = "SELECT * FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?;";
    private static final String FIND_USERDATA_BY_ID = "SELECT * FROM users_details WHERE id_user = ?;";
    private static final String ADD_NEW_USER = "INSERT INTO users (login, password, id_role, status) VALUES(?,?,?,?);";
    private static final String REGISTER_USER_INFO = "INSERT INTO users_details(id_user, name, surname, last_name, date_of_birth, telephone_number, email, address) VALUES(?,?,?,?,?,?,?,?)";

    private Connection connection;

    @Override
    public User authorization(String login, String pass) throws DaoException, SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        User user = new User();

        try {
            connection = connectToDataBase();

            preparedStatement = connection.prepareStatement(FIND_AUTHORIZED_USER);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                log.info("Attempt to log in with incorrect data. Login :{}", login);
                throw new DaoException("Wrong login or password");
            }

            if (resultSet.getString(STATUS) == "inactive") {
                log.info("Attempt to log in from deleted user. Login :{}", login);
                throw new DaoException("Sorry, your profile was deleted.");
            }

            user.setIdUser(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while working with database", e);
        }
        return user;
    }

    @Override
    public boolean registration(RegistrationUserData userData) throws DaoException, SQLException {
        PreparedStatement preparedStatementForUser;
        PreparedStatement preparedStatementForUserInfo;
        ResultSet resultSet;

        try {
            connection = connectToDataBase();

            resultSet = findUserByLogin(connection, userData.getLogin());

            // проверяем, есть ли пользователь с таким логином
            if (resultSet.next()) {
                return false;
            }

            preparedStatementForUser = connection.prepareStatement(ADD_NEW_USER);
            preparedStatementForUser.setString(1, userData.getLogin());
            preparedStatementForUser.setString(2, userData.getPassword());
            preparedStatementForUser.setString(3, userData.getRole());
            preparedStatementForUser.setString(4, "active");
            preparedStatementForUser.executeUpdate();

            resultSet = findUserByLogin(connection, userData.getLogin());
            resultSet.next();

            preparedStatementForUserInfo = connection.prepareStatement(REGISTER_USER_INFO);
            preparedStatementForUserInfo.setInt(1, resultSet.getInt(1));
            preparedStatementForUserInfo.setString(2, userData.getName());
            preparedStatementForUserInfo.setString(3, userData.getSurname());
            preparedStatementForUserInfo.setString(4, userData.getLastName());
            preparedStatementForUserInfo.setString(5, userData.getDateOfBirth());
            preparedStatementForUserInfo.setString(6, userData.getTelephoneNumber());
            preparedStatementForUserInfo.setString(7, userData.getEmail());
            preparedStatementForUserInfo.setString(8, userData.getAddress());
            preparedStatementForUserInfo.executeUpdate();

            resultSet.close();
            preparedStatementForUser.close();
            preparedStatementForUserInfo.close();
            connection.close();
        } catch (SQLException e) {
            log.error("Error working with statements while sign in", e);
            throw new DaoException("Error while adding new User", e);
        }
        return true;
    }

    public RegistrationUserData editUserInfo(RegistrationUserData userData) {
        return userData;
    }

    public RegistrationUserData loadUserDataByLogin(String login) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        RegistrationUserData userData = null;

        try {
            connection = connectToDataBase();

            resultSet = findUserByLogin(connection, login);

            if (!resultSet.next()) {
                System.out.println("не нашел логин");
                return userData;
            }

            preparedStatement = connection.prepareStatement(FIND_USERDATA_BY_ID);
            preparedStatement.setString(1, resultSet.getString(1));
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            userData = new RegistrationUserData();
            userData.setIdUser(resultSet.getInt(1));
            userData.setName(resultSet.getString(2));
            userData.setDateOfBirth(resultSet.getString(3));
            userData.setTelephoneNumber(resultSet.getString(4));
            userData.setAddress(resultSet.getString(5));
            userData.setEmail(resultSet.getString(6));
            userData.setSurname(resultSet.getString(7));
            userData.setLastName(resultSet.getString(8));

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            throw new DaoException("Error when trying to create a statement user find query", e);
        }
        return userData;
    }

    private ResultSet findUserByLogin(Connection connection, String login) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
        preparedStatement.setString(1, login);
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    private Connection connectToDataBase() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.takeConnection();
        } catch (InterruptedException e) {
            log.error("Error while getting connection from connection pool queue", e);
            throw new DaoException("Error taking connection to database", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

   /* private void fillMenu() throws SQLException, DaoException {
        connection = connectToDataBase();
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement1;


        // вводим роли
            PreparedStatement preparedStatement2;
            preparedStatement2 = connection.prepareStatement("INSERT INTO roles (title) VALUES(?);");
            preparedStatement2.setString(1, "user");
            preparedStatement2.executeUpdate();
            preparedStatement2.setString(1, "admin");
            preparedStatement2.executeUpdate();

        preparedStatement = connection.prepareStatement("INSERT INTO dish_categories (id_category, name) VALUES(?,?);");
        preparedStatement.setInt(1,1);
        preparedStatement.setString(2, "Starters");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,2);
        preparedStatement.setString(2, "Pasta");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,3);
        preparedStatement.setString(2, "Fish and seafood");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,4);
        preparedStatement.setString(2, "Meat");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,5);
        preparedStatement.setString(2, "Soups");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,6);
        preparedStatement.setString(2, "Deserts");
        preparedStatement.executeUpdate();
        preparedStatement.setInt(1,7);
        preparedStatement.setString(2, "Drinks");
        preparedStatement.executeUpdate();

        preparedStatement1 = connection.prepareStatement("INSERT INTO menu (name, price, description, photo, id_category, status) VALUES(?,?,?,?,?,?);");
        preparedStatement1.setString(1,"Tar tar their beef with chanterelles");
        preparedStatement1.setInt(2,14);
        preparedStatement1.setString(3,"150 gram");
        preparedStatement1.setString(4,"images/dish/tartar.png");
        preparedStatement1.setInt(5,1);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Tar tar their beef with buratta");
        preparedStatement1.setInt(2,12);
        preparedStatement1.setString(3,"140 gram");
        preparedStatement1.setString(4,"images/dish/tartar_buratta.png");
        preparedStatement1.setInt(5,1);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Hummus with olive salsa");
        preparedStatement1.setInt(2,17);
        preparedStatement1.setString(3,"170 gram");
        preparedStatement1.setString(4,"images/dish/hummus.png");
        preparedStatement1.setInt(5,1);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Green salad with passionfruit sauce");
        preparedStatement1.setInt(2,15);
        preparedStatement1.setString(3,"170 gram");
        preparedStatement1.setString(4,"images/dish/green_salad.png");
        preparedStatement1.setInt(5,1);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Shrimp tempura");
        preparedStatement1.setInt(2,19);
        preparedStatement1.setString(3,"110 gram");
        preparedStatement1.setString(4,"images/dish/shrimp.png");
        preparedStatement1.setInt(5,1);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        //pasta 2
        preparedStatement1.setString(1,"Pasta with salmon");
        preparedStatement1.setInt(2,19);
        preparedStatement1.setString(3,"350 gram");
        preparedStatement1.setString(4,"images/dish/pasta_salmon.png");
        preparedStatement1.setInt(5,2);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Pasta with ham and cheese");
        preparedStatement1.setInt(2,15);
        preparedStatement1.setString(3,"340 gram");
        preparedStatement1.setString(4,"images/dish/pasta_ham.png");
        preparedStatement1.setInt(5,2);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Carbonara");
        preparedStatement1.setInt(2,15);
        preparedStatement1.setString(3,"370 gram");
        preparedStatement1.setString(4,"images/dish/carbonara.png");
        preparedStatement1.setInt(5,2);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Pasta with chicken and mushrooms");
        preparedStatement1.setInt(2,15);
        preparedStatement1.setString(3,"370 gram");
        preparedStatement1.setString(4,"images/dish/pasta_chicken.png");
        preparedStatement1.setInt(5,2);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Pasta with mushrooms and feta");
        preparedStatement1.setInt(2,15);
        preparedStatement1.setString(3,"310 gram");
        preparedStatement1.setString(4,"images/dish/pasta_feta.png");
        preparedStatement1.setInt(5,2);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();

        //sea 3
        preparedStatement1.setString(1,"Oysters with rosemary");
        preparedStatement1.setInt(2,50);
        preparedStatement1.setString(3,"110 gram");
        preparedStatement1.setString(4,"images/dish/oyster.png");
        preparedStatement1.setInt(5,3);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Tataki with tuna");
        preparedStatement1.setInt(2,20);
        preparedStatement1.setString(3,"270 gram");
        preparedStatement1.setString(4,"images/dish/tataki.png");
        preparedStatement1.setInt(5,3);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Black pearl");
        preparedStatement1.setInt(2,30);
        preparedStatement1.setString(3,"280 gram");
        preparedStatement1.setString(4,"images/dish/black_pearl.png");
        preparedStatement1.setInt(5,3);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Palm beach");
        preparedStatement1.setInt(2,19);
        preparedStatement1.setString(3,"270 gram");
        preparedStatement1.setString(4,"images/dish/palm_beach.png");
        preparedStatement1.setInt(5,3);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Summer sea");
        preparedStatement1.setInt(2,21);
        preparedStatement1.setString(3,"390 gram");
        preparedStatement1.setString(4,"images/dish/summer_sea.png");
        preparedStatement1.setInt(5,3);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();

        //meat 4
        preparedStatement1.setString(1,"Wellington");
        preparedStatement1.setInt(2,33);
        preparedStatement1.setString(3,"420 gram");
        preparedStatement1.setString(4,"images/dish/wellington.png");
        preparedStatement1.setInt(5,4);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Beef in maple syrup");
        preparedStatement1.setInt(2,32);
        preparedStatement1.setString(3,"370 gram");
        preparedStatement1.setString(4,"images/dish/beef.png");
        preparedStatement1.setInt(5,4);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Steak with mushrooms and cherry");
        preparedStatement1.setInt(2,41);
        preparedStatement1.setString(3,"400 gram");
        preparedStatement1.setString(4,"images/dish/steak_cherry.png");
        preparedStatement1.setInt(5,4);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Pepper steak");
        preparedStatement1.setInt(2,41);
        preparedStatement1.setString(3,"300 gram");
        preparedStatement1.setString(4,"images/dish/pepper_steak.png");
        preparedStatement1.setInt(5,4);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Rib eye steak");
        preparedStatement1.setInt(2,88);
        preparedStatement1.setString(3,"300 gram");
        preparedStatement1.setString(4,"images/dish/ribai_steak.png");
        preparedStatement1.setInt(5,4);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();

        //soups 5
        preparedStatement1.setString(1,"Tom yam with tiger prawns");
        preparedStatement1.setInt(2,24);
        preparedStatement1.setString(3,"400 gram");
        preparedStatement1.setString(4,"images/dish/tom_yam.png");
        preparedStatement1.setInt(5,5);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Fo");
        preparedStatement1.setInt(2,22);
        preparedStatement1.setString(3,"370 gram");
        preparedStatement1.setString(4,"images/dish/fo.png");
        preparedStatement1.setInt(5,5);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Ramen with pork");
        preparedStatement1.setInt(2,23);
        preparedStatement1.setString(3,"380 gram");
        preparedStatement1.setString(4,"images/dish/ramen.png");
        preparedStatement1.setInt(5,5);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Borscht with dried beets");
        preparedStatement1.setInt(2,19);
        preparedStatement1.setString(3,"400 gram");
        preparedStatement1.setString(4,"images/dish/borsch.png");
        preparedStatement1.setInt(5,5);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Farm tomato gazpacho");
        preparedStatement1.setInt(2,21);
        preparedStatement1.setString(3,"390 gram");
        preparedStatement1.setString(4,"images/dish/gaspacho.png");
        preparedStatement1.setInt(5,5);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();

        //deserts 6
        preparedStatement1.setString(1,"Lingonberry cake with white chocolate");
        preparedStatement1.setInt(2,14);
        preparedStatement1.setString(3,"100 gram");
        preparedStatement1.setString(4,"images/dish/lingonberry_cake.png");
        preparedStatement1.setInt(5,6);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Napoleon");
        preparedStatement1.setInt(2,9);
        preparedStatement1.setString(3,"150 gram");
        preparedStatement1.setString(4,"images/dish/napoleon.png");
        preparedStatement1.setInt(5,6);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Mandarine waffles");
        preparedStatement1.setInt(2,13);
        preparedStatement1.setString(3,"120 gram");
        preparedStatement1.setString(4,"images/dish/mandarine_waffle.png");
        preparedStatement1.setInt(5,6);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Cherry waffles");
        preparedStatement1.setInt(2,12);
        preparedStatement1.setString(3,"170 gram");
        preparedStatement1.setString(4,"images/dish/cherry_waffle.png");
        preparedStatement1.setInt(5,6);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Banana waffles");
        preparedStatement1.setInt(2,11);
        preparedStatement1.setString(3,"150 gram");
        preparedStatement1.setString(4,"images/dish/banana_waffle.png");
        preparedStatement1.setInt(5,6);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();

        //drinks 7
        preparedStatement1.setString(1,"Lemonade classic");
        preparedStatement1.setInt(2,10);
        preparedStatement1.setString(3,"500 gram");
        preparedStatement1.setString(4,"images/dish/lemonade.png");
        preparedStatement1.setInt(5,7);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Tarhun");
        preparedStatement1.setInt(2,10);
        preparedStatement1.setString(3,"500 gram");
        preparedStatement1.setString(4,"images/dish/tarhun.png");
        preparedStatement1.setInt(5,7);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Lemonade with basil and strawberry");
        preparedStatement1.setInt(2,10);
        preparedStatement1.setString(3,"500 gram");
        preparedStatement1.setString(4,"images/dish/lemonde_basil.png");
        preparedStatement1.setInt(5,7);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Lemonade with orange and rosemary");
        preparedStatement1.setInt(2,10);
        preparedStatement1.setString(3,"500 gram");
        preparedStatement1.setString(4,"images/dish/lemonade_orange.png");
        preparedStatement1.setInt(5,7);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
        preparedStatement1.setString(1,"Milk cold dre");
        preparedStatement1.setInt(2,10);
        preparedStatement1.setString(3,"500 gram");
        preparedStatement1.setString(4,"images/dish/milk_cold.png");
        preparedStatement1.setInt(5,7);
        preparedStatement1.setString(6,"active");
        preparedStatement1.executeUpdate();
    }*/
}
