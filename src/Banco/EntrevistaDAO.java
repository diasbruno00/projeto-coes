package Banco;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.Entrevista;

public class EntrevistaDAO {


	public boolean salvar(Entrevista entrevista) {

		String sql = "INSERT INTO  entrevista (dataEntrevista,horaEntrevista) VALUES (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setDate(1,(Date) entrevista.getDataEntrevista());
			pstm.setString(2,  entrevista.getHorarioEntrevista());

			pstm.execute();
			System.out.println("Entrevista salva com sucesso");
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

	public boolean Editar(Entrevista entrevista) {
		String sql = "UPDATE entrevista SET dataEntrevista = ?, horaEntrevista = ? WHERE IdEntrevista  =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setDate(1, (Date) entrevista.getDataEntrevista());
			pstm.setString(2, entrevista.getHorarioEntrevista());
			pstm.setInt(3, entrevista.getIdEntrevista());

			pstm.execute();
			System.out.println("Entrevista EDITADA com sucesso");
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

	public boolean remover(Entrevista entrevista) {

		String sql = "DELETE FROM entrevista  WHERE  IdEntrevista =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1,entrevista.getIdEntrevista());

			pstm.execute();
			System.out.println("Entrevista Deletada  com sucesso");
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

	public List<Entrevista> consultar(String descricao) {
		
		String sql ;
		if(((CharSequence) descricao).length() ==0) {
		sql  = "Select * from entrevista";
		}else {
		 sql  = "Select * from entrevista WHERE  dataEntrevista like '%" + descricao +"%'";
		}
		

		List<Entrevista> entrevista = new ArrayList<Entrevista>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Entrevista x = new Entrevista();

				x.setDataEntrevista( reset.getDate("dataEntrevista"));
				x.setHorarioEntrevista(reset.getString("horaEntrevista"));
				x.setIdEntrevista(reset.getInt("IdEntrevista"));

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
