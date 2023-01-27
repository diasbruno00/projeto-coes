package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.EntrevistadorDAO;
import classes.Entrevistador;
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

public class PesquisarEntrevistador implements Initializable {

	@FXML
	private Button IconEntrevistadoresEditar = new Button();

	@FXML
	private Button IconEntrevistadoresExcluir = new Button();

	@FXML
	private Button IconEntrevistadoresNovo = new Button();

	@FXML
	private Button IconEntrevistadoresTabela = new Button();

	@FXML
	private TextField AtualizarIdProfissional;

	@FXML
	private TextField AtualizarCategoria;

	@FXML
	private TextField AtualizarNome;

	@FXML
	private TextField PesquisarEntrevistador = new TextField();

	@FXML
	private TableView<Entrevistador> TabelaEntrevistador = new TableView<Entrevistador>();

	@FXML
	private TextField TxtICategoriaEntrevistador;

	@FXML
	private TextField TxtIdProfissionalEntrevistador;

	@FXML
	private TextField TxtNomeEntrevistador;

	EntrevistadorDAO conexao = new EntrevistadorDAO();

	private List<Entrevistador> lista;

	private ObservableList<Entrevistador> ob = FXCollections.observableArrayList();

	private Entrevistador objetoSelecionado = new Entrevistador();

	int ID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		criarColunas();
		atualizarTabela();
		
		IconEntrevistadoresTabela.setGraphic(new ImageView("/icons/atualizar.png"));
		IconEntrevistadoresEditar.setGraphic(new ImageView("/icons/editar.png"));
		IconEntrevistadoresExcluir.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		IconEntrevistadoresNovo.setGraphic(new ImageView("/icons/AcaoSalvar.png"));

	}

	public void setCampoFormulario() {

		objetoSelecionado = TabelaEntrevistador.getItems()
				.get(TabelaEntrevistador.getSelectionModel().getSelectedIndex());

		AtualizarNome.setText(objetoSelecionado.getNome());
		AtualizarIdProfissional.setText(String.valueOf(objetoSelecionado.getIdProfissional()));
		AtualizarCategoria.setText(String.valueOf(objetoSelecionado.getCategoria()));
		ID = objetoSelecionado.getID();

	}

	public void criarColunas() {

		TableColumn<Entrevistador, String> colunaNome = new TableColumn<>("Nome");
		TableColumn<Entrevistador, String> colunaCategoria = new TableColumn<>("Categoria");
		TableColumn<Entrevistador, Integer> colunaIdProfissional = new TableColumn<>("IdProfissional");
		TableColumn<Entrevistador, Integer> colunaId = new TableColumn<>("ID");

		TabelaEntrevistador.getColumns().addAll(colunaNome, colunaCategoria, colunaIdProfissional, colunaId);

		TabelaEntrevistador.setColumnResizePolicy(TabelaEntrevistador.CONSTRAINED_RESIZE_POLICY);

		colunaNome.setCellValueFactory(new PropertyValueFactory("nome"));
		colunaCategoria.setCellValueFactory(new PropertyValueFactory("categoria"));
		colunaIdProfissional.setCellValueFactory(new PropertyValueFactory("IdProfissional"));
		colunaId.setCellValueFactory(new PropertyValueFactory("ID"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarEntrevistador.getText());

		for (Entrevistador t : lista) {
			ob.add(t);
		}
		TabelaEntrevistador.getItems().setAll(ob);
		TabelaEntrevistador.getSelectionModel().selectFirst();

	}

	@FXML
	void LimparDadosEntrevistador(ActionEvent event) {

		TxtICategoriaEntrevistador.clear();
		TxtIdProfissionalEntrevistador.clear();
		TxtNomeEntrevistador.clear();

	}

	@FXML
	void SalvarNovoEntrevistador(ActionEvent event) {
		Entrevistador entrevistador = new Entrevistador();
		entrevistador.setNome(TxtNomeEntrevistador.getText());
		entrevistador.setCategoria(TxtICategoriaEntrevistador.getText());

		int idProfissional = Integer.parseInt(TxtIdProfissionalEntrevistador.getText());

		entrevistador.setIdProfissional(idProfissional);

		if ((boolean) conexao.salvar(entrevistador)) {
			LimparDadosEntrevistador(event);
			Alerta.mensagemInformacao(String.format("%s salvo com sucesso no banco de dados", entrevistador.getNome()));
		} else {
			Alerta.mensagemErro(String.format("Ocoreu um erro %s nao foi possivel salvar", entrevistador.getNome()));
		}
	}

	@FXML
	void CadastrarNovoEntrevistador(ActionEvent event) {
		try {
			abrirFormulario("CadastroEntrevistador");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadastro Entrevistador");
		}

	}

	@FXML
	void ExcluirEntrevistador(ActionEvent event) {
		if (Alerta.msgConfirmaExclusao(
				String.format("Dejesa realmente excluir %s do banco de dados ? ", objetoSelecionado.getNome()))) {
			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				Alerta.mensagemInformacao(String.format("%s excluido com sucesso", objetoSelecionado.getNome()));
			} else {
				Alerta.mensagemErro("Erro ao excluir");
			}
		}
	}

	@FXML
	void SalvarEdicaoEntrevistador(ActionEvent event) {

		Entrevistador entrevistador = new Entrevistador();
		entrevistador.setCategoria(AtualizarCategoria.getText());
		int IdProfissional = Integer.parseInt(AtualizarIdProfissional.getText());
		entrevistador.setIdProfissional(IdProfissional);
		entrevistador.setNome(AtualizarNome.getText());
		entrevistador.setID(ID);

		if (Alerta.msgConfirmaEdicao(String.format("Deseja Editar os dados da %s ?", objetoSelecionado.getNome()))) {
			if (conexao.Editar(entrevistador)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Cadastro Editado com sucesso");
			} else {
				Alerta.mensagemErro("Erro ao excluir");
			}
		}
	}

	@FXML
	void AtualizarTabela(ActionEvent event) {

		atualizarTabela();

	}

	@FXML
	void clicarNaTabela(MouseEvent event) {
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
