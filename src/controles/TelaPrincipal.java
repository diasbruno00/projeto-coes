package controles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipal implements Initializable {
	

	@FXML
	private MenuItem IdPesquisarCliente;

    @FXML
    private Menu IdOutrasFuncionalidades;

	@FXML
	private MenuItem IdPesquisarCordenador;

	@FXML
	private MenuItem IdPesquisarPessoaJuridica;

	@FXML
	private MenuItem IdPesquisarEntrevista;

	@FXML
	private MenuItem IdPesquisarEntrevistador;

	@FXML
	private MenuItem IdPesquisarPaciente;

	@FXML
	private MenuItem IdPesquisarResponsavel;
	

    @FXML
    private MenuItem IdPesquisarEntrevistado;


	@FXML
	private Menu IdClientes;

	@FXML
	private Menu IdEntrevista;

	@FXML
	private Menu IdEntrevistador;

	@FXML
	private Menu IdPacientes;

	@FXML
	void AcaoPesquisarEntrevista(ActionEvent event) {

		try {
			abrirFormulario("PesquisarEntrevista");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao abrir tela pesquisar entrevista");
		}

	}

	@FXML
	void AcaoPesquisarPaciente(ActionEvent event) {

		try {
			abrirFormulario("PesquisarPacientes");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro pesquisar Paciente");
		}

	}

	@FXML
	void AcaoPesquisarCliente(ActionEvent event) {

		try {
			abrirFormulario("PesquisarCliente");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro pesquisar cliente");
		}

	}

	@FXML
	void AcaoPesquisarEntrevistador(ActionEvent event) {
		try {
			abrirFormulario("PesquisarEntrevistador");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela Principal PesquisarEntrevistador ");
		}

	}

	@FXML
	void AcaoPesquisarPessoaJuridica(ActionEvent event) {

		try {
			abrirFormulario("PesquisarPessoaJuridica");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela pessoa juridica princial");
		}

	}

	@FXML
	void AcaoPesquisarResponsavel(ActionEvent event) {
		try {
			abrirFormulario("PesquisarResponsavel");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela principal responsavel");
		}

	}

	@FXML
	void AcaoPesquisarCordenador(ActionEvent event) {
		try {
			abrirFormulario("PesquisarCordenador");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abri tela principal pesquisar cordenador");
		}

	}
	@FXML
    void AcaoPesquisarEntrevistado(ActionEvent event) {
		try {
			abrirFormulario("PesquisarEntrevistado");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao abrir tela pesquisar entrevistado");
		}
		

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		IdPacientes.setGraphic(new ImageView("/icons/NovoCliente.png"));

		IdClientes.setGraphic(new ImageView("/icons/NovoCliente.png"));

		IdEntrevista.setGraphic(new ImageView("/icons/NovaAgendar.png"));

		IdEntrevistador.setGraphic(new ImageView("/icons/NovaEntrevis.png"));

		IdPesquisarCliente.setGraphic(new ImageView("/icons/pesquisar.png"));

		IdPesquisarEntrevista.setGraphic(new ImageView("/icons/pesquisar.png"));
		IdPesquisarEntrevistador.setGraphic(new ImageView("/icons/pesquisar.png"));

		IdPesquisarPaciente.setGraphic(new ImageView("/icons/pesquisar.png"));

		IdPesquisarPessoaJuridica.setGraphic(new ImageView("/icons/pesquisar.png"));
		IdPesquisarCordenador.setGraphic(new ImageView("/icons/pesquisar.png"));
		IdPesquisarResponsavel.setGraphic(new ImageView("/icons/pesquisar.png"));
		IdPesquisarEntrevistado.setGraphic(new ImageView("/icons/pesquisar.png"));
		IdOutrasFuncionalidades.setGraphic(new ImageView("/icons/MenuAzul2.png"));
		
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
