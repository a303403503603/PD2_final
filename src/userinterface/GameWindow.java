package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    
    public static final int SCREEN_WIDTH = 600; // 窗口寬度
    private GameScreen gameScreen; // 遊戲屏幕對象
    
    // 建構函數
    public GameWindow() {
        super("Java T-Rex game"); // 調用父類建構函數，設置標題
        setSize(SCREEN_WIDTH, 175); // 設置窗口大小
        setLocation(400, 200); // 設置窗口位置
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設置窗口關閉操作
        setResizable(false); // 設置窗口不可調整大小
        
        gameScreen = new GameScreen(); // 創建遊戲屏幕對象
        addKeyListener(gameScreen); // 給遊戲屏幕添加鍵盤監聽器
        add(gameScreen); // 將遊戲屏幕添加到窗口中
    }
    
    // 啟動遊戲
    public void startGame() {
        setVisible(true); // 設置窗口可見
        gameScreen.startGame(); // 啟動遊戲屏幕
    }
    
    // main function
    public static void main(String args[]) {
        (new GameWindow()).startGame(); // 創建遊戲窗口並啟動遊戲
    }
}
