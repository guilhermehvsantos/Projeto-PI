package view;

import Model.Beans.Evento;
import Model.DAO.EventoDAO;

import javax.swing.*;
import javax.swing.text.MaskFormatter; // Importar a classe MaskFormatter
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormularioEvento extends JFrame {
    private JTextField textFieldTitulo;
    private JFormattedTextField formattedTextFieldDataHora; // Alterado para JFormattedTextField
    private JTextField textFieldLocalizacao;
    private JTextArea textAreaDescricao;
    private JTextField textFieldCapacidade;
    private JTextField textFieldValorIngresso;
    private int userID; // Alterando o nome do atributo para userID

    public FormularioEvento(int userID) {
        this.userID = userID;

        setTitle("Formulário de Evento");
        setSize(270, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel labelTitulo = new JLabel("Título do Evento:");
        textFieldTitulo = new JTextField(20);
        JLabel labelDataHora = new JLabel("Data e Hora (yyyy-MM-dd HH:mm):");
        try {
            MaskFormatter formatter = new MaskFormatter("####-##-## ##:##"); // Definir a máscara
            formattedTextFieldDataHora = new JFormattedTextField(formatter); // Usar JFormattedTextField
            formattedTextFieldDataHora.setColumns(20);
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        JLabel labelLocalizacao = new JLabel("Localização:");
        textFieldLocalizacao = new JTextField(20);
        JLabel labelDescricao = new JLabel("Descrição do Evento:");
        textAreaDescricao = new JTextArea(5, 20);
        textAreaDescricao.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textAreaDescricao);
        JLabel labelCapacidade = new JLabel("Capacidade:");
        textFieldCapacidade = new JTextField(15);
        JLabel labelValorIngresso = new JLabel("Valor do Ingresso:");
        textFieldValorIngresso = new JTextField(15);
        JButton button = new JButton("Enviar");

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(labelTitulo);
        add(textFieldTitulo);
        add(labelDataHora);
        add(formattedTextFieldDataHora); // Adicionar o JFormattedTextField
        add(labelLocalizacao);
        add(textFieldLocalizacao);
        add(labelDescricao);
        add(scrollPane);
        add(labelCapacidade);
        add(textFieldCapacidade);
        add(labelValorIngresso);
        add(textFieldValorIngresso);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    enviarFormulario();
                } catch (SQLException ex) {
                    Logger.getLogger(FormularioEvento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void enviarFormulario() throws SQLException {
        if (textFieldTitulo.getText().isEmpty() || formattedTextFieldDataHora.getText().isEmpty()
                || textFieldLocalizacao.getText().isEmpty() || textAreaDescricao.getText().isEmpty()
                || textFieldCapacidade.getText().isEmpty() || textFieldValorIngresso.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                Evento evento = new Evento();
                evento.setTitulo(textFieldTitulo.getText());
                evento.setDataHora(formattedTextFieldDataHora.getText()); // Usar o texto do JFormattedTextField
                evento.setLocalizacao(textFieldLocalizacao.getText());
                evento.setDescricao(textAreaDescricao.getText());
                evento.setCapacidade(Integer.parseInt(textFieldCapacidade.getText()));
                evento.setValorIngressos(Double.parseDouble(textFieldValorIngresso.getText()));
                evento.setIdOrganizador(userID);

                EventoDAO eventoDAO = new EventoDAO();
                eventoDAO.cadastrarEvento(evento);
                JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");

                textFieldTitulo.setText("");
                formattedTextFieldDataHora.setText(""); // Limpar o JFormattedTextField
                textFieldLocalizacao.setText("");
                textAreaDescricao.setText("");
                textFieldCapacidade.setText("");
                textFieldValorIngresso.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira um valor válido para a capacidade e o valor do ingresso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
