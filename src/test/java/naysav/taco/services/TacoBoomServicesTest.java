package naysav.taco.services;
import naysav.taco.repository.*;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RunWith(MockitoJUnitRunner.class)
public class TacoBoomServicesTest extends TestCase {

	@Mock
	private Connection connection;

	@Mock
	private PreparedStatement statement1;

	@Mock
	private ResultSet result1;

	// инициализирует Mock объекты.
	@Before
	public void setUp() throws Exception {
		assertNotNull(connection);
//		Mockito.when(getConnection()).thenReturn(connection);
		//Имитация для теста метода findUserNP()
//		Mockito.when(connection.prepareStatement( "Select a.User_Name, a.Password, a.Gender from User_Account a " //
//				+ " where a.User_Name = ? and a.password= ?")).thenReturn(statement1);
//		Mockito.when(statement1.executeQuery()).thenReturn(result1);
//		Mockito.when(result1.next()).thenReturn(true);
//		Mockito.when(result1.getString("Gender")).thenReturn("h");
	}

	/*
	Тест метода findUserNP.
	findUserNP принимает логин userName и пароль password.
	Если находит в БД пользотеля с такими логином и паролем,
	то возвращает объект UserAccount, хранящий userName, password и gender,
	иначе null.
	 */
	@Test
	public void testFindUserNP1() {
		try {
			TacoBoomServices tbs = new TacoBoomServices();
			String userName = "Naysav";
			String password = "123456";
			UserAccount user = tbs.findUserNP(userName, password);
			assertNotNull(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	Тест метода findProduct.
	findProduct принимает code продукта.
	Если находит в БД продукт с таким кодом,
	то возвращает объект Product, содержащий code, name и price продукта,
	иначе null.
	 */
	@Test
	public void testFindProduct() {
		try {
			TacoBoomServices tbs = new TacoBoomServices();
			String code = "P001";
			Product product = tbs.findProduct(code);
			assertNotNull(product);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	Тест метода countOrderPrice.
	findProduct принимает объект UserAccount, содержащий логин, пароль и
	гендер пользователя, и возвращет общую стоимость всех тако этого
	пользотателя.
	*/
	@Test
	public void testCountOrderPrice() {
		try {
			TacoBoomServices tbs = new TacoBoomServices();
			UserAccount user = new UserAccount("Naysav", "M", "123456");
			float orderPrice = tbs.countOrderPrice(user);
			assertNotNull(orderPrice);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
