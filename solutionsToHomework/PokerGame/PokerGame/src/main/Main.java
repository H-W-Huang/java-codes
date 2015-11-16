package main;

import java.util.Scanner;
import pokergame.PokerGame;

/**
 *
 * @author xiaolei
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("输入玩家人数:");
        int numberOfPlayers = input.nextInt();
        System.out.print("输入扑克牌盒数:");
        int numberOfPacks = input.nextInt();
        
        PokerGame game = new PokerGame(numberOfPlayers, numberOfPacks);

        game.go();        
        System.out.println("第1次发牌的结果: ");
        System.out.println(game.getResult());
        
        game.go();        
        System.out.println("第2次发牌的结果: ");
        System.out.println(game.getResult());
    }
}
