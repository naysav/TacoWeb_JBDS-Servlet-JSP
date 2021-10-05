package naysav.taco.servlet;

import naysav.taco.beans.UserAccount;
import naysav.taco.beans.Card;
import naysav.taco.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/payment"})
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaymentServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Проверить, вошел ли пользователь в систему (login) или нет.
		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		if (loginedUser == null) {
			session.setAttribute("returnPage", "/tacoBasket");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		request.setAttribute("user", loginedUser);

		// Forward to /WEB-INF/views/homeView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
		RequestDispatcher dispatcher =
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/paymentView.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			response.sendRedirect(request.getContextPath() + "/paymentInfo");
		}
	}

}
