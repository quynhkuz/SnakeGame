package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private int score;

    // Hàm khởi tạo (constructor)
    public Score(){
        this.score = 0;
    }

    // Tăng điểm lên 1
    public void increaseScore(){
        this.score++;
    }

    // Reset điểm về 0
    public void resetScore(){
        this.score = 0;
    }

    // Lấy giá trị điểm hiện tại để hiển thị trong Gameplay
    public int getScore(){
        return this.score;
    }

    // Hàm lấy danh sách điểm cao (HighScore)
    public String getHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            // Đọc file highscore.dat
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
                // Đọc từng dòng trong file
                line = reader.readLine();

                // Kiểm tra tránh lỗi null
                if (line == null)
                    break;

                // Ghép các dòng lại thành một chuỗi
                allLines = allLines.concat("\n" + line);
            }

            // Trả về toàn bộ nội dung file
            return allLines;
        }
        // Nếu file highscore.dat không tồn tại
        catch (Exception e) {
            // Trả về 10 dòng mặc định = 0
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
                // Đóng reader
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Hàm sắp xếp điểm cao từ cao xuống thấp
    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();

        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

            // Đưa dữ liệu từ file vào List
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

            // Sắp xếp tăng dần
            Collections.sort(list);

            // Đảo ngược để thành giảm dần (điểm cao trước)
            Collections.reverse(list);

            // Ghi lại vào file
            writeFile = new FileWriter("highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            // Chỉ giữ lại top 10 điểm cao nhất
            for (int i = 0; i < 10; i++) {
                if (i > size - 1) {
                    // Nếu không đủ thì điền 0
                    writer.write("0");
                } else {
                    // Ghi điểm từ list
                    writer.write(String.valueOf(list.get(i)));
                }

                // Xuống dòng (tránh dòng trống cuối file)
                if (i < 9) {
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                // Đóng reader và writer
                if (reader != null)
                    reader.close();
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                return;
            }
        }
    }

    // Hàm lưu điểm mới vào file
    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

        // Tạo file lưu điểm
        File scoreFile = new File("highscore.dat");

        // Nếu file chưa tồn tại thì tạo mới
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
            // Ghi thêm điểm mới vào cuối file
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }
    }
}