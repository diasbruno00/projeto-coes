package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerta {
	
	static ButtonType btnConfirmar = new ButtonType("Confirmar");
	static ButtonType btnCancelar = new ButtonType("Cancelar");
	static boolean resposta ;
	
	public  static void mensagemInformacao(String mensagem) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION );
		alert.setHeaderText("Informacao do cadastro");
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
	
	public  static void mensagemErro(String mensagem) {
		Alert alert = new Alert(Alert.AlertType.ERROR );
		alert.setHeaderText("Informacao do cadastro");
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
	
	public static boolean msgConfirmaExclusao(String mensagem) {
		Alert  alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Exclusao de cadastro");
		alert.setContentText(mensagem);
		alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);
		alert.showAndWait().ifPresent(b -> {
			if(b == btnConfirmar) {
				resposta = true ;
			}else {
				resposta = false ;
			}
			
		});
		return resposta ;
		
	}
	public static boolean msgConfirmaEdicao (String mensagem) {
		Alert  alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Edicao de cadastro");
		alert.setContentText(mensagem);
		alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);
		alert.showAndWait().ifPresent(b -> {
			if(b == btnConfirmar) {
				resposta = true ;
			}else {
				resposta = false ;
			}
			
		});
		return resposta ;
		
	}


}
