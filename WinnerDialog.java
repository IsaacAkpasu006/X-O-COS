import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinnerDialog extends JDialog {

    public WinnerDialog(Frame owner, String winnerName, Runnable resetCallback) {
        super(owner, "We Have A Winner", true);


        setSize(300, 200);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(0, 255, 0));

        JLabel winnerLabel = new JLabel(winnerName + " WINS!", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panel.add(winnerLabel, BorderLayout.CENTER);

        JButton resetButton = new JButton("Restart");
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