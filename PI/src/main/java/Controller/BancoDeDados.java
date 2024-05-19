package Controller;

import Model.Beans.Usuario;
import view.Formulario;
import view.Login;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BancoDeDados {

    static ConexaoMySQL conexao = new ConexaoMySQL();
    static Connection cn = null;
    static PreparedStatement ps = null;

    public static void main(String[] args) {
        try {
            String operacao[] = {"Cadastro de Usuário", "Login"};
            Object escolha = JOptionPane.showInputDialog(null, "O que deseja realizar?", "", JOptionPane.QUESTION_MESSAGE, null, operacao, operacao[0]);
            realizaOperacao(escolha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void realizaOperacao(Object escolha) throws Exception {
        if (escolha.toString().equals("Cadastro de Usuário")) {
            Formulario formulario = new Formulario();
            formulario.setLocationRelativeTo(null);
            formulario.setVisible(true);
        } else if (escolha.toString().equals("Login")) {
            Login login = new Login();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        }
    }

    public static void cadastrarUsuario(Usuario usuario) throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "INSERT INTO senac.usuarios (Nome, Email, Senha, Telefone, Organizador) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getTelefone());
            ps.setBoolean(5, usuario.isOrganizador());
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static int verificarLogin(String email, String senha) throws SQLException {
        ResultSet rs = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "SELECT id FROM senac.usuarios WHERE Email = ? AND Senha = ?");
            ps.setString(1, email);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("id");
                // Após o login bem-sucedido, inserir o usuário na tabela Login
                inserirUsuarioLogado(userID);
                return userID; // Retorna o ID do usuário se o login for bem-sucedido
            } else {
                return -1; // Retorna -1 se o login falhar
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void inserirUsuarioLogado(int userID) throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("INSERT INTO Login (UserID) VALUES (?)");
            ps.setInt(1, userID);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static boolean verificarUsuarioLogado(int userID) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = cn.prepareStatement("SELECT * FROM Login WHERE UserID = ?");
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static void removerUsuarioLogado() throws SQLException {
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement("DELETE FROM Login");
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public static Usuario recuperarUsuario(String email) throws SQLException {
        Usuario usuario = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "SELECT * FROM senac.usuarios WHERE Email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Extrair informações do ResultSet e criar um objeto Usuario
                String nome = rs.getString("Nome");
                String senha = rs.getString("Senha");
                String telefone = rs.getString("Telefone");
                boolean organizador = rs.getBoolean("Organizador");

                // Criar o objeto Usuario
                usuario = new Usuario(nome, senha, email, telefone, organizador);
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return usuario;
    }

}
