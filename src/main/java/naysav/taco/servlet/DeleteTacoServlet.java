package naysav.taco.servlet;


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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/deleteTaco" })
public class DeleteTacoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeleteTacoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String parameter = (String) request.getParameter("id");
		int id = Integer.parseInt(parameter);

		HttpSession session = request.getSession();
		// Внести тако определенного пользователя в корзину
		UserAccount loginedUser = MyUtils.getLoginedUser(session);

		String errorString = null;

		try {
			DBUtils.deleteTaco(conn, loginedUser, id);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}

		// Если происходит ошибка, forward (перенаправить) к странице оповещающей ошибку.
		if (errorString != null) {
			// Сохранить информацию в request attribute перед тем как forward к views.
			request.setAttribute("errorString", errorString);
			//
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/deleteTacoErrorView.jsp");
			dispatcher.forward(request, response);
		}
		// Если все хорошо.
		// Redirect (перенаправить) к странице со списком тако.
		else {
			response.sendRedirect(request.getContextPath() + "/tacoBasket");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
