package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

	private static final String usuario = "root";
	private static final String senha = "BancoCoes";
	private static final String localHost = "jdbc:mysql://localhost:3306/coes";

	public static Connection criarConexaoMysql() throws ClassNotFoundException {
		Connection conexao = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
			conexao = DriverManager.getConnection(localHost, usuario, senha);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERRO AO CRIAR CONEXAO");
		}
		return conexao;

	}
	
	public static void main(String[] args) throws SQLException {
		
		Connection con = null;
		try {
			con = criarConexaoMysql();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("erro ao abrir conexao");
		}
		
		if(con != null) {
			System.out.println("erro ao fechar conexao");
			con.close();
		}
	}
	

}
