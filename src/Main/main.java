package Main;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class main {

    private static final int MAX_AMOUNT_OF_TEAM = 10;

    public static void main(String[] args) throws IOException {
        Team[] team = new Team[MAX_AMOUNT_OF_TEAM];
        Scanner inner = new Scanner(System.in);

        int whatToDo, amountOfTeam = 0;
        while (true) {
            System.out.print("\nwhat to do:\n1 - create team\n2 - show team\n3 - battle between team\n4 - show logs\n5 - out\n");
            whatToDo = inner.nextInt();
            if (whatToDo == 5) {
                break;
            } else if (whatToDo == 1) {
                team[amountOfTeam] = new Team();
                amountOfTeam++;
            } else if (whatToDo == 2) {
                while (true) {
                    System.out.print("\nHere is " + amountOfTeam + " teams\nWhich show?\n");
                    int show = 0;
                    show = inner.nextInt();
                    if (show <= amountOfTeam && show > 0) {
                        team[show - 1].showAliveTeam();
                        break;
                    }
                    System.out.print("\nNo Team here, try again");
                }

            } else if (whatToDo == 3) {
                int defender, attacker;
                while (true) {//chose team to battle
                    System.out.print("\nDefender team:");
                    defender = inner.nextInt();
                    defender--;
                    System.out.print("Attacker team:");
                    attacker = inner.nextInt();
                    attacker--;
                    if (attacker >= amountOfTeam) {
                        System.out.print("Wrong Attacker!");
                        continue;
                    } else if (defender >= amountOfTeam) {
                        System.out.print("Wrong Defender!");
                        continue;
                    } else if (defender == attacker) {
                        System.out.print("Dont hurt yourself!");
                        continue;
                    }
                    break;
                }
                team[defender].battle(team[attacker]);

            } else if (whatToDo == 4) {
                String out = new String(Files.readAllBytes(Paths.get("log.txt")), StandardCharsets.UTF_8);
                System.out.print(out);
            } else {
                System.out.print("Wrong Case");
            }

        }
    }
}
