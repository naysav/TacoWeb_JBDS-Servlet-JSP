package naysav.taco.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import naysav.taco.repository.Product;
import naysav.taco.repository.Taco;
import naysav.taco.repository.UserAccount;

public class TacoServices {

	public static UserAccount findUser(Connection conn, //
	                                   String userName, String password) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " //
				+ " where a.User_Name = ? and a.password= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, String userName) throws SQLException {

		String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a "//
				+ " where a.User_Name = ? ";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("Password");
			String gender = rs.getString("Gender");
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setGender(gender);
			return user;
		}
		return null;
	}

	public static void insertUserAccount(Connection conn, UserAccount userAccount) throws SQLException {
		String sql = "Insert into User_Account(USER_NAME, GENDER, PASSWORD) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, userAccount.getUserName());
		pstm.setString(2, userAccount.getGender());
		pstm.setString(3, userAccount.getPassword());

		pstm.executeUpdate();
	}

	public static List<Product> queryProduct(Connection conn) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<Product> list = new ArrayList<Product>();
		while (rs.next()) {
			String code = rs.getString("Code");
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product();
			product.setCode(code);
			product.setName(name);
			product.setPrice(price);
			list.add(product);
		}
		return list;
	}

	public static Product findProduct(Connection conn, String code) throws SQLException {
		String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, code);

		ResultSet rs = pstm.executeQuery();

		while (rs.next()) {
			String name = rs.getString("Name");
			float price = rs.getFloat("Price");
			Product product = new Product(code, name, price);
			return product;
		}
		return null;
	}

	public static void updateProduct(Connection conn, Product product) throws SQLException {
		String sql = "Update Product set Name =?, Price=? where Code=? ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getName());
		pstm.setFloat(2, product.getPrice());
		pstm.setString(3, product.getCode());
		pstm.executeUpdate();
	}

	public static void insertProduct(Connection conn, Product product) throws SQLException {
		String sql = "Insert into Product(Code, Name, Price) values (?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, product.getCode());
		pstm.setString(2, product.getName());
		pstm.setFloat(3, product.getPrice());

		pstm.executeUpdate();
	}

	public static void deleteProduct(Connection conn, String code) throws SQLException {
		String sql = "Delete From Product where Code= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, code);

		pstm.executeUpdate();
	}

	// Метод вставляет тако (его ингредиенты), собранный пользователем на странице
	// "Собрать тако", и его номер (ID), получаемый из подсчета уже имеющихся ID тако,
	// в БД TACOS
	public static void insertTaco(Connection conn, Taco taco) throws SQLException {
		String sqlID = "SELECT count(*) AS res FROM tacos WHERE user = ?";

		PreparedStatement pstmID = conn.prepareStatement(sqlID);
		pstmID.setString(1, taco.getUser());
		ResultSet rs = pstmID.executeQuery();
		int id = 0;
		if (rs.next())
			id = rs.getInt("res") + 1;

		String sql = "Insert into tacos(user, id, flapjack, chicken, garlic, onion, tomato, haricot, cheese, avocado, total_price) " +
				"values (?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, taco.getUser());
		pstm.setInt(2, id);
		pstm.setString(3, taco.getFlapjack().getName());
		pstm.setString(4, taco.getChicken().getName());
		pstm.setString(5, taco.getGarlic().getName());
		pstm.setString(6, taco.getOnion().getName());
		pstm.setString(7, taco.getTomato().getName());
		pstm.setString(8, taco.getHaricot().getName());
		pstm.setString(9, taco.getCheese().getName());
		pstm.setString(10, taco.getAvocado().getName());
		pstm.setFloat(11, taco.getTotalPrice());

		pstm.executeUpdate();
	}

	// Метод создает список, содержащий в себе все собранные пользователем тако,
	// для последующего отображения в корзине пользователя.
	// Выбрасыет SQLException, если
	public static List<Taco> queryTaco(Connection conn, UserAccount loginedUser) throws SQLException {
		String sql = "Select a.id, a.flapjack, a.chicken, a.garlic, a.onion, a.tomato, a.haricot, a.cheese, a.avocado," +
				" a.total_price from tacos a WHERE user = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, loginedUser.getUserName());

		ResultSet rs = pstm.executeQuery();
		List<Taco> list = new ArrayList<Taco>();
		while (rs.next()) {
			Taco taco = new Taco(rs.getInt("id"),
					rs.getString("flapjack"),
					rs.getString("chicken"),
					rs.getString("garlic"),
					rs.getString("onion"),
					rs.getString("tomato"),
					rs.getString("haricot"),
					rs.getString("cheese"),
					rs.getString("avocado"),
					rs.getFloat("total_price"));
			list.add(taco);
		}
		return list;
	}

	// Метод удаляет тако, который захотел удалить пользователь из корзины,
	// с пересчетом ID
	public static void deleteTaco(Connection conn, UserAccount loginedUser, int id) throws SQLException {
		String sqlDelete = "DELETE FROM tacos WHERE id = ? AND user = ?";

		PreparedStatement pstm = conn.prepareStatement(sqlDelete);

		pstm.setInt(1, id);
		pstm.setString(2, loginedUser.getUserName());

		pstm.executeUpdate();

		String sqlRecountID = "UPDATE tacos SET id = id - 1 WHERE id > ? AND user = ?";

		pstm = conn.prepareStatement(sqlRecountID);

		pstm.setInt(1, id);
		pstm.setString(2, loginedUser.getUserName());

		pstm.executeUpdate();
	}

	// Метод считает итоговую стоимость всех тако, собранных пользователем, для отображения в корзине
	public static float countOrderPrice(Connection conn, UserAccount loginedUser) throws SQLException {
		String sql = "SELECT SUM(total_price) AS res FROM tacos WHERE user = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, loginedUser.getUserName());

		ResultSet rs = pstm.executeQuery();

		float orderPrice = 0;
		if (rs.next())
			orderPrice = rs.getInt("res");

		return orderPrice;
	}

	// Метод очищает корзину пользователя после успешной оплаты
	public static void cleanBasket(Connection conn, UserAccount loginedUser) throws SQLException {
		String sql = "DELETE FROM tacos WHERE user = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, loginedUser.getUserName());

		pstm.executeUpdate();
	}
}
