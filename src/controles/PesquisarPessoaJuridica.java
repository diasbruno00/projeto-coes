package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.PessoaJuridicaDAO;
import classes.PessoaJuridica;
import classes.ValidarCampos;
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

public class PesquisarPessoaJuridica implements Initializable {

	@FXML
	private Button AtuaalizarImagemIcon = new Button();
	@FXML
	private Button EditarPFicon = new Button();
	@FXML
	private Button ExcluirPJicon = new Button();

	@FXML
	private Button novoPJicon = new Button();

	@FXML
	private TextField AtualizarCNPJ;

	@FXML
	private TextField AtualizarEndereco;

	@FXML
	private TextField AtualizarNome;

	@FXML
	private TextField AtualizarNumero;

	@FXML
	private TextField AtualizarCidade;

	@FXML
	private TextField PesquisarNome = new TextField();

	@FXML
	private TableView<PessoaJuridica> TabelaPessoaJuridica = new TableView<PessoaJuridica>();

	private List<PessoaJuridica> lista;

	private ObservableList<PessoaJuridica> ob = FXCollections.observableArrayList();

	private PessoaJuridica objetoSelecionado = new PessoaJuridica();

	PessoaJuridicaDAO conexao = new PessoaJuridicaDAO();

	int ID;

	@FXML
	private TextField CNPJ_PJ;

	@FXML
	private TextField EnderecoPj;

	@FXML
	private TextField NomePj;

	@FXML
	private TextField CidadePj;
	
	@FXML
	private TextField NumeroPj;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		criarColunas();
		atualizarTabela();
		
		AtuaalizarImagemIcon.setGraphic(new ImageView("/icons/atualizar.png"));
		EditarPFicon.setGraphic(new ImageView("/icons/editar.png"));
		ExcluirPJicon.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		novoPJicon.setGraphic(new ImageView("/icons/AcaoSalvar.png"));

	}

	@FXML
	void LimparDados(ActionEvent event) {

		NomePj.clear();
		EnderecoPj.clear();
		CNPJ_PJ.clear();
		CidadePj.clear();
		NumeroPj.clear();
		NomePj.requestFocus();

	}
	
     public void limparFormulario() {
		AtualizarCidade.clear();
		AtualizarNome.clear();
		AtualizarNumero.clear();
		AtualizarEndereco.clear();
		AtualizarCNPJ.clear();
		
	}

	@FXML
	void SalvarPessoaJuridica(ActionEvent event) {

		PessoaJuridica cliente = new PessoaJuridica();

		cliente.setNome(NomePj.getText());
		cliente.setEmail(EnderecoPj.getText());
		cliente.setCNPJ(CNPJ_PJ.getText());
		cliente.setCidade(CidadePj.getText());
		cliente.setTelefone(NumeroPj.getText());

		if (ValidarCampos.isCNPJ(cliente.getCNPJ()) && ValidarCampos.validarTelefone(cliente.getTelefone())) {

			if (conexao.salvar(cliente)) {
				Alerta.mensagemInformacao(String.format("%s salvo com sucesso no banco de dados", cliente.getNome()));
				atualizarTabela();
				LimparDados(event);

			} else {
				Alerta.mensagemErro(String.format("Ocoreu um erro %s nao foi possivel salvar", cliente.getNome()));
			}

		} else {
			Alerta.mensagemErro("CPNJ  ou Telefone invalido");
		}
	}

	@FXML
	void AtualizarTabela(ActionEvent event) {
		atualizarTabela();

	}

	@FXML
	void ExcluirPj(ActionEvent event) {

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
	void SalvarEdicaoPj(ActionEvent event) {

		PessoaJuridica pessoa = new PessoaJuridica();

		pessoa.setCNPJ(AtualizarCNPJ.getText());
		pessoa.setEmail(AtualizarEndereco.getText());
		pessoa.setNome(AtualizarNome.getText());
		pessoa.setCidade(AtualizarCidade.getText());
		pessoa.setTelefone(AtualizarNumero.getText());
		pessoa.setId(ID);

		if (Alerta.msgConfirmaEdicao(String.format("Deseja Editar os dados da %s ?", objetoSelecionado.getNome()))) {

			if (ValidarCampos.isCNPJ(pessoa.getCNPJ()) && ValidarCampos.validarTelefone(pessoa.getTelefone())) {
				if (conexao.Editar(pessoa)) {
					atualizarTabela();
					Alerta.mensagemInformacao("Cadastro Editado com sucesso");
				} else {
					Alerta.mensagemErro("Erro tentar salvar edicao");
				}
			} else {

				Alerta.mensagemErro("CNPJ ou telefone invalido");
			}
		}

	}

	@FXML
	void cadastrarNovoPj(ActionEvent event) {
		try {
			abrirFormulario("CadastroPessoaJuridica");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadatro pj");
		}

	}

	public void criarColunas() {

		TableColumn<PessoaJuridica, String> colunaNome = new TableColumn<>("Nome");
		TableColumn<PessoaJuridica, String> colunaCNPJ = new TableColumn<>("CNPJ");
		TableColumn<PessoaJuridica, String> colunaEmail = new TableColumn<>("Email");
		TableColumn<PessoaJuridica, String> colunaTelefone = new TableColumn<>("Telefone");
		TableColumn<PessoaJuridica, String> colunaCidade = new TableColumn<>("Cidade Residencial");

		TabelaPessoaJuridica.getColumns().addAll(colunaNome, colunaCNPJ, colunaEmail, colunaCidade, colunaTelefone);

		TabelaPessoaJuridica.setColumnResizePolicy(TabelaPessoaJuridica.CONSTRAINED_RESIZE_POLICY);

		colunaNome.setCellValueFactory(new PropertyValueFactory("Nome"));
		colunaCNPJ.setCellValueFactory(new PropertyValueFactory("CNPJ"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
		colunaCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory("Telefone"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarNome.getText());

		for (PessoaJuridica t : lista) {
			ob.add(t);
		}
		TabelaPessoaJuridica.getItems().setAll(ob);
		TabelaPessoaJuridica.getSelectionModel().selectFirst();

	}

	public void setCampoFormulario() {

		objetoSelecionado = TabelaPessoaJuridica.getItems()
				.get(TabelaPessoaJuridica.getSelectionModel().getSelectedIndex());
		AtualizarNome.setText(objetoSelecionado.getNome());
		AtualizarCNPJ.setText(objetoSelecionado.getCNPJ());
		AtualizarEndereco.setText(objetoSelecionado.getEmail());
		AtualizarCidade.setText(objetoSelecionado.getCidade());
		AtualizarNumero.setText(String.valueOf(objetoSelecionado.getTelefone()));
		ID = objetoSelecionado.getId();

	}

	@FXML
	void clikarNaTabela(MouseEvent event) {

		setCampoFormulario();
	}

	@FXML
	void filtrarRegistro(KeyEvent event) {
		atualizarTabela();

	}

	@FXML
	void moverTabela(KeyEvent event) {
		setCampoFormulario();

	}

	public void abrirFormulario(String formulario) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/" + formulario + ".fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

	}

}
