import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class WorkoutTimer extends JFrame {
    private JLabel timeLabel;
    private JButton startStopButton, resetButton, stopCountdownButton;
    private JTextField timerInput;
    private boolean running = false;
    private int seconds = 0;
    private Timer timer;
    private TimerTask task;

    // For Countdown Timer
    private boolean countdownRunning = false;
    private int countdownSeconds = 0;
    private Timer countdownTimer;
    private TimerTask countdownTask;

    public WorkoutTimer() {
        setTitle("Workout Timer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Stopwatch Label
        timeLabel = new JLabel("00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(timeLabel, gbc);

        // Start/Stop Button
        startStopButton = new JButton("Start");
        startStopButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startStopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!running) {
                    startStopwatch();
                } else {
                    stopStopwatch();
                }
            }
        });
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(startStopButton, gbc);

        // Reset Button
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 20));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });
        gbc.gridx = 1;
        add(resetButton, gbc);

        // Countdown Timer Input
        JLabel countdownLabel = new JLabel("Countdown (in seconds): ");
        countdownLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(countdownLabel, gbc);

        timerInput = new JTextField(10);
        gbc.gridy = 3;
        add(timerInput, gbc);

        // Start Countdown Button
        JButton startCountdownButton = new JButton("Start Countdown");
        startCountdownButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startCountdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown();
            }
        });
        gbc.gridy = 4;
        add(startCountdownButton, gbc);

        // Stop Countdown Button
        stopCountdownButton = new JButton("Stop Countdown");
        stopCountdownButton.setFont(new Font("Arial", Font.PLAIN, 20));
        stopCountdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCountdown();
            }
        });
        gbc.gridy = 5;
        add(stopCountdownButton, gbc);

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startStopwatch() {
        startStopButton.setText("Stop");
        running = true;

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                seconds++;
                updateStopwatchDisplay();
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void stopStopwatch() {
        startStopButton.setText("Start");
        running = false;
        if (task != null) {
            task.cancel();
        }
    }

    private void resetStopwatch() {
        seconds = 0;
        updateStopwatchDisplay();
        if (task != null) {
            task.cancel();
        }
        startStopButton.setText("Start");
        running = false;
    }

    private void updateStopwatchDisplay() {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
    }

    private void startCountdown() {
        if (countdownRunning) {
            JOptionPane.showMessageDialog(this, "Countdown is already running.");
            return;
        }

        try {
            countdownSeconds = Integer.parseInt(timerInput.getText());
            if (countdownSeconds <= 0) {
                JOptionPane.showMessageDialog(this, "Enter a valid positive number.");
                return;
            }
            countdownRunning = true;

            countdownTimer = new Timer();
            countdownTask = new TimerTask() {
                @Override
                public void run() {
                    countdownSeconds--;
                    updateCountdownDisplay();
                    if (countdownSeconds <= 0) {
                        countdownTimer.cancel();
                        countdownRunning = false;
                        JOptionPane.showMessageDialog(null, "Countdown Complete!");
                    }
                }
            };
            countdownTimer.scheduleAtFixedRate(countdownTask, 1000, 1000);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    private void stopCountdown() {
        if (countdownRunning) {
            countdownTimer.cancel();
            countdownRunning = false;
            JOptionPane.showMessageDialog(this, "Countdown Stopped!");
        } else {
            JOptionPane.showMessageDialog(this, "No countdown is running.");
        }
    }

    private void updateCountdownDisplay() {
        int minutes = countdownSeconds / 60;
        int seconds = countdownSeconds % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        new WorkoutTimer();
    }
}
