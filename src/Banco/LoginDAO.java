package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import classes.Login;

public class LoginDAO {

	public boolean salvar(Login login) {

		String sql = "INSERT INTO  login (usuario,senha) VALUES (?,md5(?))";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, login.getUsuario());
			pstm.setString(2, login.getSenha());

			pstm.execute();
			System.out.println("login salva com sucesso");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
			return false;
		} finally {

			try {
				if (pstm != null) {
					((Connection) pstm).close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public boolean Editar(Login login) {
		String sql = "UPDATE login SET usuario = ?, senha = md5 (?)";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, login.getUsuario());
			pstm.setString(2, login.getSenha());

			pstm.execute();
			System.out.println("Login EDITADA com sucesso");
			return true ;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e);
			return false ;
		} finally {

			try {
				if (pstm != null) {
					((Connection) pstm).close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public ResultSet consultar(Login login) {

		String sql;
		sql = "Select * from login where usuario = ? and senha = md5 (?) ";

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, login.getUsuario());
			pstm.setString(2, login.getSenha());
			reset = pstm.executeQuery();

			return reset;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
		}
	}

}
