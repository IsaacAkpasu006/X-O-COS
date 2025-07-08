import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private JButton[][] buttons = new JButton[3][3];
    private JLabel statusLabel;
    private char currentPlayer = 'X';
    private boolean gameEnded = false;
    private int movesMade = 0;


    private String playerXName;
    private String playerOName;

    private char[][] board = new char[3][3];


    public TicTacToeGUI(String playerXName, String playerOName) {
        this.playerXName = playerXName;
        this.playerOName = playerOName;

        setTitle("X&O");
        setSize(400, 450); // Increased height for status label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Initialize the board internally
        initializeBoardLogic();

        // Create the main panel for layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.YELLOW);

        // Status label at the top
        statusLabel = new JLabel(getPlayerName(currentPlayer) + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        mainPanel.add(statusLabel, BorderLayout.NORTH);
        statusLabel.setBackground(Color.YELLOW);


        // Game board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        initializeButtons(boardPanel);
        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Reset button at the bottom (optional but good for UX)
        JButton resetButton = new JButton("Restart Game");
        resetButton.addActionListener(e -> resetGame());
        mainPanel.add(resetButton, BorderLayout.SOUTH);

        add(mainPanel); // Add the main panel to the frame
        setVisible(true); // Make the frame visible
    }


    private String getPlayerName(char playerChar) {
        return (playerChar == 'X') ? playerXName : playerOName;
    }



    private void initializeBoardLogic() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public class WinnerDialog extends JDialog {

        public WinnerDialog(Frame owner, String winnerName, Runnable resetCallback) {
            super(owner, "Game Over", true); // modal dialog

            setSize(300, 150);
            setLocationRelativeTo(owner);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(new Color(200, 255, 200));

            JLabel message = new JLabel(winnerName + " wins!", SwingConstants.CENTER);
            message.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            panel.add(message, BorderLayout.CENTER);

            JButton resetButton = new JButton("Reset");
            resetButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            resetButton.addActionListener(e -> {
                dispose();
                resetCallback.run();
            });
            panel.add(resetButton, BorderLayout.SOUTH);

            add(panel);
            setVisible(true);
        }
    }


    private void initializeButtons(JPanel boardPanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
                button.setFocusPainted(false);
                button.setActionCommand(i + "," + j);
                button.addActionListener(this);
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
    }

    // Handles button clicks
    