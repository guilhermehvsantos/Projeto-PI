package view;

import Model.Beans.Evento;
import Controller.EventoDAO;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class FormularioEvento extends JFrame {
    private JTextField textFieldTitulo;
    private JFormattedTextField formattedTextFieldDataHora;
    private JTextField textFieldLocalizacao;
    private JTextArea textAreaDescricao;
    private JTextField textFieldCapacidade;
    private JTextField textFieldIngressos;
    private int idOrganizador;

    public FormularioEvento(int idOrganizador) {
        this.idOrganizador = idOrganizador;

        setTitle("Formulário de Evento");
        setSize(270, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JLabel labelTitulo = new JLabel("Título do Evento:");
        textFieldTitulo = new JTextField(20);
        JLabel labelDataHora = new JLabel("Data e Hora (yyyy-MM-dd HH:mm):");
        try {
            MaskFormatter formatter = new MaskFormatter("####-##-## ##:##");
            formattedTextFieldDataHora = new JFormattedTextField(formatter);
            formattedTextFieldDataHora.setColumns(20);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        JLabel labelLocalizacao = new JLabel("Localização:");
        textFieldLocalizacao = new JTextField(20);
        JLabel labelDescricao = new JLabel("Descrição do Evento:");
        textAreaDescricao = new JTextArea(5, 20);
        textAreaDescricao.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textAreaDescricao);
        JLabel labelCapacidade = new JLabel("Capacidade:");
        textFieldCapacidade = new JTextField(20);
        JLabel labelIngressos = new JLabel("Ingressos:");
        textFieldIngressos = new JTextField(20);
        JLabel labelVazio = new JLabel("      ");
        JButton button = new JButton("Enviar");

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        add(labelTitulo);
        add(textFieldTitulo);
        add(labelDataHora);
        add(formattedTextFieldDataHora);
        add(labelLocalizacao);
        add(textFieldLocalizacao);
        add(labelDescricao);
        add(scrollPane);
        add(labelCapacidade);
        add(textFieldCapacidade);
        add(labelIngressos);
        add(labelVazio);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarFormulario();
            }
        });
    }

    private void enviarFormulario() {
        if (textFieldTitulo.getText().isEmpty() || formattedTextFieldDataHora.getText().isEmpty()
                || textFieldLocalizacao.getText().isEmpty() || textAreaDescricao.getText().isEmpty()
                || textFieldCapacidade.getText().isEmpty() || textFieldIngressos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            Evento evento = new Evento();
            evento.setTitulo(textFieldTitulo.getText());
            evento.setDataHora(formattedTextFieldDataHora.getText());
            evento.setLocalizacao(textFieldLocalizacao.getText());
            evento.setDescricao(textAreaDescricao.getText());
            evento.setCapacidade(Integer.parseInt(textFieldCapacidade.getText()));
            evento.setValorIngressos(Double.parseDouble(textFieldIngressos.getText()));
            evento.setIdOrganizador(idOrganizador);

            try {
                EventoDAO eventoDAO = new EventoDAO();
                eventoDAO.cadastrarEvento(evento);
                JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");

                textFieldTitulo.setText("");
                formattedTextFieldDataHora.setText("");
                textFieldLocalizacao.setText("");
                textAreaDescricao.setText("");
                textFieldCapacidade.setText("");
                textFieldIngressos.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        int idOrganizador = 1; // Exemplo de ID do organizador, isto deve ser passado de acordo com o login
        FormularioEvento frame = new FormularioEvento(idOrganizador);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
