package controles;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Banco.PacienteDAO;
import classes.Paciente;
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

public class PesquisarPacientes implements Initializable {
	
	@FXML
    private Button IconAtualizarTabela = new Button();

    @FXML
    private Button IconEditar = new Button();

    @FXML
    private Button IconExcluir = new Button();

    @FXML
    private Button IconNovo = new Button();


	@FXML
	private TableView<Paciente> TabelaPaciente = new TableView<Paciente>();

	@FXML
	private TextField TxtIdadeX;

	@FXML
	private TextField TxtNomeX;

	@FXML
	private TextField TxtCPFx;

	@FXML
	private TextField TxtPesquisarNome = new TextField();

	@FXML
	private TextField TxtIdade;

	@FXML
	private TextField txtNomeCompleto;

	@FXML
	private TextField TxtCpf;

	private List<Paciente> lista;

	private ObservableList<Paciente> ob = FXCollections.observableArrayList();

	private Paciente objetoSelecionado = new Paciente();

	PacienteDAO conexao = new PacienteDAO();

	String cpf;

	int idUnico;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		criarColunas();
		atualizarTabela();
		
		 IconAtualizarTabela.setGraphic(new ImageView("/icons/atualizar.png"));
         IconEditar.setGraphic(new ImageView("/icons/editar.png"));

	     IconExcluir.setGraphic(new ImageView("/icons/AcaoExcluir.png"));

	    IconNovo.setGraphic(new ImageView("/icons/AcaoSalvar.png"));


		
	}

	@FXML
	void CadastrarNovoPaciten(ActionEvent event) {
		try {
			abrirFormulario("CadastroPaciente");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela cadastro paciente");
		}

	}

	@FXML
	void SalvarPaciente(ActionEvent event) {

		Paciente paciente = new Paciente();
		int idade = Integer.parseInt(TxtIdade.getText());
		paciente.setIdade(idade);
		paciente.setNomeCompleto(txtNomeCompleto.getText());

		cpf = TxtCpf.getText();

		if (ValidarCampos.validarCpf(cpf)) {
			paciente.setCpf(cpf);
			if (conexao.salvar(paciente)) {
				atualizarTabela();
				limparDados(event);
				Alerta.mensagemInformacao(
						String.format("%s salvo com sucesso no banco de dados", paciente.getNomeCompleto()));
			} else {
				Alerta.mensagemErro(
						String.format("Ocoreu um erro %s nao foi possivel salvar", paciente.getNomeCompleto()));
			}
		} else {
			Alerta.mensagemErro("CPF invalido");
		}
	}

	@FXML
	void EditarPacientes(ActionEvent event) {

		Paciente paciente = new Paciente();
		int idade = Integer.parseInt(TxtIdadeX.getText());
		String nome = TxtNomeX.getText();
		String cpf = TxtCPFx.getText();
		paciente.setIdade(idade);
		paciente.setNomeCompleto(nome);
		paciente.setCpf(cpf);
		paciente.setIdentificador(idUnico);

		if (Alerta.msgConfirmaEdicao(
				String.format("Deseja Editar os dados da %s ?", objetoSelecionado.getNomeCompleto()))) {
			if (conexao.Editar(paciente)) {
				atualizarTabela();
				Alerta.mensagemInformacao("Cadastro Editado com sucesso");
			} else {
				Alerta.mensagemErro("Erro ao salvar dados");
			}
		}

	}

	@FXML
	void ExcluirPacientes(ActionEvent event) {

		if (Alerta.msgConfirmaExclusao(String.format("Dejesa realmente excluir %s do banco de dados ? ",
				objetoSelecionado.getNomeCompleto()))) {
			if (conexao.remover(objetoSelecionado)) {
				atualizarTabela();
				Alerta.mensagemInformacao(
						String.format("%s Excluido com sucesso", objetoSelecionado.getNomeCompleto()));
			} else {
				Alerta.mensagemErro("Erro ao excluir ");
			}
		}

	}

	public void criarColunas() {

		TableColumn<Paciente, String> colunaCPF = new TableColumn<>("CPF Paciente");
		TableColumn<Paciente, String> colunaNome = new TableColumn<>("Nome Completo");
		TableColumn<Paciente, Integer> colunaIdade = new TableColumn<>("Idade");

		TabelaPaciente.getColumns().addAll(colunaCPF, colunaNome, colunaIdade);

		TabelaPaciente.setColumnResizePolicy(TabelaPaciente.CONSTRAINED_RESIZE_POLICY);

		colunaNome.setCellValueFactory(new PropertyValueFactory("nomeCompleto"));
		colunaIdade.setCellValueFactory(new PropertyValueFactory("idade"));
		colunaCPF.setCellValueFactory(new PropertyValueFactory("cpf"));

	}

	public void atualizarTabela() {

		ob.clear();

		lista = conexao.consultar(TxtPesquisarNome.getText());

		for (Paciente t : lista) {
			ob.add(t);
		}
		TabelaPaciente.getItems().setAll(ob);
		TabelaPaciente.getSelectionModel().selectFirst();

	}

	public void setCampoFormulario() {

		objetoSelecionado = TabelaPaciente.getItems().get(TabelaPaciente.getSelectionModel().getSelectedIndex());

		TxtNomeX.setText(objetoSelecionado.getNomeCompleto());

		TxtIdadeX.setText(String.valueOf(objetoSelecionado.getIdade()));

		idUnico = objetoSelecionado.getIdentificador();
		TxtCPFx.setText(objetoSelecionado.getCpf());

	}

	@FXML
	void clikarNaTabela(MouseEvent event) {

		setCampoFormulario();

	}

	@FXML
	void moverTabela(KeyEvent event) {
		setCampoFormulario();

	}

	@FXML
	void filtrarRegistro(KeyEvent event) {
		atualizarTabela();
	}

	@FXML
	void AtaualizarTabela(ActionEvent event) {

		atualizarTabela();
	}

	@FXML
	void limparDados(ActionEvent event) {

		txtNomeCompleto.clear();
		TxtIdade.clear();
	    TxtCpf.clear();

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
