package com.adm_user_JavaDeveloper.java_developer.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.adm_user_JavaDeveloper.java_developer.controller.exceptions.DbException;

public class DB {

	private static Connection conn = null;

	
	public static Connection getConnection() {
		if (conn == null) {
	
			Properties props = LoadProperties();
		    String url = props.getProperty("spring.datasource.url");
		    String user = props.getProperty("spring.datasource.username");
		    String password = props.getProperty("spring.datasource.password");

		    try {
		      
		        if (url == null) {
		            throw new SQLException("A URL de conexão não pode ser nula.");
		        }
		       
		        return DriverManager.getConnection(url, user, password);
		    } catch (SQLException e) {
		        throw new DbException("Erro ao conectar ao banco de dados: " + e.getMessage());
		    }
		}
		return conn;
	}

	private static Properties LoadProperties() {
		try (InputStream fs = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
			Properties props = new Properties();
			if (fs == null) {
		            throw new DbException("Arquivo de propriedades não encontrado!");
		        }
			props.load(fs);
			return props;
			
		} catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	
	
	
}
