package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.ResponsavelDAO;
import classes.Responsavel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Alerta;

public class PesquisarResponsavel implements Initializable {
	
	@FXML
    private Button IconEditarResponsavel = new Button();

    @FXML
    private Button IconExcluirResponsavel = new Button();

    @FXML
    private Button IconNovoResponsavel = new Button();

    @FXML
    private Button IconTabelaResponsavel = new Button();
	
	@FXML
	private RadioButton IdRadioPF;

	@FXML
	private RadioButton IdRadioPj;

	@FXML
	private TextField AtualiazarCnpjPessoaJuridica;

	@FXML
	private TextField AtualiazarCpfPaciente;

	@FXML
	private TextField AtualiazarCpfPessoaFisica;

	@FXML
	private RadioButton RabioButtonPF;

	@FXML
	private RadioButton RabioButtonPj;

	@FXML
	private TextField idGeral = new TextField();

	@FXML
	private TextField idPaciente = new TextField();

	@FXML
	private TextField PesquisarPaciente = new TextField();

	@FXML
	private TableView<Responsavel> TabelaResponsavel = new TableView<Responsavel>();

	private List<Responsavel> lista;

	private ObservableList<Responsavel> ob = FXCollections.observableArrayList();

	private Responsavel objetoSelecionado = new Responsavel();

	ResponsavelDAO conexao = new ResponsavelDAO();

	@FXML
	private ToggleGroup grupo;

	@FXML
	private ToggleGroup grupo2;

	int ID;

	@FXML
	void AcaoLimparDados(ActionEvent event) {

		idPaciente.clear();
		idGeral.clear();

	}

	@FXML
	void AcaoAtualiazarTabela(ActionEvent event) {

		atualizarTabela();

	}

	@FXML
	void AcaoSalvarResponsavel(ActionEvent event) throws NumberFormatException {

		Responsavel responsavel = new Responsavel();

		RadioButton radio = (RadioButton) grupo.getSelectedToggle();

		if (radio == RabioButtonPF) {

			responsavel.setCpfPaciente(idPaciente.getText());
			responsavel.setIdentificador(idGeral.getText());

			System.out.println(idGeral.getText());

			if (conexao.salvarPF(responsavel)) {
				AcaoLimparDados(event);
				Alerta.mensagemInformacao(String.format("%s salvo com sucesso", responsavel.getCpfPaciente()));
			} else {
				Alerta.mensagemErro("Erro ao salvar os dadoas");
			}

		} else if (radio == RabioButtonPj) {

			responsavel.setCpfPaciente(idPaciente.getText());
			responsavel.setIdentificador(idGeral.getText());
			System.out.println(idGeral.getText());

			if (conexao.salvarPJ(responsavel)) {
				AcaoLimparDados(event);
				Alerta.mensagemInformacao(String.format("%s salvo com sucesso", responsavel.getCpfPaciente()));
			} else {
				Alerta.mensagemErro("Erro ao salvar os dadoas");
			}

		}

	}

	@FXML
	void AcaoCadastrarNovo(ActionEvent event) {

		try {
			abrirFormulario("CadastroResponsavel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadastro responsavel");
		}

	}

	@FXML
	void AcaoEditarResponsavel(ActionEvent event) {

		RadioButton radio = (RadioButton) grupo2.getSelectedToggle();

		if (radio == IdRadioPF) {
			Responsavel responsavel = new Responsavel();
			responsavel.setCpfPaciente(AtualiazarCpfPaciente.getText());
			;
			responsavel.setIdentificador(AtualiazarCpfPessoaFisica.getText());
			responsavel.setID(ID);
			if (Alerta.msgConfirmaEdicao("Deseja realmente editar ?")) {

				if (conexao.EditarPF(responsavel)) {
					atualizarTabela();
					Alerta.mensagemInformacao("Atualizado com sucesso");
				} else {
					Alerta.mensagemErro("Erro ao atualizar favor conferir os dados inseridos");
				}
			}
		} else if (radio == IdRadioPj) {

			Responsavel responsavel = new Responsavel();
			responsavel.setCpfPaciente(AtualiazarCpfPaciente.getText());
			;
			responsavel.setPJCnpj(AtualiazarCnpjPessoaJuridica.getText());
			responsavel.setID(ID);
			if (Alerta.msgConfirmaEdicao("Deseja realmente editar ?")) {

				if (conexao.EditarPj(responsavel)) {
					atualizarTabela();
					Alerta.mensagemInformacao("Atualizado com sucesso");
				} else {
					Alerta.mensagemErro("Erro ao atualizar favor conferir os dados inseridos");
				}
			}
		}

	}

	@FXML
	void AcaoExcluirResponsavel(ActionEvent event) {
		if (Alerta.msgConfirmaExclusao("Deseja realmente excluir ?")) {
			if(conexao.remover(objetoSelecionado)) {
			atualizarTabela();
			Alerta.mensagemInformacao("Removido com sucesso");
			}else {
				Alerta.mensagemErro("Erro ao remover");
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		criarColunas();
		atualizarTabela();
      
		IconTabelaResponsavel.setGraphic(new ImageView("/icons/atualizar.png"));
		IconEditarResponsavel.setGraphic(new ImageView("/icons/editar.png"));
		IconExcluirResponsavel.setGraphic(new ImageView("/icons/AcaoExcluir.png"));
		IconNovoResponsavel.setGraphic(new ImageView("/icons/AcaoSalvar.png"));

		

	}

	public void criarColunas() {

		TableColumn<Responsavel, String> colunaCPF = new TableColumn<>(" Pessoa Fisica ");
		TableColumn<Responsavel, String> colunaCnpj = new TableColumn<>("Pessoa Juridica");
		TableColumn<Responsavel, String> colunaPacienteCPF = new TableColumn<>("CPF do paciente");

		TabelaResponsavel.getColumns().addAll(colunaCPF, colunaCnpj, colunaPacienteCPF);

		TabelaResponsavel.setColumnResizePolicy(TabelaResponsavel.CONSTRAINED_RESIZE_POLICY);

		colunaCPF.setCellValueFactory(new PropertyValueFactory("Identificador"));
		colunaCnpj.setCellValueFactory(new PropertyValueFactory("PJCnpj"));
		colunaPacienteCPF.setCellValueFactory(new PropertyValueFactory("CpfPaciente"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(PesquisarPaciente.getText());

		for (Responsavel t : lista) {
			ob.add(t);
		}
		TabelaResponsavel.getItems().setAll(ob);
		TabelaResponsavel.getSelectionModel().selectFirst();

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

	public void setCampoFormulario() {

		objetoSelecionado = TabelaResponsavel.getItems().get(TabelaResponsavel.getSelectionModel().getSelectedIndex());
		AtualiazarCpfPaciente.setText(objetoSelecionado.getCpfPaciente());
		AtualiazarCpfPessoaFisica.setText(objetoSelecionado.getIdentificador());
		AtualiazarCnpjPessoaJuridica.setText(objetoSelecionado.getPJCnpj());
		ID = objetoSelecionado.getID();

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
