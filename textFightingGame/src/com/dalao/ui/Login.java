package com.dalao.ui;

import com.dalao.domain.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {
    private Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println("登录注册页打开了");


        //定义用户队列
        ArrayList<User> list = new ArrayList<>();

        //欢迎界面
        while (true) {
            System.out.println("|-------------------------------|");
            System.out.println("|------欢迎来带文字格斗游戏------|");
            System.out.println("|-------------------------------|");
            System.out.println("请选择操作：1.登录 2.注册 3.退出");

            // 输入
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();


            //switch
            switch (choice) {
                //防止穿透的写法
                case "1" -> login(list);
                case "2" -> register(list);
                case "3" -> {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);
                }
                default -> {
                    System.out.println("输入有误，请重新输入");
                }
            }
        }


    }

    //登陆方法
    public void login(ArrayList<User> list) {
        System.out.println("用户选择了登录操作");


        //1.输入用户名
        System.out.println("请输入用户名");
        String username = sc.next();

        //2.判断用户名是否存在,不存在 提示未注册，回到选择页
        if (!contains(list, username)) {
            System.out.println("用户名" + username + "不存在");
            //如果用户名不存在，跳出登录方法，回到选择界面
            return;
        }

        //3.用户名存在，但是状态是禁用，联系管理员
        int index = findIndex(list, username);
        User u = list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户" + username + "被禁用，请联系管理员");
            //如果用户被禁用，跳出登录方法，回到选择界面
            return;
        }

        //4.验证码和密码
        String rightPassword = u.getPassword();
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码");
            String password = sc.next();

            //验证码是否正确
            while (true) {
                //生成一个正确的验证码
                String rightCode = generateCode();
                System.out.println("正确的验证码为：" + rightCode);
                //让用户输入验证码
                System.out.println("请输入验证码");
                String code = sc.next();
                //校验两者是否一样，一样提示验证码正确，不一样提示验证码错误 ，重新生成一个验证码，让用户重新输入
                if (code.equals(rightCode)) {
                    System.out.println("验证码正确");
                    break;
                } else {
                    System.out.println("验证码错误");
                    continue;
                }
            }


            if (rightPassword.equals(password)) {
                System.out.println("登录成功，游戏启动");
                //创建fightinggame对象，调用gamestart、
                FightingGame fg = new FightingGame();
                fg.gameStart(username);
                break;
            } else if (i == 2) {
                System.out.println("密码错误次数过多，用户" + username + "被禁用，请联系管理员");
                u.setStatus(false);
                return;
            } else {
                System.out.println("密码错误，还剩下" + (2 - i) + "次机会，请重新输入");
            }

        }


    }


    //注册方法
    public void register(ArrayList<User> list) {
        System.out.println("用户选择了注册操作");
        //用户名 密码 写入userarraylist

        //创建 User对象
        User u = new User();


        //用户名
        while (true) {
            System.out.println("请输入用户名");
            String username = sc.next();
            //校验用户名，要求 长度3-16个，
            if (!isLengthBetween(username, 3, 16)) {
                System.out.println("用户名长度必须在3-16个字符之间");
                continue;
            }
            //校验用户名，要求 必须含字母 可以含数字 不能有其他字符
            if (!isUsernameValid(username)) {
                System.out.println("用户名只能包含字母和数字，且必须包含字母");
                continue;
            }
            //校验用户名唯一
            if (contains(list, username)) {
                System.out.println("用户名已存在，请重新输入");
                continue;
            }

            //代码到这里表示用户名符合要求
            u.setName(username);
            break;


        }
        //密码
        while (true) {
            System.out.println("请输入密码");
            String password1 = sc.next();
            System.out.println("请再次输入密码");
            String password2 = sc.next();

            //校验密码，要求 长度3-8个，
            if (!isLengthBetween(password1, 3, 8)) {
                System.out.println("密码长度必须在3-8个字符之间");
                continue;
            }
            //校验密码，要求 必须含字母和数字 不能有其他字符
            if (!isPasswordValid(password1)) {
                System.out.println("密码必须包含字母和数字，不能包含其他字符");
                continue;
            }
            //校验密码是否一致
            if (!password1.equals(password2)) {
                System.out.println("两次输入密码不一致，请重新输入");
                continue;
            }

            //代码到这里表示密码符合要求
            u.setPassword(password1);
            break;
        }

        //写入 array
        list.add(u);

        //提示成功
        System.out.println("用户：" + u.getName() + "注册成功");


    }

    //在集合中找到username所在的索引的方法
    public int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(username)) {
                return i;
            }
        }
        return -1;
    }


    //判断长度是否在min-max之间的方法
    public boolean isLengthBetween(String str, int min, int max) {
        return str.length() >= min && str.length() <= max;
    }

    //判断传进来的字符串里有几个字母、数字、其他字符的方法
    public int[] countCharacters(String str) {
        //定义一个数组，用于存储字母、数字、其他字符的数量
        int[] count = {0, 0, 0};
        //循环遍历字符串
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            //判断是否为字母
            if (Character.isLetter(c)) {
                count[0]++;
            } else if (Character.isDigit(c)) {
                count[1]++;
            } else {
                count[2]++;
            }
        }
        return count;
    }

    //判断用户名是否符合要求的方法
    public boolean isUsernameValid(String str) {
        int[] count = countCharacters(str);
        return count[0] > 0 && count[1] >= 0 && count[2] == 0;
    }

    //判断是否包含在集合中的方法
    public boolean contains(ArrayList<User> list, String username) {
        for (User u : list) {
            if (u.getName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //判断密码是否符合要求的方法
    public boolean isPasswordValid(String str) {
        int[] count = countCharacters(str);
        return count[0] > 0 && count[1] > 0 && count[2] == 0;
    }

    //随机生成验证码 4个字母+1个数字 方法
    public String generateCode() {
        //1.定义一个空集合，循环遍历 a-z，A-Z，得到所有字母
        ArrayList<Character> letters = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            letters.add((char) ('a' + i));
        }
        for (int i = 0; i < 26; i++) {
            letters.add((char) ('A' + i));
        }

        //2.从集合中随机抽取字母，拼接成字符串
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(letters.size());
            char c = letters.get(index);
            sb.append(c);
        }

        //3.随机抽取一个数字，拼接在字母后面
        int num = random.nextInt(10);
        sb.append(num);

        //4.随机挑选最后一个index前的index，跟最后一个index交换
        //把sb变成字符数组
        char[] codeArr = sb.toString().toCharArray();
        //随机挑选最后一个index前的index，跟最后一个index交换
        int i = random.nextInt(codeArr.length - 1);
        //交换
        char temp = codeArr[i];
        codeArr[i] = codeArr[codeArr.length - 1];
        codeArr[codeArr.length - 1] = temp;

        //5.把字符数组变成字符串
        String code = new String(codeArr);
        //返回string格式
        return code;
    }


}


