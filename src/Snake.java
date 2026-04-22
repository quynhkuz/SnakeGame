package src;

public class Snake {
    // Mảng lưu tọa độ X và Y của từng phần thân rắn
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];

    // Độ dài của rắn và số lần di chuyển
    int lengthOfSnake;
    int moves;

    // Hướng di chuyển của rắn
    boolean left;
    boolean right;
    boolean up;
    boolean down;

    // Trạng thái rắn đã chết hay chưa
    boolean death;

    // Hàm khởi tạo
    public Snake(){
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.death = false;
        this.lengthOfSnake = 5;
        this.moves = 0;
    }

    // Di chuyển sang phải
    public void moveRight(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            // Không cho quay đầu trực tiếp
            if (!this.left) {
                this.right = true;
            } else {
                this.right = false;
                this.left = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    // Di chuyển sang trái
    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    // Di chuyển lên trên
    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    // Di chuyển xuống dưới
    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    // Hàm xử lý khi rắn chết (tránh lặp code nhiều lần)
    public void dead() {
        // Dừng toàn bộ chuyển động
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.death = true;
    }

    // Di chuyển rắn sang phải
    public void movementRight(){
        // Dịch chuyển thân theo trục Y
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        // Dịch chuyển theo trục X
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            if (i == 0) {
                // Đầu rắn tiến sang phải
                this.snakexLength[i] = this.snakexLength[i] + 6;
            } else {
                // Thân rắn đi theo đốt phía trước
                this.snakexLength[i] = this.snakexLength[i - 1];
            }

            // Nếu chạm biên phải
            if (this.snakexLength[0] > 637) {
                this.snakexLength[0] -= 6;
                dead();
            }
        }
    }

    // Di chuyển rắn sang trái
    public void movementLeft(){
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakexLength[i] = this.snakexLength[i] - 6;
            } else {
                this.snakexLength[i] = this.snakexLength[i - 1];
            }

            // Nếu chạm biên trái
            if (this.snakexLength[0] < 25) {
                this.snakexLength[0] += 6;
                dead();
            }
        }
    }

    // Di chuyển rắn lên trên
    public void movementUp(){
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] - 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }

            // Nếu chạm biên trên
            if (this.snakeyLength[0] < 73) {
                this.snakeyLength[0] += 6;
                dead();
            }
        }
    }

    // Di chuyển rắn xuống dưới
    public void movementDown(){
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] + 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }

            // Nếu chạm biên dưới
            if (this.snakeyLength[0] > 679) {
                this.snakeyLength[0] -= 6;
                dead();
            }
        }
    }
}