package com.dalao.domain;

public class EnemyCharacter extends Character {

    public String skill;
    public boolean defending;

    public EnemyCharacter() {
        super();
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    //收到伤害
    @Override
    public void takeDamage(int damage) {
        //防御状态伤害减半，一个回合
        if (defending) {
            //damage除以2大于1，取1，小于0，取商
            damage = Math.max(1, damage / 2);
            defending = false;
        }
        //调用父类方法
        super.takeDamage(damage);

    }






}
