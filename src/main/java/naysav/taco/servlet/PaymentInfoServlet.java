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

@WebServlet(urlPatterns = { "/paymentInfo"})
public class PaymentInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PaymentInfoServlet() {
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

		Connection conn = MyUtils.getStoredConnection(request);
		String errorString = null;
		float orderPrice = 0;

		try {
			orderPrice = DBUtils.countOrderPrice(conn, loginedUser);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		try {
			DBUtils.cleanBasket(conn, loginedUser);
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
