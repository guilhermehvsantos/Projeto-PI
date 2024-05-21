package view;

import Model.Beans.Evento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioCompraIngresso extends JDialog {

    public FormularioCompraIngresso(JFrame parentFrame, Evento evento) {
        super(parentFrame, "Compra de Ingressos", true);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelTitulo = new JLabel("Título do Evento:");
        JTextField textTitulo = new JTextField(evento.getTitulo());
        textTitulo.setEditable(false);

        JLabel labelDataEvento = new JLabel("Data do Evento:");
        JTextField textDataEvento = new JTextField(evento.getDataHora());
        textDataEvento.setEditable(false);

        JLabel labelValorIngresso = new JLabel("Valor do Ingresso:");
        JTextField textValorIngresso = new JTextField(String.valueOf(evento.getValorIngressos()));
        textValorIngresso.setEditable(false);

        JLabel labelMeia = new JLabel("Meia:");
        JCheckBox checkBoxMeia = new JCheckBox();

        JLabel labelFormaPagamento = new JLabel("Forma de Pagamento:");
        String[] formasPagamento = {"Pix", "Cartão", "Boleto"};
        JComboBox<String> comboBoxFormasPagamento = new JComboBox<>(formasPagamento);

        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField textQuantidade = new JTextField();

        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implemente a lógica de compra aqui
                JOptionPane.showMessageDialog(FormularioCompraIngresso.this,
                        "Compra realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        panel.add(labelTitulo);
        panel.add(textTitulo);
        panel.add(labelDataEvento);
        panel.add(textDataEvento);
        panel.add(labelValorIngresso);
        panel.add(textValorIngresso);
        panel.add(labelMeia);
        panel.add(checkBoxMeia);
        panel.add(labelFormaPagamento);
        panel.add(comboBoxFormasPagamento);
        panel.add(labelQuantidade);
        panel.add(textQuantidade);
        panel.add(new JLabel()); // Espaço vazio para alinhar o botão
        panel.add(botaoComprar);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parentFrame);
    }
}
