package com.dalao.ui;

import com.dalao.domain.EnemyCharacter;
import com.dalao.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {

    //启动游戏欢迎页
    public void gameStart(String username) {
        System.out.println("|-------------------------------------|");
        System.out.println("|----" + username + "欢迎来带文字格斗游戏----|");
        System.out.println("|-------------------------------------|");

        //创建玩家角色：名字+属性
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功！");
        //打印用户属性 和 技能列表
        System.out.println("初始属性为：" + player.show());
        System.out.println("技能列表为：" + player.showSkillList());

        //创建多个敌人的集合
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        //5 准备战斗
        //统计战斗回合
        int count = 1;
        //统计赢的次数
        int win = 0;
        //user还是alive的
        while (player.isAlive()) {
            //进入战斗循环

            //5.1重置敌人属性，从第二场开始给敌人加血条，意味着第一场赢了
            if (count > 1) {
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP = c.maxHP + 10;
                    c.HP = c.maxHP;
                    c.attack = c.attack + 3;
                    c.defense = c.defense + 2;
                    c.defending = false;
                }
            }

            // 5.2 随机选择敌人
            Random random = new Random();
            int index = random.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println(enemy.show());

            //5.3 全部战斗开始
            System.out.println("=================================");
            System.out.println("第" + count + "场战斗开始！对手：" + enemy.name);

            //跟当前敌人第几回合
            int round = 1;
            while (player.isAlive()) {
                //显示双方的生命值
                System.out.println("-------------------------------");
                //第几回合
                System.out.println("第" + round + "回合开始！");
                System.out.println(getHealthBar(player.name, player.HP, player.maxHP));
                System.out.println(getHealthBar(enemy.name, enemy.HP, enemy.maxHP));
                System.exit(0);

            }




        }


    }

    //显示血条的方法
    public String getHealthBar(String name, int HP, int maxHP) {
        //满血20个方块
        int barLength = 20;
        int fill = (int) (HP * 1.0 / maxHP * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name + ": 【");
        for (int i = 0; i < barLength; i++) {
            if (i < fill) {
                sb.append("⬛\uFE0F");
            } else {
                sb.append("-");
            }
        }
        sb.append("】" + HP + "/" + maxHP + " HP");
        return sb.toString();
    }

    public HeroCharacter createPlayerCharacter(String username) {
        //分配属性
        int point = 20;
        //提示
        System.out.println("请分配属性点（共20点）：");
        System.out.println("1. 生命值（每点 + 10HP）");
        System.out.println("2. 攻击力（每点 + 2ATK）");
        System.out.println("3. 防御力（每点 + 1DEF）");

        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻击力", "防御力"};
        //记录三个值
        int[] value = new int[3];

        for (int i = 0; i < 3; i++) {
            System.out.println("请分配点数到" + attributes[i] + "（剩余点数：" + point + "）：");
            int input = sc.nextInt();

            if (input < 0) {
                System.out.println("输入有误，请重新输入");
                input = 0;
            }
            if (input > point) {
                System.out.println("属性点不足！剩余属性点全部分配到：" + attributes[i]);
                input = point;
            }
            point -= input;
            value[i] = input;


        }

        //创建herocharacter对象
        HeroCharacter player = new HeroCharacter(username, 100 + value[0] * 10, //生命值
                10 + value[1] * 2,  //攻击
                0 + value[2] * 1    //防御
        );

        //添加玩家技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        //返回player
        return player;


    }
}
