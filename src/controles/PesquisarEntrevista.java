package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Banco.EntrevistaDAO;
import classes.Entrevista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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

public class PesquisarEntrevista implements Initializable {

	@FXML
	private Button IconEditarEntrevista = new Button();

	@FXML
	private Button IconExcluirEntrevista = new Button();

	@FXML
	private Button IconNovaEntrevista = new Button();

	@FXML
	private Button IconTabelaEntrevista = new Button();

	@FXML
	private DatePicker AtualizarData;

	@FXML
	private TextField AtualizarHora;

	@FXML
	private TextField Txthora;

	@FXML
	private TextField DATA = new TextField();

	@FXML
	private DatePicker datapicker;

	@FXML
	private TextField PesquisarEntrevista = new TextField();

	@FXML
	private TableView<Entrevista> TabelaEntrevista = new TableView<Entrevista>();

	private List<Entrevista> lista;

	private ObservableList<Entrevista> ob = FXCollections.observableArrayList();

	private Entrevista objetoSelecionado = new Entrevista();

	EntrevistaDAO conexao = new EntrevistaDAO();

	int ID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		criarColunas();
		atualizarTabela();
		IconTabelaEntrevista.setGraphic(new ImageView("/icons/atualizar.png"));
		IconEditarEntrevista.setGraphic(new ImageView("/icons/editar.png"));
		IconExcluirEntrevista.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		IconNovaEntrevista.setGraphic(new ImageView("/icons/AcaoSalvar.png"));

	}

	@FXML
	void SalvarEntrevista(ActionEvent event) throws SQLDataException {

		datapicker.getValue();

		try {

			Date dataconvertida;
			dataconvertida = asDate(datapicker.getValue());
			java.sql.Date sqldate = converterData(dataconvertida);

			Entrevista entrevista = new Entrevista();

			entrevista.setHorarioEntrevista(Txthora.getText());
			entrevista.setDataEntrevista(sqldate);
			System.out.println(Txthora.getText());

			if (conexao.salvar(entrevista)) {
				atualizarTabela();
				limparDados(event);
				Alerta.mensagemInformacao("Nova Entrevista salva no banco de dados ");
				;
			} else {
				Alerta.mensagemErro("Nao foi possivel salvar ocorreu um erro");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setCampoFormulario() {

		objetoSelecionado = TabelaEntrevista.getItems().get(TabelaEntrevista.getSelectionModel().getSelectedIndex());

		AtualizarHora.setText(objetoSelecionado.getHorarioEntrevista());
		ID = objetoSelecionado.getIdEntrevista();

	}

	@FXML
	void AtualizarTabela(ActionEvent event) {
		atualizarTabela();

	}

	public void criarColunas() {

		TableColumn<Entrevista, Date> colunaData = new TableColumn<>("data");
		TableColumn<Entrevista, String> colunaHora = new TableColumn<>("hora");
		TableColumn<Entrevista, Integer> colunaId = new TableColumn<>("ID Entrevista");

		TabelaEntrevista.getColumns().addAll(colunaData, colunaHora, colunaId);

		TabelaEntrevista.setColumnResizePolicy(TabelaEntrevista.CONSTRAINED_RESIZE_POLICY);

		colunaData.setCellValueFactory(new PropertyValueFactory("dataEntrevista"));
		colunaHora.setCellValueFactory(new PropertyValueFactory("horarioEntrevista"));
		colunaId.setCellValueFactory(new PropertyValueFactory("IdEntrevista"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarEntrevista.getText());

		for (Entrevista t : lista) {
			ob.add(t);
		}
		TabelaEntrevista.getItems().setAll(ob);
		TabelaEntrevista.getSelectionModel().selectFirst();

	}

	@FXML
	void CadastrarNovaEntrevista(ActionEvent event) {
		try {
			abrirFormulario("CadastroEntrevista");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadastroEntrevista");
		}

	}

	@FXML
	void ExcluirEntrevista(ActionEvent event) {

		if (Alerta.msgConfirmaExclusao(String.format("Dejesa realmente excluir "))) {
			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Excluido com sucesso");
			} else {
				Alerta.mensagemErro("Erro ao excluir");
			}
		}

	}

	@FXML
	void SalvarEdicaoEntrevista(ActionEvent event) {

		Date dataconvertida;
		dataconvertida = asDate(AtualizarData.getValue());
		java.sql.Date sqldate = converterData(dataconvertida);

		Entrevista entrevista = new Entrevista();
		entrevista.setDataEntrevista(sqldate);
		entrevista.setHorarioEntrevista(AtualizarHora.getText());
		entrevista.setIdEntrevista(ID);

		if (Alerta.msgConfirmaEdicao(String.format("Deseja Editar os dados da Entrevista ?"))) {
			if (conexao.Editar(entrevista)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Entrevista alterada com sucesso");
			}
		} else {
			Alerta.mensagemErro("Erro ao tentar editar dados");
		}

	}

	@FXML
	void MoverTabela(KeyEvent event) {
		setCampoFormulario();
	}

	@FXML
	void clikarNatabela(MouseEvent event) {
		setCampoFormulario();
	}

	@FXML
	void filtrarRegistro(KeyEvent event) {
		atualizarTabela();

	}

	@FXML
	void limparDados(ActionEvent event) {
		DATA.clear();
		Txthora.clear();

	}

	public void abrirFormulario(String formulario) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/" + formulario + ".fxml"));
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

	}

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private static java.sql.Date converterData(java.util.Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

}
