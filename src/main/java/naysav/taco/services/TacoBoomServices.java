package naysav.taco.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import naysav.taco.repository.*;


// Класс записывает, удаляет, читает данные из БД tacoweb
public class TacoBoomServices {
	// Метод создает connection к схеме tacoweb (MySQL)
	public static Connection getConnection()
			throws ClassNotFoundException, SQLException {
		// Параметры соединения, соответствующие структуре:
		// jdbc:mysql://localhost:3306/tacoweb
		String hostName = "localhost";
		String dbName = "tacoweb";
		String userName = "root";
		String password = "root";

		Class.forName("com.mysql.jdbc.Driver");

		// Структура URL Connection для MySQL:
		// jdbc:mysql://localhost:3306/tacoweb
		String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

		Connection conn = DriverManager.getConnection(connectionURL, userName,
				password);
		return conn;
	}

	/*
	Метод ищет в БД USER_ACCOUNT логин, пароль, а так же пол пользователя
	и возвращает их в объекте UserAccount, если такой аккаунт существует,
	чтобы установить в качестве атрибута сессии loginedUser, а иначе
	возвращает null.
	*/
	public static UserAccount findUserNP(String userName, String password)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			connection = getConnection();
			String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
					+ " where a.User_Name = ? and a.password= ?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, userName);
			pStatement.setString(2, password);
			result = pStatement.executeQuery();

			if (result.next()) {
				String gender = result.getString("Gender");
				UserAccount user = new UserAccount();
				user.setUserName(userName);
				user.setPassword(password);
				user.setGender(gender);
				return user;
			}
			return null;
		} finally {
			close(connection, pStatement, result);
		}
	}

	// Метод ищет в таблице USER_ACCOUNT пользователя
	// по логину для проверки Cookie
	public static UserAccount findUser(String userName)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			connection = getConnection();
			String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a "//
					+ " where a.User_Name = ? ";

			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, userName);

			result = pStatement.executeQuery();

			if (result.next()) {
				String password = result.getString("Password");
				String gender = result.getString("Gender");
				UserAccount user = new UserAccount();
				user.setUserName(userName);
				user.setPassword(password);
				user.setGender(gender);
				return user;
			}
			return null;
		} finally {
			close(connection, pStatement, result);
		}
	}

	/*
	Метод вставляет информацию о пользователе
	(userName, password, gender) в таблицу USER_ACCOUNT
	*/
	public static void insertUserAccount(UserAccount userAccount)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = getConnection();
			String sql = "Insert into User_Account(USER_NAME, GENDER, PASSWORD) values (?,?,?)";

			pStatement = connection.prepareStatement(sql);

			pStatement.setString(1, userAccount.getUserName());
			pStatement.setString(2, userAccount.getGender());
			pStatement.setString(3, userAccount.getPassword());

			pStatement.executeUpdate();
		} finally {
			close(connection, pStatement, null);
		}
	}

	/*
	Метод выполняет поиск продукта по его коду в таблице PRODUCT
	и возвращает его код (code), наименование (name) и цену (Price)
	в объекте Product
	*/
	public static Product findProduct(String code)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			connection = getConnection();
			String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";

			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, code);

			result  = pStatement.executeQuery();

			while (result.next()) {
				String name = result.getString("Name");
				float price = result.getFloat("Price");
				Product product = new Product(code, name, price);
				return product;
			}
			return null;
		} finally {
			close(connection, pStatement, result);
		}
	}

	/*
	Метод вставляет тако (его ингредиенты), собранный пользователем на странице
	"Собрать тако", и его номер (ID), получаемый из подсчета уже имеющихся ID тако,
	в БД TACOS
	*/
	public static void insertTaco(Taco taco)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {

			String sqlID = "SELECT count(*) AS res FROM tacos WHERE user = ?";
			connection = getConnection();
			pStatement = connection.prepareStatement(sqlID);
			pStatement.setString(1, taco.getUser());
			result = pStatement.executeQuery();
			int id = 0;
			if (result.next())
				id = result.getInt("res") + 1;

			String sql = "Insert into tacos(user, id, flapjack, chicken, garlic, onion, tomato, haricot, cheese, avocado, total_price) " +
					"values (?,?,?,?,?,?,?,?,?,?,?)";
			pStatement = connection.prepareStatement(sql);

			pStatement.setString(1, taco.getUser());
			pStatement.setInt(2, id);
			pStatement.setString(3, taco.getFlapjack().getName());
			pStatement.setString(4, taco.getChicken().getName());
			pStatement.setString(5, taco.getGarlic().getName());
			pStatement.setString(6, taco.getOnion().getName());
			pStatement.setString(7, taco.getTomato().getName());
			pStatement.setString(8, taco.getHaricot().getName());
			pStatement.setString(9, taco.getCheese().getName());
			pStatement.setString(10, taco.getAvocado().getName());
			pStatement.setFloat(11, taco.getTotalPrice());

			pStatement.executeUpdate();
		} finally {
			close(connection, pStatement, result);
		}
	}

	/*
	 Метод создает список, содержащий в себе все собранные пользователем тако,
	 для последующего отображения в корзине пользователя.
	 */
	public static List<Taco> queryTaco(UserAccount loginedUser)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			String sql = "Select a.id, a.flapjack, a.chicken, a.garlic, a.onion, a.tomato, a.haricot, a.cheese, a.avocado," +
					" a.total_price from tacos a WHERE user = ?";
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);

			pStatement.setString(1, loginedUser.getUserName());

			result = pStatement.executeQuery();
			List<Taco> list = new ArrayList<Taco>();
			while (result.next()) {
				Taco taco = new Taco(result.getInt("id"),
						result.getString("flapjack"),
						result.getString("chicken"),
						result.getString("garlic"),
						result.getString("onion"),
						result.getString("tomato"),
						result.getString("haricot"),
						result.getString("cheese"),
						result.getString("avocado"),
						result.getFloat("total_price"));
				list.add(taco);
			}
			return list;
		} finally {
			close(connection, pStatement, result);
		}
	}

	// Метод удаляет тако, который захотел удалить пользователь из корзины,
	// с пересчетом ID всех тако этого пользователя
	public static void deleteTaco(UserAccount loginedUser, int id)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			String sql = "DELETE FROM tacos WHERE id = ? AND user = ?";
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);

			pStatement.setInt(1, id);
			pStatement.setString(2, loginedUser.getUserName());

			pStatement.executeUpdate();

			sql = "UPDATE tacos SET id = id - 1 WHERE id > ? AND user = ?";
			pStatement = connection.prepareStatement(sql);

			pStatement.setInt(1, id);
			pStatement.setString(2, loginedUser.getUserName());

			pStatement.executeUpdate();
		} finally {
			close(connection, pStatement, null);
		}
	}

	// Метод считает итоговую стоимость всех тако, собранных пользователем, для отображения в корзине
	public static float countOrderPrice(UserAccount loginedUser)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			String sql = "SELECT SUM(total_price) AS res FROM tacos WHERE user = ?";
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, loginedUser.getUserName());

			result = pStatement.executeQuery();

			float orderPrice = 0;
			if (result.next())
				orderPrice = result.getInt("res");

			return orderPrice;
		} finally {
			close(connection, pStatement, result);
		}
	}

	// Метод удаляет все тако пользователя из таблицы TACOS для
	// очистки корзины пользователя после успешной оплаты
	public static void cleanBasket(UserAccount loginedUser)
			throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			String sql = "DELETE FROM tacos WHERE user = ?";
			connection = getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, loginedUser.getUserName());

			pStatement.executeUpdate();
		} finally {
			close(connection, pStatement, null);
		}
	}

	// Метод закрывает соединение с БД
	private static void close(Connection connection, Statement statement, ResultSet result) {
		try {
			if (connection != null) connection.close();
			if (statement != null) statement.close();
			if (result != null) result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
