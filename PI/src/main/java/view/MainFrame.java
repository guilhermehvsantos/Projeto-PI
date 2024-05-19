package view;

import Controller.BancoDeDados;
import Model.Beans.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    private Usuario usuarioLogado;

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void limparUsuarioLogado() {
        this.usuarioLogado = null;
    }

    public MainFrame(int userID, Usuario usuarioLogado) throws SQLException {
        this.usuarioLogado = usuarioLogado; // Atribui o usuário logado ao campo usuarioLogado

        setTitle("Aplicativo de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu lateral e seção de perfil do usuário
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(1.0 / 3.0); // Define a proporção para 1/3
        splitPane.setPreferredSize(new Dimension(220, getHeight())); // Define a largura mínima

        // Seção de Perfil do Usuário
        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBackground(Color.LIGHT_GRAY);

        // Círculo de perfil (imagem do usuário)
        ImageIcon profileImageIcon = new ImageIcon("Cod\\src\\Assets\\Perfil.png");
        Image profileImage = profileImageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel profileImageLabel = new JLabel(new ImageIcon(profileImage));
        profileImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profileImageLabel.setVerticalAlignment(SwingConstants.CENTER);
        profilePanel.add(profileImageLabel, BorderLayout.CENTER);

        // Nome do usuário
        JLabel usernameLabel = new JLabel(usuarioLogado.getNome());
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(usernameLabel, BorderLayout.SOUTH);

        // Menu
        String[] menuItems = {"Profile", "Eventos", "Preferências", "Meus Ingressos", "Logout"};
        JList<String> menuList = new JList<>(menuItems);
        JScrollPane menuScrollPane = new JScrollPane(menuList);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuScrollPane);

        menuList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                String selectedMenuItem = menuList.getSelectedValue();
                if (selectedMenuItem != null && selectedMenuItem.equals("Logout")) {
                    realizarLogout();
                }
            }
        });

        // Adicionando os componentes ao JSplitPane
        splitPane.setTopComponent(profilePanel);
        splitPane.setBottomComponent(menuPanel);

        // Conteúdo principal (duas colunas do meio)
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Barra de pesquisa no topo
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Pesquisar");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Adicionando a barra de pesquisa no topo do painel de conteúdo
        contentPanel.add(searchPanel, BorderLayout.NORTH);

        // Conteúdo da pesquisa
        JTextArea searchResults = new JTextArea();
        searchResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResults);

        // Adicionando o conteúdo da pesquisa abaixo da barra de pesquisa
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Coluna lateral (última coluna)
        JPanel sidePanel = new JPanel(new GridLayout(3, 1));

        // Seção de texto
        JTextArea infoText = new JTextArea();
        infoText.setText("TEXTOOO");
        infoText.setEditable(false);
        sidePanel.add(new JScrollPane(infoText));

        // Painel para a imagem do mapa
        JPanel mapPanel = new ImagePanel("Cod\\src\\Assets\\mapa.jpg");
        mapPanel.add(new JLabel("Localização"));
        sidePanel.add(mapPanel);

        // Calendário
        JCalendar calendar = new JCalendar();
        sidePanel.add(calendar);

        // Adicionando os painéis ao frame
        add(splitPane, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        // Configuração do tamanho e visibilidade
        setSize(1200, 1000);
        setVisible(true);
    }

    private void realizarLogout() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente fazer logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                // Remova o usuário logado da tabela Login
                BancoDeDados.removerUsuarioLogado();
                // Feche o MainFrame
                dispose();
                // Abra a tela de escolha (Cadastro de Usuário ou Login)
                BancoDeDados.main(new String[]{});
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
