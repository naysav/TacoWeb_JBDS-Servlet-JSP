package naysav.taco.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import naysav.taco.beans.Taco;
import naysav.taco.beans.UserAccount;
import naysav.taco.utils.DBUtils;
import naysav.taco.utils.MyUtils;

@WebServlet(urlPatterns = { "/tacoBasket" })
public class TacoBasketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TacoBasketServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		// Если еще не вошел в систему (login).
		if (loginedUser == null) {
			session.setAttribute("returnPage", "/tacoBasket");
			// Redirect (Перенаправить) к странице login.
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		// Сохранить информацию в request attribute перед тем как forward (перенаправить).
		request.setAttribute("user", loginedUser);

		Connection conn = MyUtils.getStoredConnection(request);

		String errorString = null;

		List<Taco> list = null;
		try {
			list = DBUtils.queryTaco(conn, loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		float orderPrice = 0;
		try {
			orderPrice = DBUtils.countOrderPrice(conn, loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}


		// Сохранить информацию в request attribute перед тем как forward к views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("tacoList", list);
		request.setAttribute("orderPrice", orderPrice);

		// Forward к /WEB-INF/views/createTacoView.jsp
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/tacoBasketView.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}