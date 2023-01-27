package Banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import classes.PessoaJuridica;

public class PessoaJuridicaDAO {

	public boolean salvar(PessoaJuridica pessoajuridica) {

		String sql = "INSERT INTO  pessoajuridica (nome,email,cnpj,cidadeResidencial,telefone) VALUES (?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoajuridica.getNome());
			pstm.setString(2, pessoajuridica.getEmail());
			pstm.setString(3, pessoajuridica.getCNPJ());
			pstm.setString(4, pessoajuridica.getCidade());
			pstm.setString(5, pessoajuridica.getTelefone());

			pstm.execute();
			System.out.println("Pessoa Juridica salva com sucesso");
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

	public boolean Editar(PessoaJuridica pessoajuridica) {
		String sql = "UPDATE pessoajuridica SET Nome = ?, Cnpj = ?, email = ?, cidadeResidencial = ?, telefone =? WHERE id  =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoajuridica.getNome());
			pstm.setString(2, pessoajuridica.getCNPJ());
			pstm.setString(3, pessoajuridica.getEmail());
			pstm.setString(4,pessoajuridica.getCidade());
			pstm.setString(5, pessoajuridica.getTelefone());
			pstm.setInt(6, pessoajuridica.getId());

			pstm.execute();
			System.out.println("Pessoa juridica EDITADA com sucesso");
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

	public boolean remover(PessoaJuridica pessoajuridica) {

		String sql = "DELETE FROM pessoajuridica  WHERE  cnpj =?";

		Connection conn = null;
		PreparedStatement pstm = null;
		try {

			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			pstm.setString(1, pessoajuridica.getCNPJ());

			pstm.execute();
			System.out.println("Pessoa juridica EDITADAn com sucesso");
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

	public List<PessoaJuridica> consultar(String descricao) {

		String sql;
		if (descricao.length() == 0) {
			sql = "Select * from pessoajuridica";
		} else {
			sql = "Select * from pessoajuridica WHERE  cnpj like '%" + descricao + "%'";
		}

		List<PessoaJuridica> pessoas = new ArrayList<PessoaJuridica>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet reset = null;

		try {
			conn = FabricaConexao.criarConexaoMysql();

			pstm = conn.prepareStatement(sql);

			reset = pstm.executeQuery();

			while (reset.next()) {

				PessoaJuridica x = new PessoaJuridica();

				x.setNome(reset.getString("nome"));
				x.setCNPJ(reset.getString("CNPJ"));
				x.setEmail(reset.getString("email"));
				x.setCidade(reset.getString("cidadeResidencial"));
				x.setTelefone(reset.getString("Telefone"));
				x.setId(reset.getInt("Id"));

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
