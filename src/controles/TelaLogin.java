package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Banco.LoginDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Alerta;

public class TelaLogin implements Initializable {
	



    
	@FXML
	private PasswordField TrSenha;

	@FXML
	private TextField TrUsuario;

	@FXML
	private TextField Usuario;

	@FXML
	private PasswordField senha;

	LoginDAO conexao = new LoginDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	
    
    @FXML
    void LimparDadosTrocarSenha(ActionEvent event) {
    	TrUsuario.clear();
    	TrSenha.clear();
    	TrUsuario.requestFocus();

    }


	@FXML
	void Login(ActionEvent event) {

		try {

			classes.Login lg = new classes.Login();
			lg.setUsuario(Usuario.getText());
			lg.setSenha(senha.getText());
			ResultSet autenticar = conexao.consultar(lg);

			if (autenticar.next()) {
				try {
					abrirFormulario("TelaPrincipal");
				} catch (IOException e) {
					e.printStackTrace();
					Alerta.mensagemErro(String.format("Ocorreu um erro %s", e));
				}
			} else {
				Alerta.mensagemErro("Senha ou usuario invalido");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.mensagemErro(e.getMessage());
		}
	}

	@FXML
	void TrocarSenha(ActionEvent event) {
		try {
			abrirFormulario("TrocarSenha");
		} catch (IOException e) {
			System.out.println("Erro ao abrir trocar senha");
		}

	}

	@FXML
	void SalvarAlteracoes(ActionEvent event) {
		classes.Login lg = new classes.Login();
		lg.setUsuario(TrUsuario.getText());
		lg.setSenha(TrSenha.getText());

		if (conexao.Editar(lg)) {
			LimparDadosTrocarSenha(event);
			Alerta.mensagemInformacao("Senha alterada com sucesso");
		} else {
			Alerta.mensagemErro("Nao foi possivel altera a senha");
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
