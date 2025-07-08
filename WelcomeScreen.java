import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WelcomeScreen extends JFrame implements ActionListener {

    private final JTextField player1NameField;
    private final JTextField player2NameField;
    private final JButton startGameButton;
    private BufferedImage image;

    public WelcomeScreen() {
        try {
            image = ImageIO.read(new File("XO.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTitle("Welcome to Tic-Tac-Toe");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.YELLOW);


        JLabel welcomeLabel = new JLabel("X&O", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer
        mainPanel.setBackground(Color.YELLOW);

        JPanel playerNamesPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        playerNamesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerNamesPanel.setBackground(Color.YELLOW);

        JLabel player1Label = new JLabel("Player X Name:");
        player1Label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        player1NameField = new JTextField("Player X", 15);
        player1NameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        player1NameField.setBackground(Color.WHITE);

        JLabel player2Label = new JLabel("Player O Name:");
        player2Label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        player2NameField = new JTextField("Player O", 15);
        player2NameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        playerNamesPanel.add(player1Label);
        playerNamesPanel.add(player1NameField);
        playerNamesPanel.add(player2Label);
        playerNamesPanel.add(player2NameField);

        mainPanel.add(playerNamesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Spacer

        startGameButton = new JButton("Let's Play");
        startGameButton.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(this);
        mainPanel.add(startGameButton);
        startGameButton.setBackground(Color.YELLOW);

        add(mainPanel);
        setVisible(true);
    }
    class ImagePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            final String playerXName;
            final String playerOName;

            String tempPlayerXName = player1NameField.getText().trim();
            String tempPlayerOName = player2NameField.getText().trim();

            if (tempPlayerXName.isEmpty()) {
                playerXName = "Player X";
            } else {
                playerXName = tempPlayerXName;
            }

            if (tempPlayerOName.isEmpty()) {
                playerOName = "Player O";
            } else {
                playerOName = tempPlayerOName;
            }

            this.dispose();


            SwingUtilities.invokeLater(() -> new TicTacToeGUI(playerXName, playerOName));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen());
    }
}