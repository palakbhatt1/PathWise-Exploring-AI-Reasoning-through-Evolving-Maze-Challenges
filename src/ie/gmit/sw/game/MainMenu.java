package ie.gmit.sw.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    private static class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(getBackground().darker());
            } else {
                g.setColor(getBackground());
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }
    }

    private RoundButton hardButton;
    private RoundButton mediumButton;
    private RoundButton easyButton;
    private RoundButton exitButton;

    public MainMenu() {
        setTitle("PathWise");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel with background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(158, 169, 237));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Icon
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon("resources/altharry.png"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(iconLabel, gbc);

        // Title
        JLabel titleLabel = new JLabel("PathWise");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Select Difficulty");
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 36));
        subtitleLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        mainPanel.add(subtitleLabel, gbc);

        // Buttons
        hardButton = new RoundButton("Hard Mode (90x90)");
        mediumButton = new RoundButton("Medium Mode (65x65)");
        easyButton = new RoundButton("Easy Mode (35x35)");
        exitButton = new RoundButton("Exit");

        // Style buttons
        RoundButton[] buttons = {hardButton, mediumButton, easyButton, exitButton};
        for (RoundButton button : buttons) {
            button.setPreferredSize(new Dimension(300, 60));
            button.setFont(new Font("Serif", Font.BOLD, 18));
            button.setBackground(new Color(0, 0, 139));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(this);
        }

        gbc.gridwidth = 1;
        gbc.gridy = 3;
        mainPanel.add(hardButton, gbc);

        gbc.gridy = 4;
        mainPanel.add(mediumButton, gbc);

        gbc.gridy = 5;
        mainPanel.add(easyButton, gbc);

        gbc.gridy = 6;
        mainPanel.add(exitButton, gbc);

        add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hardButton) {
            startGame(Difficulty.HARD);
        } else if (e.getSource() == mediumButton) {
            startGame(Difficulty.MEDIUM);
        } else if (e.getSource() == easyButton) {
            startGame(Difficulty.EASY);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void startGame(Difficulty difficulty) {
        this.dispose(); // Close menu
        try {
            GameRunner.setDifficulty(difficulty);
            new GameRunner();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error starting game: " + e.getMessage());
            new MainMenu(); // Return to menu on error
        }
    }

    public static void showMainMenu() {
        SwingUtilities.invokeLater(MainMenu::new);
    }

    public static void showWinScreen(Difficulty currentDifficulty) {
        SwingUtilities.invokeLater(() -> {
            JFrame winFrame = new JFrame("Victory!");
            winFrame.setSize(300, 200);
            winFrame.setLocationRelativeTo(null);
            winFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            winFrame.setAlwaysOnTop(true);

            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel winLabel = new JLabel("Congratulations! You won!", SwingConstants.CENTER);
            winLabel.setFont(new Font("Serif", Font.BOLD, 20));

            JButton retryButton = new JButton("Play Again");
            JButton exitButton = new JButton("Exit");

            retryButton.addActionListener(e -> {
                winFrame.dispose();
                try {
                    new GameRunner();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            });

            exitButton.addActionListener(e -> System.exit(0));

            winFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            panel.add(winLabel);
            panel.add(retryButton);
            panel.add(exitButton);

            winFrame.add(panel);
            winFrame.setVisible(true);
        });
    }

    public static void showGameOverScreen(Difficulty currentDifficulty) {
        SwingUtilities.invokeLater(() -> {
            JFrame gameOverFrame = new JFrame("Game Over");
            gameOverFrame.setSize(300, 200);
            gameOverFrame.setLocationRelativeTo(null);
            gameOverFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            gameOverFrame.setAlwaysOnTop(true);

            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel gameOverLabel = new JLabel("Game Over! You were defeated!", SwingConstants.CENTER);
            gameOverLabel.setFont(new Font("Serif", Font.BOLD, 16));
            gameOverLabel.setForeground(Color.RED);

            JButton retryButton = new JButton("Retry");
            JButton exitButton = new JButton("Exit");

            retryButton.addActionListener(e -> {
                gameOverFrame.dispose();
                try {
                    new GameRunner();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            });

            exitButton.addActionListener(e -> System.exit(0));

            gameOverFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            panel.add(gameOverLabel);
            panel.add(retryButton);
            panel.add(exitButton);

            gameOverFrame.add(panel);
            gameOverFrame.setVisible(true);
        });
    }
}
