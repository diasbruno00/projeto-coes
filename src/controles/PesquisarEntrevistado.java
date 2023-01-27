package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.EntrevistadoDAO;
import classes.Entrevistado;
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

public class PesquisarEntrevistado implements Initializable {

	@FXML
    private Button IconEntrevistadosEditar = new Button();

    @FXML
    private Button IconEntrevistadosExcluir  = new Button();

    @FXML
    private Button IconEntrevistadosNovo = new Button();

    @FXML
    private Button IconEntrevistadosTabela = new Button();
	

	@FXML
	private TextField AtualiazarID;

	@FXML
	private TextField AtualizarCpf;

	@FXML
	private TextField PesquisarCpf = new TextField();

	@FXML
	private TableView<Entrevistado> TabelaEntrevistado = new TableView<Entrevistado>();

	@FXML
	private TextField txtCpf;

	@FXML
	private TextField txtId;

	private List<Entrevistado> lista;

	private ObservableList<Entrevistado> ob = FXCollections.observableArrayList();

	private Entrevistado objetoSelecionado = new Entrevistado();

	EntrevistadoDAO conexao = new EntrevistadoDAO();

	int id;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		criarColunas();
		atualizarTabela();
		
		IconEntrevistadosTabela.setGraphic(new ImageView("/icons/atualizar.png"));
		IconEntrevistadosEditar.setGraphic(new ImageView("/icons/editar.png"));
		IconEntrevistadosExcluir.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		IconEntrevistadosNovo.setGraphic(new ImageView("/icons/AcaoSalvar.png"));
		
	}

	@FXML
	void AcaoLimpar(ActionEvent event) {
		txtCpf.clear();
		txtId.clear();
		txtCpf.requestFocus();

	}

	@FXML
	void AcaoSalvar(ActionEvent event) {
		Entrevistado ent = new Entrevistado();
		ent.setCpfPaciente(txtCpf.getText());
		ent.setIdEntrevista(Integer.parseInt(txtId.getText()));

		if (conexao.salvar(ent)) {
			Alerta.mensagemInformacao("Salvo com sucesso");
			AcaoLimpar(event);
		} else {
			Alerta.mensagemErro("Erro ao salvar");
		}

	}

	@FXML
	void EditarEntrevistado(ActionEvent event) {
		Entrevistado ent = new Entrevistado();
		ent.setCpfPaciente(AtualizarCpf.getText());
		ent.setIdEntrevista(Integer.parseInt(AtualiazarID.getText()));
		ent.setId(id);

		if (Alerta.msgConfirmaEdicao("Deseja realmente editar os dados")) {
			if (conexao.Editar(ent)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Dados editados com suceso");
			} else {
				Alerta.mensagemErro("Erro ao editar os dados favor conferir os dados inseridos");
			}
		}

	}

	@FXML
	void ExcluirEntrevistado(ActionEvent event) {
		if (Alerta.msgConfirmaExclusao("Deseja realmente excluir ?")) {

			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Excluido com sucesso");
			} else {
				Alerta.mensagemErro("Erro ao excluir");
			}
		}

	}

	public void criarColunas() {

		TableColumn<Entrevistado, String> colunaCPF = new TableColumn<>("CPF do Paciente");
		TableColumn<Entrevistado, Integer> colunaId = new TableColumn<>("Id do Entrevistador");

		TabelaEntrevistado.getColumns().addAll(colunaCPF, colunaId);

		TabelaEntrevistado.setColumnResizePolicy(TabelaEntrevistado.CONSTRAINED_RESIZE_POLICY);

		colunaCPF.setCellValueFactory(new PropertyValueFactory("cpfPaciente"));
		colunaId.setCellValueFactory(new PropertyValueFactory("IdEntrevista"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarCpf.getText());

		for (Entrevistado t : lista) {
			ob.add(t);
		}
		TabelaEntrevistado.getItems().setAll(ob);
		TabelaEntrevistado.getSelectionModel().selectFirst();

	}

	public void setCampoFormulario() {

		objetoSelecionado = TabelaEntrevistado.getItems()
				.get(TabelaEntrevistado.getSelectionModel().getSelectedIndex());

		AtualizarCpf.setText(objetoSelecionado.getCpfPaciente());

		AtualiazarID.setText(String.valueOf(objetoSelecionado.getIdEntrevista()));

		id = objetoSelecionado.getId();

	}

	@FXML
	void clikarNaTabela(MouseEvent event) {
		setCampoFormulario();
	}

	@FXML
	void atualizarTabela(ActionEvent event) {
		atualizarTabela();

	}

	@FXML
	void filtrarRegistro(KeyEvent event) {
		atualizarTabela();
	}

	@FXML
	void moverTabela(KeyEvent event) {
		setCampoFormulario();

	}

	@FXML
	void CadastrarNovoEntrevistado(ActionEvent event) {

		try {
			abrirFormulario("CadastroEntrevistado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadastro entrevistado");
		}

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
