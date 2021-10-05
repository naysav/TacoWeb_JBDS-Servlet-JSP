package naysav.taco.servlet;

import naysav.taco.beans.Product;
import naysav.taco.beans.UserAccount;
import naysav.taco.utils.DBUtils;
import naysav.taco.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/registration"})
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrationServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		// Forward to /WEB-INF/views/homeNotLogginedView.jsp
		// (Users can not access directly into JSP pages placed in WEB-INF)
//		Для того, чтобы выполнить перенаправление запроса, вначале с помощью метода
//		getServletContext() получаем объект ServletContext, который представляет контекст
//		запроса. Затем с помощью его метода getRequestDispatcher() получаем
//		объект RequestDispatcher. Путь к ресурсу, на который надо выполнить перенаправление,
//		передается в качестве параметра в getRequestDispatcher.
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/registrationView.jsp");

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
		Connection conn = MyUtils.getStoredConnection(request);

		String login = (String) request.getParameter("login");
		String password1 = (String) request.getParameter("password1");
		String password2 = (String) request.getParameter("password2");
		String gender = (String) request.getParameter("gender");

		UserAccount userAccount = new UserAccount(login, gender, password1);

		String errorString = null;

		if (!(password1.equals(password2))) {
			errorString = "Passwords is different!";
		}

		// Паролем является строка [a-zA-Z_0-9]
		// Имеет от 4 до 10 символов.
		String regex = "\\w{4,10}";

		if (password1 == null || !password1.matches(regex) ||
				password2 == null || !password2.matches(regex)) {
			errorString += " Passwords should contain 4-10 characters (a-zA-Z_0-9)!";
		}

		if (errorString == null) {
			try {
				DBUtils.insertUserAccount(conn, userAccount);
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
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}
