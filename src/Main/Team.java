package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

class Team {
    private int n;
    private Drone drones[];


    public Team() {
        Scanner inner = new Scanner(System.in);
        System.out.print("Amount of drones:");
        n = inner.nextInt();
        drones = new Drone[n];
        for (int i = 0; i < n; i++) {
            String type;
            do {
                System.out.print("f - fighter\nb - balanced\nd - defender\nType:");
                type = inner.nextLine();
                if ((!type.equals("f")) && (!type.equals("b")) && (!type.equals("d"))) {
                    System.out.print("Wrong type! Pls try again\n\n");
                    continue;
                } else
                    break;
            } while (true);

            String name;
            float hp, dmg;
            System.out.print("Name:");
            name = inner.nextLine();
            System.out.print("hp:");
            hp = inner.nextInt();
            System.out.print("damage:");
            dmg = inner.nextInt();
            if ("f".equals(type)) {
                drones[i] = new Fighter(name, hp, dmg);
            } else if ("b".equals(type)) {
                drones[i] = new DefaultDrone(name, hp, dmg);
            } else if ("d".equals(type)) {
                drones[i] = new Defender(name, hp, dmg);
            }
        }
        sort_priority();
    }

    private void save(Drone attacked, float dmg) throws IOException {
        Files.write(Paths.get("log.txt"), (attacked.droneToCSV() + "\t Get Damage: " + dmg).getBytes(), StandardOpenOption.APPEND);
    }

    private void sort_priority() {
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if ((drones[i].getPrioritet()) < (drones[j].getPrioritet())) {
                    Drone tmp = drones[i];
                    drones[i] = drones[j];
                    drones[j] = tmp;
                }
            }
        }
    }

    public int getAmount() {
        return n;
    }

    public Drone[] getDrones() {
        return drones;
    }

    public void battle(Team team2) throws IOException {
        Files.write(Paths.get("log.txt"), ("Start of Battle\n").getBytes(), StandardOpenOption.APPEND);

        int attackedDroneTeam1 = 0;//атакуєм по черзі всіх дронів. Так як вони посортовані за пріорітетом, то от і порядок за яким атакувати.
        int attackedDroneTeam2 = 0;//Коли дойде до кінця масиву дронів, то відповідно завершити бій
        while ((attackedDroneTeam1 < this.getAmount()) && (attackedDroneTeam2 < team2.getAmount())) {
            //рахуєм урон який наносить команда
            float nowDmgTeam1 = 0, nowDmgTeam2 = 0;
            for (int i = 0; i < this.getAmount(); i++)
                if (this.getDrones()[i].getAlive())//якщо живий, то він наносить урон. динамічно записаний в Дрона
                    nowDmgTeam1 += this.getDrones()[i].getNowDmg();

            for (int i = 0; i < team2.getAmount(); i++)
                if (team2.getDrones()[i].getAlive())//якщо живий, то він наносить урон. динамічно записаний в Дрона
                    nowDmgTeam2 += team2.getDrones()[i].getNowDmg();

            //наносимо урон, і зберігаємо результат
            this.getDrones()[attackedDroneTeam1].reduceHpWithDmg(nowDmgTeam2);
            save(this.getDrones()[attackedDroneTeam1], nowDmgTeam2);
            team2.getDrones()[attackedDroneTeam2].reduceHpWithDmg(nowDmgTeam1);
            save(team2.getDrones()[attackedDroneTeam2], nowDmgTeam1);

            //перевірка кого бити
            if (!this.getDrones()[attackedDroneTeam1].getAlive())//якщо не живий, то переходимо до наступного
                attackedDroneTeam1++;
            if (!team2.getDrones()[attackedDroneTeam2].getAlive())//якщо не живий, то переходимо до наступного
                attackedDroneTeam2++;
        }

        //виводимо результат
        if (attackedDroneTeam1 < this.getAmount()) {
            System.out.print("\nWinner: Defender Team!(Team1)");
            this.showAliveTeam();
        } else if (attackedDroneTeam2 < team2.getAmount()) {
            System.out.print("\nWinner: Attacker Team!(Team2)");
            team2.showAliveTeam();
        } else {
            System.out.print("\nBoth Team died :(");
        }

        Files.write(Paths.get("log.txt"), ("\n\nEnd of Battle\n\n").getBytes(), StandardOpenOption.APPEND);
    }

    public void showAliveTeam() {
        System.out.print("\nP.S. priority==type. \n1-default\n2-fighter\n3-defender\n");
        System.out.print("Team:");
        for (int i = 0; i < n; i++) {
            if (drones[i].getAlive()) {
                drones[i].showDrone();
                System.out.println();
            }
        }
    }
};
