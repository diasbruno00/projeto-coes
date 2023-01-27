package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import classes.Entrevistado;

public class EntrevistadoDAO {
	
	public boolean salvar(Entrevistado entrevistado) {

		String sql = "INSERT INTO  entrevistado (Id_Entrevista,Paciente_cpf) VALUES (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, entrevistado.getIdEntrevista());
			pstm.setString(2,  entrevistado.getCpfPaciente());

			pstm.execute();
			System.out.println("Entrevistado salva com sucesso");
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

	public boolean Editar(Entrevistado entrevistado) {
		String sql = "UPDATE entrevistado SET id_entrevista = ?, paciente_cpf = ? WHERE Id  =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, entrevistado.getIdEntrevista());
			pstm.setString(2, entrevistado.getCpfPaciente());
			pstm.setInt(3, entrevistado.getId());

			pstm.execute();
			System.out.println("Entrevistado EDITADA com sucesso");
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

	public boolean remover(Entrevistado entrevistado) {

		String sql = "DELETE FROM entrevistado  WHERE  Id =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1,entrevistado.getId());

			pstm.execute();
			System.out.println("Entrevistado Deletada  com sucesso");
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

	public List<Entrevistado> consultar(String descricao) {
		
		String sql ;
		if( descricao.length() ==0) {
		sql  = "Select * from entrevistado";
		}else {
		 sql  = "Select * from entrevistado  WHERE  paciente_cpf like '%" + descricao +"%'";
		}
		

		List<Entrevistado> entrevista = new ArrayList<Entrevistado>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Entrevistado x = new Entrevistado();

				x.setCpfPaciente(reset.getString("Paciente_cpf"));
				x.setIdEntrevista(reset.getInt("Id_Entrevista"));
				x.setId(reset.getInt("id"));

				entrevista.add(x);

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

		return entrevista;
		
	}

}
