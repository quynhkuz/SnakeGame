package src;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // Khởi tạo đối tượng rắn
    Snake snake = new Snake();

    // Khởi tạo đối tượng táo
    Apple apple = new Apple();

    // Hình ảnh đầu rắn
    private ImageIcon snakeHead;

    private Timer timer;
    private int delay = 500;
    private ImageIcon snakeBody;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    // Tọa độ ban đầu của đầu rắn
    private int snakeHeadXPos = 379;

    // Hình ảnh quả táo
    private ImageIcon appleImage;

    // Dùng để tạo số ngẫu nhiên
    private Random random = new Random();

    private int xPos = random.nextInt(100);
    private int yPos = random.nextInt(100);

    // Hình ảnh tiêu đề game
    private ImageIcon titleImage;

    // Đối tượng điểm số
    Score score = new Score();

    // Chuỗi lưu điểm cao nhất
    private String highScore;

    // Hình ảnh hướng dẫn điều khiển
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;

    public Gameplay() {
        // Thiết lập khi game bắt đầu
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // Nếu game chưa bắt đầu thì khởi tạo vị trí ban đầu của rắn
        if (snake.moves == 0) {
            for (int i = 0; i < 5; i++) {
                snake.snakexLength[i] = snakeHeadXPos;
                snakeHeadXPos -= 6;
                snake.snakeyLength[i] = 355;
            }
        }

        // Vẽ khung tiêu đề
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        // Hiển thị tiêu đề
        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

        // Vẽ khung khu vực chơi
        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 620, 614);

        // Nền khu vực chơi
        g.setColor(Color.black);
        g.fillRect(25, 72, 619, 613);

        // Khung bảng điểm
        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

        // Nền bảng điểm
        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

        // Hiển thị điểm hiện tại
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        g.drawRect(653, 130, 221, 1);

        // Hiển thị điểm cao nhất
        score.sortHighScore();
        highScore = score.getHighScore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 705, 200);

        // Hiển thị hướng dẫn điều khiển
        g.drawRect(653, 490, 221, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString("CONTROLS", 690, 530);

        arrowImage = new ImageIcon("images/keyboardArrow.png");
        arrowImage.paintIcon(this, g, 670, 560);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 770, 590);

        shiftImage = new ImageIcon("images/shift.png");
        shiftImage.paintIcon(this, g, 695, 625);
        g.drawString("Boost", 770, 640);

        // Vẽ đầu rắn
        snakeHead = new ImageIcon("images/snakeHead4.png");
        snakeHead.paintIcon(this, g, snake.snakexLength[0], snake.snakeyLength[0]);

        // Vẽ toàn bộ thân rắn
        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (i == 0 && (snake.right || snake.left || snake.up || snake.down)) {
                snakeHead = new ImageIcon("images/snakeHead4.png");
                snakeHead.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody = new ImageIcon("images/snakeimage4.png");
                snakeBody.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
        }

        appleImage = new ImageIcon("images/apple4.png");

        // Kiểm tra nếu rắn ăn táo
        if ((apple.applexPos[xPos]) == snake.snakexLength[0] && (apple.appleyPos[yPos] == snake.snakeyLength[0])) {
            snake.lengthOfSnake++;
            score.increaseScore();
            xPos = random.nextInt(100);
            yPos = random.nextInt(100);

            // Tăng tốc độ khi đạt các mốc điểm
            if (score.getScore() % 5 == 0 && score.getScore()!= 0){
                if(delay > 100){
                    delay = delay - 100;
                }
                else if (delay == 100){
                    delay = delay - 50;
                }
                else if (delay <= 50 && delay > 20){
                    delay = delay - 10;
                }
                else {
                    delay = 20;
                }
                timer.setDelay(delay);
            }
        }

        // Chỉ hiển thị táo khi game đã bắt đầu
        if (snake.moves != 0) {
            appleImage.paintIcon(this, g, apple.applexPos[xPos], apple.appleyPos[yPos]);
        }

        // Hiển thị thông báo bắt đầu game
        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        // Kiểm tra va chạm với thân
        for (int i = 1; i < snake.lengthOfSnake; i++) {
            if (snake.snakexLength[i] == snake.snakexLength[0] && snake.snakeyLength[i] == snake.snakeyLength[0]) {
                snake.dead();
            }
        }

        // Nếu rắn chết
        if (snake.death) {
            score.saveNewScore();

            // Hiển thị Game Over
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 190, 340);

            // Hiển thị điểm
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 250, 370);

            // Hướng dẫn chơi lại
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 187, 400);
        }
        g.dispose();
    }

    // Hàm hiển thị chuỗi nhiều dòng (có ký tự xuống dòng \n)
    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        // Di chuyển rắn theo hướng hiện tại
        if (snake.right) {
            snake.movementRight();
            repaint();
        }
        if (snake.left) {
            snake.movementLeft();
            repaint();
        }
        if (snake.up) {
            snake.movementUp();
            repaint();
        }
        if (snake.down) {
            snake.movementDown();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Nhấn SHIFT để tăng tốc
            case KeyEvent.VK_SHIFT:
                if (speedUp.compareAndSet(true, false)) {
                    if (delay > 100) {
                        timer.setDelay(delay/10);
                    } else {
                        timer.setDelay(10);
                    }
                }
                break;

            // Nhấn SPACE để bắt đầu hoặc chơi lại
            case KeyEvent.VK_SPACE:
                if (snake.moves == 0) {
                    snake.moves++;
                    snake.right = true;
                }
                if (snake.death) {
                    snake.moves = 0;
                    snake.lengthOfSnake = 5;
                    score.resetScore();
                    repaint();
                    snake.death = false;
                }
                break;

            // Điều khiển hướng di chuyển
            case KeyEvent.VK_RIGHT:
                snake.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                snake.moveLeft();
                break;
            case KeyEvent.VK_UP:
                snake.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                snake.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Thả SHIFT thì trở lại tốc độ bình thường
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }
}