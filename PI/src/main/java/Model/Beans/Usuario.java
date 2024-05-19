package Model.Beans;

public class Usuario {
    private String nome;
    private String senha;
    private String email;
    private String telefone;
    private boolean organizador;

    // Construtor
    public Usuario(String nome, String senha, String email, String telefone, boolean organizador) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.organizador = organizador;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public Usuario() {
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isOrganizador() {
        return organizador;
    }

    public void setOrganizador(boolean organizador) {
        this.organizador = organizador;
    }
}
