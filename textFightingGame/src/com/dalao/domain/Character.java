package com.dalao.domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;


    public Character() {
    }

    public Character(String name, int HP, int attack, int defense) {
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    //1.判断当前角色是否存活
    public boolean isAlive() {
        return HP > 0;
    }

    //2.恢复血量，恢复了多少
    public void heal(int amount) {
        HP = amount + HP;
        if (HP > maxHP) {
            HP = maxHP;
        }
    }

    //3.攻击，攻击了多少
    public void takeDamage(int damage) {
        HP = HP - damage;
        if (HP < 0) {
            HP = 0;
        }
    }

    //4.展示人物属性，name hp 攻击 防御
    public String show() {
        return " Name: " + name + " ，当前生命: " + HP + " ，攻击: " + attack + " ，防御: " + defense;
    }


}
