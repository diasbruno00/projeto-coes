package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.exceptions.SQLError;

import Banco.PessoaFisicaDAO;
import classes.PessoaFisica;
import classes.ValidarCampos;
import javafx.beans.binding.SetBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Alerta;

public class PesquisarPessoaFisica implements Initializable {

	@FXML
	private Button ExcluirImagem = new Button();

	@FXML
	private Button NovoImagem = new Button();

	@FXML
	private Button editarImagem = new Button();

	@FXML
	private Button tabelaImagem = new Button();

	@FXML
	private TextField EnderecoCliente;

	@FXML
	private TextField CIdadeCliente;
	
	@FXML
    private TextField NumeroCliente;

	@FXML
	private TextField IdCPF;

	@FXML
	private TextField IdNome;

	@FXML
	private TextField IdPesquisarDados = new TextField();

	@FXML
	private TextField Dados = new TextField();

	@FXML
	private TextField SetCPF;

	@FXML
	private TextField SetEndereco;

	@FXML
	private TextField SetNumero;

	@FXML
	private TextField SetCidade;

	@FXML
	private TableView<PessoaFisica> Tabela = new TableView<PessoaFisica>();

	private PessoaFisicaDAO conexao = new PessoaFisicaDAO();

	private List<PessoaFisica> lista;

	private ObservableList<PessoaFisica> ob = FXCollections.observableArrayList();

	private PessoaFisica objetoSelecionado = new PessoaFisica();

	int ID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		criarColunas();
		atualizarTabela();
		
		tabelaImagem.setGraphic(new ImageView("/icons/atualizar.png"));
		editarImagem.setGraphic(new ImageView("/icons/editar.png"));
		ExcluirImagem.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		NovoImagem.setGraphic(new ImageView("/icons/AcaoSalvar.png"));

	}

	public void criarColunas() {

		TableColumn<PessoaFisica, String> colunaNome = new TableColumn<>("Nome");
		TableColumn<PessoaFisica, String> colunaCPF = new TableColumn<>("CPF");
		TableColumn<PessoaFisica, String> colunaEmail = new TableColumn<>("Email");
		TableColumn<PessoaFisica, String> colunaTelefone = new TableColumn<>("Telefone");
		TableColumn<PessoaFisica, String> colunaCidade = new TableColumn<>("Cidade Residencial");

		Tabela.getColumns().addAll(colunaNome, colunaCPF, colunaEmail, colunaTelefone, colunaCidade);

		Tabela.setColumnResizePolicy(Tabela.CONSTRAINED_RESIZE_POLICY);

		colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
		colunaCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
		colunaCidade.setCellValueFactory(new PropertyValueFactory("cidade"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(IdPesquisarDados.getText());

		for (PessoaFisica t : lista) {
			ob.add(t);
		}
		Tabela.getItems().setAll(ob);
		Tabela.getSelectionModel().selectFirst();

	}

	@FXML
	void eliminar(ActionEvent event) {

		if (Alerta.msgConfirmaExclusao(
				String.format("Dejesa realmente excluir %s do banco de dados ? ", objetoSelecionado.getNome()))) {
			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				limparFormulario();
				Alerta.mensagemInformacao(String.format("%s Excluido com sucesso", objetoSelecionado.getNome()));
			} else {
				Alerta.mensagemErro("Erro ao excluir");
			}
		}

	}

	@FXML
	void filtrarRegistro(KeyEvent event) {
		atualizarTabela();

	}

	public void setCampoFormulario() {

		objetoSelecionado = Tabela.getItems().get(Tabela.getSelectionModel().getSelectedIndex());
		Dados.setText(objetoSelecionado.getNome());
		String y = objetoSelecionado.getCPF();
		SetCPF.setText(y);
		SetEndereco.setText(objetoSelecionado.getEmail());
		SetCidade.setText(objetoSelecionado.getCidade());
		SetNumero.setText(String.valueOf(objetoSelecionado.getTelefone()));
		ID = objetoSelecionado.getId();

	}

	@FXML
	void MoverTabela(KeyEvent event) {
		setCampoFormulario();
	}

	@FXML
	void ClicarNaTabela(MouseEvent event) {
		setCampoFormulario();

	}

	@FXML
	void CadastroNovoFuncionario(ActionEvent event) {
		try {
			abrirFormulario("CadastroCliente");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void CarregarTabela(ActionEvent event) {
		atualizarTabela();
	}

	public void abrirFormulario(String formulario) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/" + formulario + ".fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

	}

	public void limparFormulario() {
		Dados.clear();
		SetCPF.clear();
		SetEndereco.clear();
		SetNumero.clear();
        SetCidade.clear();
	}

	@FXML
	void SalvarEdicao(ActionEvent event) {

		PessoaFisica cliente = new PessoaFisica();

		cliente.setCPF(SetCPF.getText());
		cliente.setNome(Dados.getText());
		cliente.setEmail(SetEndereco.getText());
		cliente.setCidade(SetCidade.getText());
		cliente.setTelefone(SetNumero.getText());
		cliente.setId(ID);

		if (Alerta.msgConfirmaEdicao(String.format("Deseja Editar os dados da %s ?", objetoSelecionado.getNome()))) {

			if (ValidarCampos.validarCpf(cliente.getCPF()) && ValidarCampos.validarTelefone(cliente.getTelefone())) {
				if (conexao.Editar(cliente)) {
					atualizarTabela();
					Alerta.mensagemInformacao("Cadastro com sucesso");
				} else {
					Alerta.mensagemErro("Erro ao tentar editar dados");

				}
			} else {
				Alerta.mensagemErro("CPF ou telefone invalido");

			}
		}

	}

	@FXML
	void LimparDadosClientes(ActionEvent event) {

		IdCPF.clear();
		IdNome.clear();
		EnderecoCliente.clear();
		NumeroCliente.clear();
		CIdadeCliente.clear();
		IdCPF.requestFocus();

	}

	@FXML
	void SalvarDadosCliente(ActionEvent event) throws SQLException {

		PessoaFisica pessoafisica = new PessoaFisica();
		pessoafisica.setCPF(IdCPF.getText());
		pessoafisica.setNome(IdNome.getText());
		pessoafisica.setEmail(EnderecoCliente.getText());
		pessoafisica.setCidade(CIdadeCliente.getText());
		pessoafisica.setTelefone(NumeroCliente.getText());

		if (ValidarCampos.validarCpf(pessoafisica.getCPF()) && ValidarCampos.validarTelefone(pessoafisica.getTelefone())) {

			if (conexao.salvar(pessoafisica)) {
				Alerta.mensagemInformacao(
						String.format("%s salvo com sucesso no banco de dados", pessoafisica.getNome()));
				LimparDadosClientes(event);
			} else {
				Alerta.mensagemErro(String.format("Ocoreu um erro %s nao foi possivel salvar", pessoafisica.getNome()));
			}

		} else {
			Alerta.mensagemErro("CPF informado invalido ou Telefone incorreto");
		}
	}
}
