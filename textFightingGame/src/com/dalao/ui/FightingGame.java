package com.dalao.ui;

import com.dalao.domain.EnemyCharacter;
import com.dalao.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {

    //1.启动游戏欢迎页
    public void gameStart(String username) {
        System.out.println("|-------------------------------------|");
        System.out.println("|----" + username + "欢迎来带文字格斗游戏----|");
        System.out.println("|-------------------------------------|");

        //2.创建玩家角色：名字+属性
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("角色创建成功！");
        //3.打印用户属性 和 技能列表
        System.out.println("初始属性为：" + player.show());
        System.out.println("技能列表为：" + player.showSkillList());

        //4.创建多个敌人的集合
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
        // 在游戏中。我是依次跟多个敌人进行战斗。直到我方的生命值为0，游戏才会结束。
        // 第1个敌人
        // 第2个敌人
        // 第3个敌人
        // 第4个敌人
        // 第5个敌人
        // 循环来进行表示
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

                //5.4 玩家回合，选择行为
                playerTurn(player, enemy);

                //5.5 判断敌人是否被击败
                if (!enemy.isAlive()) {
                    System.out.println("你击败了" + enemy.name + "!");
                    win++;
                    break;
                }

                //5.6 敌人回合:选择行动(50%的几率普通攻击/50%的几率技能攻击/不同的敌人采取不网的技能进行攻击)
                enemyTurn(player, enemy);

                //5.7 判断我方是否被击败
                if (!player.isAlive()) {
                    System.out.println("你被击败了!");
                    break;
                }
                //如果玩家未被击败，则继续游戏
                round++;


            }

            //5.8 玩家胜利，随机恢复血量21-40点，玩家失败则游戏结束
            if (player.isAlive()) {
                int healHP = random.nextInt(21) + 20;
                player.heal(healHP);
                System.out.println("战斗结束，你恢复了" + healHP + "点HP!");
                System.out.println("当前胜利 " + win + "场!");
                System.out.println("---------------------------------------");
            }

            //5.9 玩家每胜利3场，技能增加
            if (player.isAlive() && win > 0 && win % 3 == 0) {
                System.out.println("恭喜你，属性提升!");
                player.maxHP += 30;
                player.attack += 5;
                player.defense += 3;
                System.out.println("最大生命值+30，攻击+5，防御+3");
                System.out.println("当前属性：" + player.show());
            }

            //5.10 询问玩家是否继续
            if (player.isAlive()) {
                System.out.println("是否继续下一场战斗？(y/n)");
                Scanner scanner = new Scanner(System.in);
                String choose = scanner.nextLine();
                //不区分大小写
                if ("y".equalsIgnoreCase(choose)) {
                    count++;
                    continue;
                } else if ("n".equalsIgnoreCase(choose)) {
                    break;
                } else {
                    System.out.println("输入错误，请重新输入!");
                    continue;
                }
            }




        }

        // 6.最终结算
        System.out.println("===================================");
        System.out.println("游戏结束，你共击败了" + win + "个敌人！");
        System.out.println("感谢游玩！");
        //停止虚拟机，结束游戏
        System.exit(0);


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


    //我方攻击敌人回合的方法
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("======玩家回合======");
        System.out.println("请选择技能：");
        System.out.println("1.普通攻击");
        System.out.println("2.全力一击");
        System.out.println("3.生命汲取");
        System.out.println("输入技能编号（1-3）：");

        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();


        switch (choose) {
            default:
                System.out.println("无效输入，将匹配1.普通攻击");
            case 1:
                System.out.println("1.普通攻击");
                //计算伤害
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("你对" + enemy.name + "使用了普通攻击，造成了" + damage1 + "点伤害!");
                //扣血
                enemy.takeDamage(damage1);
                break;
            case 2:
                //hp大于10时，才能使用全力一击
                if (player.HP > 10) {
                    System.out.println("2.全力一击");
                    //消耗10HP
                    player.takeDamage(10);
                    //计算伤害
                    int damage2 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("消耗10HP，你对" + enemy.name + "使用了全力一击，造成了" + damage2 + "点伤害!");
                    //扣血
                    enemy.takeDamage(damage2);
                } else {
                    System.out.println("你的HP小于等于10，无法使用全力一击");
                }
                break;
            case 3:
                if (player.HP > 10) {
                    System.out.println("3.生命汲取");
                    //消耗10HP
                    player.takeDamage(10);
                    //计算回血,随机20-40点
                    Random random = new Random();
                    int healHP = random.nextInt(21)+20;
                    //我方恢复血量
                    player.heal(healHP);
                    //提示我方当前血量
                    System.out.println("消耗10HP，你使用了生命汲取，恢复了" + healHP + "点HP!");
                } else {
                    System.out.println("你的HP小于等于10，无法使用生命汲取");
                }
                break;
        }


    }

    //计算我方对敌人造成伤害的方法
    public int calculateDamage(int attack, int defense) {
        //伤害=攻击-防御
        int damage = attack - defense;
        if (damage < 1) {
            damage = 1;
        }
        return damage;
    }

    //敌人回合方法
    public void enemyTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("======敌人" + enemy.name + "回合======");
        //普通攻击或技能攻击
        String action = "普通攻击";

        //生成随机数0-10
        Random random = new Random();
        int roll = random.nextInt(11);
        if (roll >= 5) {
            action = enemy.skill;
        }
        switch (action) {
            case "普通攻击":
                System.out.println("敌人" + enemy.name + "使用了普通攻击");
                int damage1 = calculateDamage(enemy.attack, player.defense);
                System.out.println("敌人" + enemy.name + "造成了" + damage1 + "点伤害!");
                player.takeDamage(damage1);
                break;
            case "猛击":
                System.out.println("敌人" + enemy.name + "使用了猛击");
                int damage2 = calculateDamage((int) (enemy.attack * 1.5), player.defense);
                System.out.println("敌人" + enemy.name + "造成了" + damage2 + "点伤害!");
                player.takeDamage(damage2);
                break;
            case "快速攻击":
                System.out.println("敌人" + enemy.name + "使用了快速攻击");
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    damage3 += calculateDamage((int) (enemy.attack / 2), player.defense);
                }
                System.out.println("敌人" + enemy.name + "造成了" + damage3 + "点伤害!");
                player.takeDamage(damage3);
                break;
            case "防御姿态":
                System.out.println("敌人" + enemy.name + "使用了防御姿态");
                enemy.defending = true;
                System.out.println("敌人" + enemy.name + "进入了防御姿态");
                break;
            case "火球术":
                System.out.println("敌人" + enemy.name + "使用了火球术");
                int damage4 = calculateDamage((int) (enemy.attack * 1.8), player.defense);
                System.out.println("敌人" + enemy.name + "造成了" + damage4 + "点伤害!");
                player.takeDamage(damage4);
                break;
        }


    }
}
