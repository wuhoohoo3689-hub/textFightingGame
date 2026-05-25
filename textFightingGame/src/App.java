import com.dalao.ui.Login;
import com.dalao.ui.FightingGame;

public class App {
    public static void main(String[] args) {
       //启动类
        // 不写业务逻辑
        // 启动登录界面

        /*Login l = new Login();
        l.start();*/

        //创建fightinggame对象，调用gamestart、传进入zhangsan
        FightingGame fg = new FightingGame();
        fg.gameStart("zhangsan");
    }
}
