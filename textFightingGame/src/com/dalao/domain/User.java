package com.dalao.domain;

import java.util.Random;

public class User {
    private String ID;
    private String userName;
    private String password;
    private boolean status;

    //空参构造
    public User() {
        ID = createID();
        status = true;
    }
    //带参构造
    public User(String name, String password ) {
        ID =createID();
        this.userName = name;
        this.password = password;
        status = true;
    }

    //自动生成ID的方法，ID为 dalao 加 6位随机数
    public String createID() {
        //用string bulider 创建dalao
        StringBuilder sb = new StringBuilder("dalao");
        //调用random
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(10) ;
            sb.append(num);
        }

        //返回string格式
        return  ID = sb.toString();
    }

    //status初始值为true


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
