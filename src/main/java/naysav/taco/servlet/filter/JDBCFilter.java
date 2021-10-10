package naysav.taco.servlet.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {

	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	public JDBCFilter() {
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	// Подключение к MySQL.
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

	public static void closeQuietly(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
		}
	}

	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
		}
	}

	// Проверить является ли Servlet цель текущего request?
	private boolean needJDBC(HttpServletRequest request) {
		System.out.println("JDBC Filter");
		//
		// Servlet Url-pattern: /spath/*
		//
		// => /spath
		String servletPath = request.getServletPath();
		// => /abc/mnp
		String pathInfo = request.getPathInfo();

		String urlPattern = servletPath;

		if (pathInfo != null) {
			// => /spath/*
			urlPattern = servletPath + "/*";
		}

		// Key: servletName.
		// Value: ServletRegistration
		Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();

		// Коллекционировать все Servlet в вашем WebApp.
		Collection<? extends ServletRegistration> values = servletRegistrations.values();
		for (ServletRegistration sr : values) {
			Collection<String> mappings = sr.getMappings();
			if (mappings.contains(urlPattern)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		// Открыть  connection (соединение) только для request со специальной ссылкой.
		// (Например ссылка к servlet, jsp, ..)
		// Избегать открытия Connection для обычных запросов.
		// (Например image, css, javascript,... )
		if (this.needJDBC(req)) {

			System.out.println("Open Connection for: " + req.getServletPath());

			Connection conn = null;
			try {
				// Создать объект Connection подключенный к database.
				conn = getConnection();
				// Настроить автоматический commit false, чтобы активно контролировать.
				conn.setAutoCommit(false);

				// Сохранить объект Connection в attribute в request.
				// Данная информация хранения существует только во время запроса (request)
				// до тех пор, пока данные возвращаются приложению пользователя.
				request.setAttribute(ATT_NAME_CONNECTION, conn);

				// Разрешить request продвигаться далее.
				// (Далее к следующему Filter tiếp или к цели).
				chain.doFilter(request, response);

				// Вызвать метод commit() чтобы завершить транзакцию с DB.
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				rollbackQuietly(conn);
				throw new ServletException();
			} finally {
				closeQuietly(conn);
			}
		}
		// Для обычных request (image,css,html,..)
		// не нужно открывать connection.
		else {
			// Разрешить request продвигаться далее.
			// (Далее к следующему Filter tiếp или к цели).
			chain.doFilter(request, response);
		}

	}

}
