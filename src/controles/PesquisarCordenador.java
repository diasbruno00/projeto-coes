package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.CordenadorDAO;
import classes.Coordena;
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

public class PesquisarCordenador implements Initializable {

	@FXML
	private Button IconCordenadorEditar = new Button();

	@FXML
	private Button IconCordenadorExcluir = new Button();

	@FXML
	private Button IconCordenadorNovo = new Button();

	@FXML
	private Button IconCordenadorTabela = new Button();

	@FXML
	private TextField AtualizarCpf;

	@FXML
	private TextField AtualizarID;

	@FXML
	private TextField PesquisarCPF = new TextField();

	@FXML
	private TextField CpfPaciente;

	@FXML
	private TextField idEntrevista;

	int ID;

	@FXML
	private TableView<Coordena> TabelaCordenador = new TableView<Coordena>();

	private List<Coordena> lista;

	private ObservableList<Coordena> ob = FXCollections.observableArrayList();

	private Coordena objetoSelecionado = new Coordena();

	CordenadorDAO conexao = new CordenadorDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		criarColunas();
		atualizarTabela();
		
		IconCordenadorTabela.setGraphic(new ImageView("/icons/atualizar.png"));
		IconCordenadorEditar.setGraphic(new ImageView("/icons/editar.png"));
		IconCordenadorExcluir.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		IconCordenadorNovo.setGraphic(new ImageView("/icons/AcaoSalvar.png"));


	}

	public void criarColunas() {

		TableColumn<Coordena, String> colunaCpfPaciente = new TableColumn<>("CPF Paciente");
		TableColumn<Coordena, Integer> colunaIdEntrevista = new TableColumn<>("Id da entrevista");

		TabelaCordenador.getColumns().addAll(colunaCpfPaciente, colunaIdEntrevista);

		TabelaCordenador.setColumnResizePolicy(TabelaCordenador.CONSTRAINED_RESIZE_POLICY);

		colunaCpfPaciente.setCellValueFactory(new PropertyValueFactory("CpfPaciente"));
		colunaIdEntrevista.setCellValueFactory(new PropertyValueFactory("idEntrevista"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarCPF.getText());

		for (Coordena t : lista) {
			ob.add(t);
		}
		TabelaCordenador.getItems().setAll(ob);
		TabelaCordenador.getSelectionModel().selectFirst();

	}

	@FXML
	void AcaoLimparDados(ActionEvent event) {
		idEntrevista.clear();
		CpfPaciente.clear();

	}

	@FXML
	void EditarCordenador(ActionEvent event) {
		Coordena coordena = new Coordena();
		coordena.setCpfPaciente(AtualizarCpf.getText());
		int id = Integer.parseInt(AtualizarID.getText());
		coordena.setIdEntrevista(id);
		coordena.setIdUnico(ID);

		if (Alerta.msgConfirmaEdicao("Deseja reamente salva alteracoes?")) {
			if (conexao.Editar(coordena)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Alterado com sucesso");
			}
		} else {
			Alerta.mensagemErro("Erro tentar edidar dados");
		}
	}

	@FXML
	void ExcluirCordenador(ActionEvent event) {
		if (Alerta.msgConfirmaExclusao("Deseja realmente remover ?")) {
			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Excluido com sucesso");
			}
		} else {
			Alerta.mensagemErro("Erro ao tentar excluir");
		}

	}

	@FXML
	void atualizarTabela(ActionEvent event) {
		atualizarTabela();

	}

	@FXML
	void AcaoSalvarCordenador(ActionEvent event) {
		Coordena cordenador = new Coordena();
		cordenador.setCpfPaciente(CpfPaciente.getText());
		int id = Integer.parseInt(idEntrevista.getText());
		cordenador.setIdEntrevista(id);

		if (conexao.salvar(cordenador)) {
			AcaoLimparDados(event);
			Alerta.mensagemInformacao("Salvo com sucesso");
		} else {
			Alerta.mensagemErro("Houve um erro que nao permitiu a gravacao dos dados");
		}

	}

	@FXML
	void AcaoNovoCordenador(ActionEvent event) {
		try {
			abrirFormulario("CadastroCordenador");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			System.out.println("Erro ao abrir cadastro cordenador");
		}

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

	public void setCampoFormulario() {

		objetoSelecionado = TabelaCordenador.getItems().get(TabelaCordenador.getSelectionModel().getSelectedIndex());
		AtualizarCpf.setText(objetoSelecionado.getCpfPaciente());
		String id = String.valueOf(objetoSelecionado.getIdEntrevista());
		AtualizarID.setText(id);

		ID = objetoSelecionado.getIdUnico();

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
