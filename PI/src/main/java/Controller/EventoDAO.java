package Controller;

import Model.Beans.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventoDAO {
    private ConexaoMySQL conexao = new ConexaoMySQL();

    public void cadastrarEvento(Evento evento) throws SQLException {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = conexao.openDB();
            ps = cn.prepareStatement(
                    "INSERT INTO senac.eventos (Titulo, DataHora, Localizacao, Descricao, Capacidade, Ingressos, IdOrganizador) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, evento.getTitulo());
            ps.setString(2, evento.getDataHora());
            ps.setString(3, evento.getLocalizacao());
            ps.setString(4, evento.getDescricao());
            ps.setInt(5, evento.getCapacidade());
            ps.setDouble(6, evento.getValorIngressos());
            ps.setInt(7, evento.getIdOrganizador());
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
}
