package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.Paciente;

public class PacienteDAO {

	public boolean salvar(Paciente paciente) {

		String sql = "INSERT INTO  paciente (Nome,idade,pacienteCpf) VALUES (?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, paciente.getNomeCompleto());
			pstm.setInt(2, paciente.getIdade());
			pstm.setString(3, paciente.getCpf());

			pstm.execute();
			System.out.println("Pessoa salva com sucesso");
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

	public boolean Editar(Paciente paciente) {
		String sql = "UPDATE paciente  SET pacienteCpf = ?, Nome = ?, idade = ? where  id = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(2, paciente.getNomeCompleto());
			pstm.setInt(3, paciente.getIdade());
			pstm.setString(1, paciente.getCpf());
			pstm.setInt(4, paciente.getIdentificador());
			

			pstm.execute();
			System.out.println("Pessoa EDITADA com sucesso");
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

	public boolean remover(Paciente paciente) {

		String sql = "DELETE FROM paciente  WHERE  id =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1,paciente.getIdentificador());

			pstm.execute();
			System.out.println("Pessoa Deletada  com sucesso");
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

	public List<Paciente> consultar(String descricao) {
		
		String sql ;
		if(descricao.length() ==0) {
		sql  = "Select * from paciente";
		}else {
		 sql  = "Select * from paciente WHERE  nome like '%" + descricao +"%'";
		}
		

		List<Paciente> paciente = new ArrayList<Paciente>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Paciente x = new Paciente();

				x.setNomeCompleto(reset.getString("nome"));
				x.setIdade(reset.getInt("idade"));
				x.setCpf(reset.getString("pacienteCpf"));
				x.setIdentificador(reset.getInt("Id"));

				paciente.add(x);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reset != null) {
					reset.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {

				e2.printStackTrace();
			}

		}

		return paciente;
		
	}

}
