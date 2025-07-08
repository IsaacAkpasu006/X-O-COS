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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameEnded) {
            return;
        }

        JButton clickedButton = (JButton) e.getSource();
        String[] coords = clickedButton.getActionCommand().split(",");
        int row = Integer.parseInt(coords[0]);
        int col = Integer.parseInt(coords[1]);

        if (board[row][col] == ' ') { // Check if the spot is empty
            board[row][col] = currentPlayer;
            clickedButton.setText(String.valueOf(currentPlayer));
            movesMade++;

            if (checkWin()) {
                statusLabel.setText(getPlayerName(currentPlayer) + " wins!");
                new WinnerDialog(this, getPlayerName(currentPlayer), this::resetGame);
                gameEnded = true;
                disableAllButtons();
            } else if (movesMade == 9) {
                statusLabel.setText("It's a draw!");
                gameEnded = true;
            } else {
                switchPlayer();
                statusLabel.setText(getPlayerName(currentPlayer) + "'s turn");
            }
        } else {
            statusLabel.setText("Invalid move! Try again, " + getPlayerName(currentPlayer) + ".");
        }
    }



    private boolean checkWin() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns() {
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer && board[1][j] == currentPlayer && board[2][j] == currentPlayer) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void enableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private void resetGame() {
        initializeBoardLogic();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        enableAllButtons();
        currentPlayer = 'X';
        gameEnded = false;
        movesMade = 0;
        statusLabel.setText(getPlayerName(currentPlayer) + "'s turn");
    }




}

    
    
