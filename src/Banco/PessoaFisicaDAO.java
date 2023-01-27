package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import classes.PessoaFisica;

public class PessoaFisicaDAO {

	public boolean salvar(PessoaFisica pessoafisica) {

		String sql = "INSERT INTO  pessoaFisica (nome,telefone,cpf,email,cidadeResidencial) VALUES (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoafisica.getNome());
			pstm.setString(2, pessoafisica.getTelefone());
			pstm.setString(3, pessoafisica.getCPF());
			pstm.setString(4, pessoafisica.getEmail());
			pstm.setString(5, pessoafisica.getCidade());

			pstm.execute();
			System.out.println("Pessoa Fisica salva com sucesso");
			
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

	public boolean Editar(PessoaFisica pessoafisica) {
		String sql = "UPDATE pessoafisica SET Nome = ?, CPF = ?, email = ?, cidadeResidencial = ?, telefone = ? WHERE id =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoafisica.getNome());
			pstm.setString(2, pessoafisica.getCPF());
			pstm.setString(3, pessoafisica.getEmail());
			pstm.setString(4,pessoafisica.getCidade());
			pstm.setString(5, pessoafisica.getTelefone());
			pstm.setInt(6, pessoafisica.getId());

			pstm.execute();
			System.out.println("Pessoa EDITADAn com sucesso");
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

	public boolean remover(PessoaFisica pessoafisica) {

		String sql = "DELETE FROM pessoafisica  WHERE  CPF =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoafisica.getCPF());

			pstm.execute();
			System.out.println("Pessoa fisica EDITADAn com sucesso");
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

	public List<PessoaFisica> consultar(String descricao) {

		String sql;
		if (descricao.length() == 0) {
			sql = "Select * from pessoafisica";
		} else {
			sql = "Select * from pessoafisica WHERE  cpf like '%" + descricao + "%'";
		}

		List<PessoaFisica> pessoas = new ArrayList<PessoaFisica>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				PessoaFisica x = new PessoaFisica();

				x.setNome(reset.getString("nome"));
				x.setCPF(reset.getString("CPF"));
				x.setEmail(reset.getString("email"));
				x.setTelefone(reset.getString("telefone"));
				x.setCidade(reset.getString("cidadeResidencial"));
				x.setId(reset.getInt("id"));

				pessoas.add(x);

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

		return pessoas;

	}

}