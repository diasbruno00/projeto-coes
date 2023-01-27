package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.Entrevistador;

public class EntrevistadorDAO {

	public Boolean salvar(Entrevistador entrevistador) {

		String sql = "INSERT INTO  entrevistador  (nome,IdProfissional,Categoria) VALUES (?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, entrevistador.getNome());
			pstm.setString(3, entrevistador.getCategoria());
			pstm.setInt(2, entrevistador.getIdProfissional());
			
			pstm.execute();
			System.out.println("Entrevistador salvo com sucesso");
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

	public boolean Editar(Entrevistador entrevistador) {
		String sql = "UPDATE entrevistador SET Nome = ?, IdProfissional = ?, categoria = ? WHERE IdEntrevistador  =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, entrevistador.getNome());
			pstm.setInt(2, entrevistador.getIdProfissional());
			pstm.setString(3, entrevistador.getCategoria());
			pstm.setInt(4, entrevistador.getID());

			pstm.execute();
			System.out.println("Entrevistador EDITADA com sucesso");
			return true;

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

	public boolean remover(Entrevistador entrevistador) {

		String sql = "DELETE FROM entrevistador  WHERE  IdEntrevistador =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1, entrevistador.getID());

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

	public List<Entrevistador> consultar(String descricao) {

		String sql;
		if (descricao.length() == 0) {
			sql = "Select * from entrevistador";
		} else {
			sql = "Select * from entrevistador WHERE  nome like '%" + descricao + "%'";
		}

		List<Entrevistador> entrevistador = new ArrayList<Entrevistador>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Entrevistador x = new Entrevistador();

				x.setID(reset.getInt("IdEntrevistador"));
				x.setNome(reset.getString("nome"));
				x.setIdProfissional(reset.getInt("IdProfissional"));
				x.setCategoria(reset.getString("Categoria"));

				entrevistador.add(x);

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

		return entrevistador;

	}

}
