package naysav.taco.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnUtils_SQLJDBC {

	// Подключиться к SQL Server.
	// (Использовать библиотеку SQLJDBC)
	public static Connection getSQLServerConnection_SQLJDBC() //
			throws ClassNotFoundException, SQLException {

		// Примечание: Изменить параметры соединения соответствующе.
		String hostName = "localhost";
		String sqlInstanceName = "SQLEXPRESS";
		String database = "mytest";
		String userName = "sa";
		String password = "12345";

		return getSQLServerConnection_SQLJDBC(hostName, sqlInstanceName, database, userName, password);
	}

	// Подключиться к SQLServer, используя библиотеку SQLJDBC.
	private static Connection getSQLServerConnection_SQLJDBC(String hostName, //
	                                                         String sqlInstanceName, String database, String userName, String password)//
			throws ClassNotFoundException, SQLException {

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		// Структура URL Connection для SQLServer
		// Например:
		// jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
		String connectionURL = "jdbc:sqlserver://" + hostName + ":1433" //
				+ ";instance=" + sqlInstanceName + ";databaseName=" + database;

		Connection conn = DriverManager.getConnection(connectionURL, userName, password);
		return conn;
	}

}