package classes;

import java.util.Date;

public class Entrevista {
	
	private Date dataEntrevista;
    private String horarioEntrevista;
    private int IdEntrevista ;
    
	public int getIdEntrevista() {
		return IdEntrevista;
	}
	public void setIdEntrevista(int idEntrevista) {
		IdEntrevista = idEntrevista;
	}
	@Override
	public String toString() {
		return "Entrevista [dataEntrevista=" + dataEntrevista + ", horarioEntrevista=" + horarioEntrevista + "]";
	}
	
	public Date getDataEntrevista() {
		return dataEntrevista;
	}
	public void setDataEntrevista(Date dataEntrevista) {
		this.dataEntrevista = dataEntrevista;
	}
	public String getHorarioEntrevista() {
		return horarioEntrevista;
	}
	public void setHorarioEntrevista(String horarioEntrevista) {
		this.horarioEntrevista = horarioEntrevista;
	}
    
}
