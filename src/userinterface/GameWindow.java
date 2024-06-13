package userinterface;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    
    public static final int SCREEN_WIDTH = 600; // 窗口宽度
    private GameScreen gameScreen; // 游戏屏幕对象
    
    // 构造函数
    public GameWindow() {
        super("Java T-Rex game"); // 调用父类构造函数，设置窗口标题
        setSize(SCREEN_WIDTH, 175); // 设置窗口大小
        setLocation(400, 200); // 设置窗口位置
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口关闭操作
        setResizable(false); // 设置窗口不可调整大小
        
        gameScreen = new GameScreen(); // 创建游戏屏幕对象
        addKeyListener(gameScreen); // 给游戏屏幕添加键盘监听器
        add(gameScreen); // 将游戏屏幕添加到窗口中
    }
    
    // 启动游戏
    public void startGame() {
        setVisible(true); // 设置窗口可见
        gameScreen.startGame(); // 启动游戏屏幕
    }
    
    // 主方法
    public static void main(String args[]) {
        (new GameWindow()).startGame(); // 创建游戏窗口并启动游戏
    }
}
