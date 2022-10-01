package Main;

class Drone {
    protected String name;
    protected float hp = 0, nowHp = 0;
    protected float dmg = 0, nowDmg = 0;
    protected float coefHp = 1, coefDmg = 1;
    protected int prioritet = 1;
    protected boolean alive = true;

    public void reduceHpWithDmg(float nowDmg) {
        nowHp -= nowDmg;
        if (nowHp < 0) {
            alive = false;
            return;
        }
        nowDmg = dmg * (coefDmg * nowHp / hp);// damage what will be used = default damage * coef of class of ship * (now Hp/hp default *is ship broken?*)
    }

    public int getPrioritet() {
        return prioritet;
    }

    public boolean getAlive() {
        return alive;
    }

    public float getNowDmg() {
        return nowDmg;
    }

    public void showDrone() {
        System.out.print("\nName:");
        System.out.print(this.name);
        System.out.print("\nHP:");
        System.out.print(this.hp);
        System.out.print("\nNow HP:");
        System.out.print(this.nowHp);
        System.out.print("\nDamage:");
        System.out.print(this.dmg);
        System.out.print("\nNow Damage:");
        System.out.print(this.nowDmg);
        System.out.print("\nPrioritet:");
        System.out.print(this.prioritet);
    }

    public String droneToCSV()//інформація про дрона в стрічці
    {
        String out = "\n[Name:" + this.name + ";" + "HP:" + this.hp + ";" + "Now HP:" + this.nowHp + ";" + "Damage:" + this.dmg + ";"
                + "Now Damage:" + this.nowDmg + ";" + "Prioritet:" + this.prioritet + "]";
        return out;
    }
};

class Fighter extends Drone {
    Fighter(String name, float hp, float dmg) {
        prioritet = 2;
        coefDmg = 2;
        coefHp = 0.75F;
        this.name = name;
        this.hp = hp * coefHp;
        nowHp = this.hp;
        this.dmg = dmg;
        nowDmg = dmg * (coefDmg * nowHp / this.hp);
    }
};

class Defender extends Drone {
    Defender(String name, float hp, float dmg) {
        prioritet = 3;
        coefDmg = 0.5F;
        coefHp = 2;
        this.name = name;
        this.hp = hp * coefHp;
        nowHp = this.hp;
        this.dmg = dmg;
        nowDmg = dmg * (coefDmg * nowHp / this.hp);
    }
};

class DefaultDrone extends Drone {
    DefaultDrone(String name, float hp, float dmg) {
        prioritet = 1;
        coefDmg = 1;
        coefHp = 1;
        this.name = name;
        this.hp = hp * coefHp;
        nowHp = this.hp;
        this.dmg = dmg;
        nowDmg = dmg * (coefDmg * nowHp / this.hp);
    }
};


