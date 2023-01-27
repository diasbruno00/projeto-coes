package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import classes.Coordena;
import classes.PessoaJuridica;
import classes.Responsavel;

public class ResponsavelDAO {
	

	public boolean salvarPF(Responsavel responsavel) {

		String sql = "INSERT INTO  responsavel ( Pcpf, PfCpf) VALUES (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, responsavel.getCpfPaciente());
			pstm.setString(2, responsavel.getIdentificador());

			pstm.execute();
			System.out.println("Responsavel PF salvo com sucesso");
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


	public boolean salvarPJ(Responsavel responsavel) {

		String sql = "INSERT INTO  responsavel ( Pcpf, PjCnpj) VALUES (?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, responsavel.getCpfPaciente());
			pstm.setString(2, responsavel.getIdentificador());

			pstm.execute();
			System.out.println("Responsavel PJ salvo com sucesso");
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
	
	public boolean EditarPF(Responsavel responsavel) {
		String sql = "UPDATE responsavel SET Pcpf = ?, Pfcpf = ? WHERE id =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, responsavel.getCpfPaciente());
			pstm.setString(2, responsavel.getIdentificador());
			pstm.setInt(3, responsavel.getID());

			pstm.execute();
			System.out.println("Responsavel Editado com sucesso");
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
	
	public boolean EditarPj(Responsavel responsavel) {
		String sql = "UPDATE responsavel SET Pcpf = ?, Pjcnpj = ? WHERE id =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, responsavel.getCpfPaciente());
			pstm.setString(2, responsavel.getPJCnpj());
			pstm.setInt(3, responsavel.getID());

			pstm.execute();
			System.out.println("Responsavel Editado com sucesso");
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

	public boolean remover(Responsavel responsavel) {

		String sql = "DELETE FROM responsavel  WHERE id = ? ";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setInt(1,responsavel.getID());

			pstm.execute();
			System.out.println("Responsavel PF  Deletada  com sucesso");
			return true ;

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
	
	


	public List<Responsavel > consultar(String descricao) {
		
		String sql ;
		if(descricao.length() ==0) {
		sql  = "Select * from responsavel";
		}else {
		 sql  = "Select * from responsavel WHERE  pcpf like '%" + descricao +"%'";
		}
		

		List<Responsavel > responsavel = new ArrayList<Responsavel>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				Responsavel  x = new  Responsavel();

				x.setCpfPaciente(reset.getString("Pcpf"));
				x.setIdentificador(reset.getString("Pfcpf"));
				x.setPJCnpj(reset.getString("Pjcnpj"));
				x.setID(reset.getInt("ID"));

				responsavel.add(x);

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

		return responsavel;
		
	}

	
}
