package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import classes.Coordena;

public class CordenadorDAO {
	

	public boolean salvar(Coordena cordenador) {

		String sql = "INSERT INTO  cordenador (Pcpf,  EntrevistaID) VALUES (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, cordenador.getCpfPaciente());
			pstm.setInt(2, cordenador.getIdEntrevista());

			pstm.execute();
			System.out.println("Cordenador salvo com sucesso");
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
	
	public boolean Editar(Coordena cordenador) {
		String sql = "UPDATE cordenador SET pcpf = ?, entrevistaid = ? WHERE  id = ?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, cordenador.getCpfPaciente());
			pstm.setInt(2, cordenador.getIdEntrevista());
			pstm.setInt(3, cordenador.getIdUnico());

			pstm.execute();
			System.out.println("cordenador EDITADA com sucesso");
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

	public boolean remover(Coordena cordenador) {

		String sql = "DELETE FROM cordenador  Where  EntrevistaID =? ";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1,cordenador.getIdEntrevista());

			pstm.execute();
			System.out.println("cordenador Deletada  com sucesso");
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

	public List<Coordena> consultar(String descricao) {
		
		String sql ;
		if(descricao.length() ==0) {
		sql  = "Select * from cordenador";
		}else {
		 sql  = "Select * from cordenador WHERE  pcpf like '%" + descricao +"%'";
		}
		

		List<Coordena> coordena = new ArrayList<Coordena>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Coordena x = new Coordena();

				x.setCpfPaciente(reset.getString("Pcpf"));
				x.setIdEntrevista(reset.getInt("EntrevistaId"));
				x.setIdUnico(reset.getInt("id"));

				coordena.add(x);

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

		return coordena;
		
	}

}
