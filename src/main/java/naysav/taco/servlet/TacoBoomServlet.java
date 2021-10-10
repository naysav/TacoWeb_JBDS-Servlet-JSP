package naysav.taco.servlet;
import naysav.taco.repository.*;
import naysav.taco.services.TacoBoomServices;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.rmi.ServerException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/TacoBoom"})
public class TacoBoomServlet extends HttpServlet {

	// ATT_NAME_CONNECTION - имя атрибута, хранящего соединение с БД
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	// ATT_NAME_USER_NAME - имя атрибута, хранящего cookie пользователя
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	public TacoBoomServlet() {
		super();
	}



	private UserAccount getLoginedUser(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");;
		session.setAttribute("loginedUser", loginedUser);
		return loginedUser;
	}

	// Метод отвечает за выбор загружаемой jsp-страницы
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String rule = request.getParameter("rule");
			if (rule == null)
				rule = "homePage";
			switch (rule) {
				case "homePage":
					loadHomePage(request, response);
					break;
				case "loginPage":
					loadLoginPage(request, response);
					break;
				case "registrationPage":
					loadRegistrationPage(request, response);
					break;
				case "createTacoPage":
					loadCreateTacoPage(request, response);
					break;
				case "tacoBasketPage":
					loadTacoBasketPage(request, response);
					break;
				case "deleteTacoProcess":
					deleteTacoProcess(request, response);
					break;
				case "paymentPage":
					loadPaymentPage(request, response);
					break;
				case "paymentInfoPage":
					loadPaymentInfoPage(request, response);
					break;
				case "logoutProcess":
					logoutProcess(request, response);
					break;
				default:
					loadHomePage(request, response);
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.toString());
		}

	}

	// Метод загружет главную страницу с информацией о сервисе - homeView.jsp
	private void loadHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/homeView.jsp");
		UserAccount loginedUser = getLoginedUser(request);

		dispatcher.forward(request, response);
	}

	// Метод загружает авторизационную форму - loginView.jsp
	private void loadLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		UserAccount loginedUser = getLoginedUser(request);

		dispatcher.forward(request, response);
	}

	// Метод загружает регистрационную форму - registrationView.jsp
	private void loadRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/registrationView.jsp");
		UserAccount loginedUser = getLoginedUser(request);

		dispatcher.forward(request, response);
	}

	// Метод загружает форму создания тако из ингредиентов - createTacoView.jsp
	private void loadCreateTacoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/createTacoView.jsp");
		UserAccount loginedUser = getLoginedUser(request);
		HttpSession session = request.getSession();

		if (loginedUser == null) {
			session.setAttribute("returnPage", "createTacoPage");
			// Redirect (Перенаправить) к странице login.
			response.sendRedirect("TacoBoom?rule=loginPage");
			return;
		}

		dispatcher.forward(request, response);
	}

	// Метод загружает страницу со списком созданных пользователем тако
	// и их суммарной стоимостью - tacoBasketView.jsp
	private void loadTacoBasketPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/tacoBasketView.jsp");
		UserAccount loginedUser = getLoginedUser(request);
		HttpSession session = request.getSession();

		if (loginedUser == null) {
			session.setAttribute("returnPage", "tacoBasketPage");
			// Redirect (Перенаправить) к странице login.
			response.sendRedirect("TacoBoom?rule=loginPage");
			return;
		}

		String errorString = null;

		List<Taco> list = null;
		try {
			list = TacoBoomServices.queryTaco(loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		float orderPrice = 0;
		try {
			orderPrice = TacoBoomServices.countOrderPrice(loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// Сохранить информацию в request attribute перед тем как forward к views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("tacoList", list);
		request.setAttribute("orderPrice", orderPrice);
		System.out.println("loadCreateTacoPage");
		dispatcher.forward(request, response);
	}

	/*
	Метод удаляет тако, выбранный пользователем в корзине заказа, из БД tacos
	и перенаправляет на страницу tacoBasketView.jsp с обновленным списком тако
	*/
	private void deleteTacoProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// id тако пользователя, который нужно удалить
		String parameter = (String) request.getParameter("id");
		int id = Integer.parseInt(parameter);

		//
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
		String errorString = null;

		try {
			TacoBoomServices.deleteTaco(loginedUser, id);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// Если происходит ошибка, forward (перенаправить) к странице оповещающей ошибку.
		if (errorString != null) {
			// Сохранить информацию в request attribute перед тем как forward к views.
			request.setAttribute("errorString", errorString);
			// Перенаправление
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/deleteTacoErrorView.jsp");
			dispatcher.forward(request, response);
		}
		// Если все хорошо.
		// Redirect (перенаправить) к странице со списком тако.
		else {
			response.sendRedirect("TacoBoom?rule=" + "tacoBasketPage");
		}
	}

	/*
	Метод загружает страницу с формой для заполнения реквизитов
	банковской картыи их суммарной стоимостью - paymentView.jsp
	 */
	private void loadPaymentPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/paymentView.jsp");
		UserAccount loginedUser = getLoginedUser(request);

		dispatcher.forward(request, response);
	}

	/*
	Метод загружает страницу с информацией об успешной оплате (реквезиты карты и потраченную сумму),
	при этом очищая корзину пользователя - paymentInfoView.jsp
	 */
	private void loadPaymentInfoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// Проверить, вошел ли пользователь в систему (login) или нет.
		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");

		String errorString = null;
		float orderPrice = 0;

		try {
			orderPrice = TacoBoomServices.countOrderPrice(loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		try {
			TacoBoomServices.cleanBasket(loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		request.setAttribute("orderPrice", orderPrice);
		if (errorString != null) {
			request.setAttribute("errorString", errorString);

			// Forward (перенаправить) к странице /WEB-INF/views/paymentView.jsp
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher("/WEB-INF/views/paymentInfoView.jsp");
			dispatcher.forward(request, response);
		}
		// Forward to /WEB-INF/views/paymentInfoView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		else {
			RequestDispatcher dispatcher =
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/paymentInfoView.jsp");
			dispatcher.forward(request, response);
		}
	}

	// Метод выполняет LogOut пользователя
	private void logoutProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		// Удалить атрибут сессии, содержащий логин и пароль и отвечающий за авторизацию пользователя
		session.removeAttribute("loginedUser");

		// Redirect (Перенаправить) к странице homeView.
		response.sendRedirect("TacoBoom?rule=homePage");
		return;
	}

	// Метод отвечает за выбор метода обработки поступивших данных
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RequestDispatcher dispatcher;
			String rule = request.getParameter("rule");
			switch (rule) {
				case "loginProcess":
					loginProcess(request, response);
					break;
				case "registrationProcess":
					registrationProcess(request, response);
					break;
				case "createTacoProcess":
					createTacoProcess(request, response);
					break;
				case "paymentProcess":
					paymentProcess(request, response);
					break;
			}
		} catch (Exception e) {
			//    System.out.println(e);
			throw new ServerException(e.toString());
		}
	}

	/*
	После нажатия кнопки "Войти" на странице loginView.jsp этот метод проверяет валидность
	логина и пароля, ища пользователя с таким логином в таблице USER_ACCOUNT, и в случае, если находит,
	перенаправляет на страницу, откуда была вызвана форма авторизации,
	иначе перенаправляет на страницу loginView.jsp с сообщением об ошибке.
	 */
	private void loginProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String rememberMeStr = request.getParameter("rememberMe");
		boolean remember = "Y".equals(rememberMeStr);

		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;

		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			hasError = true;
			errorString = "Логин и пароль обязательны!";
		} else {
			try {
				// Найти user в DB.
				user = TacoBoomServices.findUserNP(userName, password);

				if (user == null) {
					hasError = true;
					errorString = "Логин или пароль неверный!";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}
		// В случае, если есть ошибка,
		// forward (перенаправить) к /WEB-INF/views/login.jsp
		if (hasError) {
			user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);

			// Сохранить информацию в request attribute перед forward.
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);

			// Forward (перенаправить) к странице /WEB-INF/views/login.jsp
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");

			dispatcher.forward(request, response);
		}
		// В случае, если нет ошибки.
		// Сохранить информацию пользователя в Session.
		// И перенаправить к странице userInfo.
		else {
			HttpSession session = request.getSession();
			// В JSP можно получить доступ через ${loginedUser}
			session.setAttribute("loginedUser", user);

			// Если пользователь выбирает функцию "Remember me".
			// Сохранить информацию пользователя в Cookie.
			if (remember) {
				System.out.println("Store user cookie");
				Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
				// 1 день (Конвертированный в секунды)
				cookieUserName.setMaxAge(24 * 60 * 60);
				response.addCookie(cookieUserName);
			}
			// Наоборот, удалить Cookie
			else {
				Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
				// 0 секунд. (Данный Cookie будет сразу недействителен)
				cookieUserName.setMaxAge(0);
				response.addCookie(cookieUserName);
			}

			// Redirect (Перенаправить) на страницу /userInfo.
			response.sendRedirect("TacoBoom?rule=" + session.getAttribute("returnPage"));
		}
	}

	/*
	После заполнения всех полей на странице registrationView.jsp этот метод
	проверяет есть ли уже такой такой пользователь, совпадают ли пароли и если все хорошо,
	вставляет в таблицу USER_ACCOUNT поля USER_NAME, GENDER, PASSWORD
	 */
	private void registrationProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String login = (String) request.getParameter("login");
		String password1 = (String) request.getParameter("password1");
		String password2 = (String) request.getParameter("password2");
		String gender = (String) request.getParameter("gender");

		UserAccount userAccount = new UserAccount(login, gender, password1);
		UserAccount alreadyUser = null;
		String errorString = null;

		try {
			alreadyUser = TacoBoomServices.findUser(login);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		if (login == alreadyUser.getUserName())
			errorString = "Такой логин уже занят!";

		if (!(password1.equals(password2))) {
			errorString += " Пароли отличаются!";
		}

		// Паролем является строка [a-zA-Z_0-9]
		// Имеет от 4 до 10 символов.
		String regex = "\\w{4,10}";

		if (password1 == null || !password1.matches(regex) ||
				password2 == null || !password2.matches(regex)) {
			errorString += " Пароли должны содержать 4-10 символов (a-zA-Z_0-9)!";
		}

		if (errorString == null) {
			try {
				TacoBoomServices.insertUserAccount(userAccount);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Сохранить информацию в request attribute перед тем как forward к views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("userAccount", userAccount);

		// Если имеется ошибка forward (перенаправления) к странице 'edit'.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/registrationView.jsp");
			dispatcher.forward(request, response);
		}
		// Если все хорошо.
		// Redirect (перенаправить) к странице со списком продуктов.
		else {
			response.sendRedirect("TacoBoom?rule=" + "loginPage");
		}
	}

	/*
	После нажатия кнопки "Добавить в корзину" на странице createTacoView.jsp этот метод
	создает модель тако и вставляет ингредиенты тако, id тако и логин пользователя,
	который его создал, в таблицу TACOS
	 */
	private void createTacoProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flapjackCode = (String) request.getParameter("flapjack");
		String chickenCode = (String) request.getParameter("chicken");
		String garlicCode = (String) request.getParameter("garlic");
		String onionCode = (String) request.getParameter("onion");
		String tomatoCode = (String) request.getParameter("tomato");
		String haricotCode = (String) request.getParameter("haricot");
		String cheeseCode = (String) request.getParameter("cheese");
		String avocadoCode = (String) request.getParameter("avocado");

		Taco taco = new Taco();
		String errorString = null;
		HttpSession session = request.getSession();

		UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");

		try {
			taco = new Taco(loginedUser.getUserName(),
					TacoBoomServices.findProduct(flapjackCode),
					TacoBoomServices.findProduct(chickenCode),
					TacoBoomServices.findProduct(garlicCode),
					TacoBoomServices.findProduct(onionCode),
					TacoBoomServices.findProduct(tomatoCode),
					TacoBoomServices.findProduct(haricotCode),
					TacoBoomServices.findProduct(cheeseCode),
					TacoBoomServices.findProduct(avocadoCode));
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		if (errorString == null) {
			try {
				TacoBoomServices.insertTaco(taco);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}

		// Сохранить информацию в request attribute перед тем как forward к views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("taco", taco);

		// Если имеется ошибка forward (перенаправления) к странице 'edit'.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createTacoView.jsp");
			dispatcher.forward(request, response);
		}
		// Если все хорошо.
		// Redirect (перенаправить) к странице со списком продуктов.
		else {
			request.setAttribute("successMessage", "Тако успешно создан! Состав заказа можно посмотреть в корзине.");
//			response.sendRedirect(request.getContextPath() + "/createTaco");
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/createTacoView.jsp");
			dispatcher.forward(request, response);
		}
	}

	/*
	После нажатия кнопки "Подтвердить" на странице paymentView.jsp этот метод
	проверяет на валидность номер карты, имя ее владельца и срок действия. В случае, если
	все хорошо, перенаправляет на страцу с информацией об успешной оплате, в противном
	случае перенаправляет на страницу paymentInfoView.jsp с сообщением об ошибке.
	 */
	private void paymentProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String number = request.getParameter("number");
		String name = request.getParameter("name");
		String data = request.getParameter("data");

		String errorString = "";

		// Номером карты является строка [0-9]
		// Имеет 16 символов.
		String regexNumber = "\\d{16}";
		if (!number.matches(regexNumber)) {
			errorString = "Номер карты должен состоять из 16 цифр без пробелов!\n";
		}

		// Номером карты является строка [0-9]
		// Имеет 16 символов.
		String regexName = "[ABCDEFGHIJKLMNOPQRSTUVWXYZ]{1,16}[ ][ABCDEFGHIJKLMNOPQRSTUVWXYZ]{1,16}";
		if (!name.matches(regexName)) {
			errorString += " Имя должно состоять из заглавных латинских букв (с 1 пробелом)!\n";
		}

		// Номером карты является строка [0-9]
		// Имеет 5 символов.
		String regexData = "[01]\\d[/]\\d\\d";
		if (!data.matches(regexData)) {
			errorString += " Дата карты заполняется  в формате <месяц/год>!\n";
		}

		if (errorString != "") {
			// Сохранить информацию в request attribute перед forward.
			request.setAttribute("errorString", errorString);

			// Forward (перенаправить) к странице /WEB-INF/views/paymentView.jsp
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher("/WEB-INF/views/paymentView.jsp");

			dispatcher.forward(request, response);
		}
		// В случае, если нет ошибки.
		// Сохранить информацию пользователя в Session.
		// И перенаправить к странице userInfo.
		else {
			// Redirect (Перенаправить) на страницу /paymentInfo.
			HttpSession session = request.getSession();
			Card card = new Card(number, name, data);
			session.setAttribute("bankCard", card);
			response.sendRedirect("TacoBoom?rule=paymentInfoPage");
		}
	}


}
