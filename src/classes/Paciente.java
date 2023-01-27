package classes;

public class Paciente {
	private String nomeCompleto;
	private int idade;
	private String Cpf ;
	private int identificador;
	
	
	@Override
	public String toString() {
		return "Paciente [ nomeCompleto=" + nomeCompleto + ", idade=" + idade + "]";
	}
	
	public Paciente(String nomeCompleto, int idade) {
		super();
		this.nomeCompleto = nomeCompleto;
		this.idade = idade;
	}
	
	public Paciente() {
		
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cpf) {
		Cpf = cpf;
	}

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}
	
	

}
