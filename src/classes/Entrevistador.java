package classes;

public class Entrevistador {

	private String nome;
	private int ID;
	private int IdProfissional;
	private String categoria;
	
	
	
	public Entrevistador(String nome, int iD, int idProfissional, String categoria) {
		super();
		this.nome = nome;
		ID = iD;
		IdProfissional = idProfissional;
		this.categoria = categoria;
	}
	
	public Entrevistador() {
		
	}
	@Override
	public String toString() {
		return "Entrevistador [nome=" + nome + ", ID=" + ID + ", IdProfissional=" + IdProfissional + ", categoria="
				+ categoria + "]";
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getIdProfissional() {
		return IdProfissional;
	}
	public void setIdProfissional(int idProfissional) {
		IdProfissional = idProfissional;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
}
