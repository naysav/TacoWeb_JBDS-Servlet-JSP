package naysav.taco.servlet;

import naysav.taco.beans.Product;
import naysav.taco.beans.Taco;
import naysav.taco.beans.UserAccount;
import naysav.taco.utils.DBUtils;
import naysav.taco.utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = { "/createTaco" })
public class CreateTacoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateTacoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		// Если еще не вошел в систему (login).
		if (loginedUser == null) {
			session.setAttribute("returnPage", "/createTaco");
			// Redirect (Перенаправить) к странице login.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// Сохранить информацию в request attribute перед тем как forward (перенаправить).
		request.setAttribute("user", loginedUser);

		Connection conn = MyUtils.getStoredConnection(request);

		String errorString = null;
		List<Product> list = null;
		try {
			list = DBUtils.queryProduct(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		// Сохранить информацию в request attribute перед тем как forward к views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", list);

		// Forward к /WEB-INF/views/createTacoView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/createTacoView.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

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

		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		try {
			taco = new Taco(loginedUser.getUserName(),
					DBUtils.findProduct(conn, flapjackCode),
					DBUtils.findProduct(conn, chickenCode),
					DBUtils.findProduct(conn, garlicCode),
					DBUtils.findProduct(conn, onionCode),
					DBUtils.findProduct(conn, tomatoCode),
					DBUtils.findProduct(conn, haricotCode),
					DBUtils.findProduct(conn, cheeseCode),
					DBUtils.findProduct(conn, avocadoCode));
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		if (errorString == null) {
			try {
				DBUtils.insertTaco(conn, taco);
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
}