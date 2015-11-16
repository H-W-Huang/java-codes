package pokergame;

import java.util.Arrays;
import java.util.Random;

/**
 * 类PokerGame实现了扑克游戏中的发牌
 * <pre>
 * 实现假设：
 *      使用整数0-51代表一付牌的52张扑克牌。
 *      其中：0-12 代表 方块A-K, 13-25 代表 草花A-K, 26-38 代表 红心 A-K, 39-51 代表 黑桃 A-K
 *            花色顺序：黑桃 > 红心 > 草花 > 方块
 *            点数顺序：K > Q > J > 10 > ... > A
 *      这样可以实现时，排序算法较为简单直接。
 * 使用方法：
 *      (1) 创建对象：            PokerGame game = new PokerGame(3);
 *      (2) 发牌或重新发牌：       game.go(); //必须先调用该方法, 才能调用下面方法获得结果
 *      (3) 获取字符串的发牌结果：  String s = game.getResult();
 *      (4) 获取存放发牌结果的数组: int[][] array = game.getArrayResult();
 * </pre>
 *
 * @author 肖磊<Xiaolei@SCAU>
 */
public class PokerGame {

    // 一组用于本类的常量---------------------------------------------------------
    private static final int NUMBER_OF_CARDS = 52;  //牌的总数
    private static final int NUMBER_PER_SUIT = 13;  //每种花色的牌数
    private static final int SPADE = 3;             //黑桃的数字
    private static final int HEART = 2;             //红心的数字
    private static final int CLUB = 1;              //草花的数字
    private static final int DIAMOND = 0;           //方块的数字
    //4种花色对应图案的Unicode编码，依次为方块、草花、红心、黑桃
    private static final String[] suits = {"\u2666", "\u2663", "\u2665", "\u2660"};
    //每种花色13张牌对应的输出形式
    private static final String[] points = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    //本类数据域-----------------------------------------------------------------
    /**
     * <pre>
     * 发好的牌存放于本数组，第1维代表玩家，第2维代表玩家获得的牌。
     * 该数据域经过封装，类外部无法访问该数组。
     * 如果将来考虑在外部获得该数组，可以为其增加一个getter方法。
     * </pre>
     */
    private int[][] hands = null;
    /*
     * 存放游戏玩家人数的数据域，只能由构造方法初始化, 游戏开始后不能改变
     */
    private int numberOfPlayers;
    /*
     * 存放游戏所需的扑克的盒数的数据域, 只能由构造方法初始化, 游戏开始后不能改变
     */
    private int numberOfPacks;
    /*
     * 存放所有牌的数组, 内部使用, 无setter&getter方法
     */
    private int[] cards = null;

    //构造方法-------------------------------------------------------------------
    /**
     * 构造方法，创建对象时需要指明参加游戏玩家的人数和扑克的盒数.
     * <pre>
     * 考虑到发牌游戏必须指定参加人数，因此没有提供无参构造方法.
     * 理论上玩家人数可以是大于0的整数，但是考虑到实际情况，通常人数不多于牌的总数才有意义
     * </pre>
     *
     * @param numberOfPlayers 玩家人数
     * @param numberOfPacks 扑克盒数
     */
    public PokerGame(int numberOfPlayers, int numberOfPacks) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfPacks = numberOfPacks;
        this.cards = new int[PokerGame.NUMBER_OF_CARDS * this.numberOfPacks];
        this.hands = this.createHandsArray();
    }

    // public 方法--------------------------------------------------------------
    /**
     * 公共方法, 实现发牌的全过程
     */
    public void go() {
        this.resetCards(); //重置所有的牌为初始顺序
        this.shuffle(PokerGame.NUMBER_OF_CARDS * this.numberOfPacks / 2);//洗牌
        this.deal();      //发牌
    }

    /**
     * 获得用于存放发牌结果的二维数组。 属于类的公开调用方法，用public修饰。
     *
     * @return 二维数组
     */
    public int[][] getArrayResult() {
        return this.hands;  //直接返回存放结果的数组
    }

    /**
     * 获得用于显示发牌结果的字符串。 属于类的公开调用方法，用public修饰。
     *
     * @return 发牌结果的字符串
     */
    public String getResult() {
        String spliter = "\n-----------------------------------------------\n";
        String result = spliter;
        result += String.format("共%d人参加游戏，发牌结果如下：", this.numberOfPlayers);

        //外层for循环每次迭代产生当前玩家牌的分配情况
        for (int player = 0; player < hands.length; player++) {
            result += String.format("\n第%d个玩家：", player + 1);
            //定义4个花色字符串的目的是如果出现某玩家的某种花色没有分配到牌时，也会产生输出
            String spades = suits[SPADE] + " ";         //当前玩家黑桃的牌字符串
            String hearts = suits[HEART] + " ";         //当前玩家红心的牌字符串
            String clubs = suits[CLUB] + " ";           //当前玩家草花的牌字符串
            String diamonds = suits[DIAMOND] + " ";     //当前玩家方块的牌字符串

            //内层for循环，依次将每张牌附加到花色字符串后面
            for (int card = hands[player].length - 1; card >= 0; card--) {
                int curSuit = hands[player][card] / NUMBER_PER_SUIT; //计算当前牌的花色
                switch (curSuit) {
                    case SPADE:
                        spades += points[hands[player][card] % NUMBER_PER_SUIT] + " ";
                        break;
                    case HEART:
                        hearts += points[hands[player][card] % NUMBER_PER_SUIT] + " ";
                        break;
                    case CLUB:
                        clubs += points[hands[player][card] % NUMBER_PER_SUIT] + " ";
                        break;
                    case DIAMOND:
                        diamonds += points[hands[player][card] % NUMBER_PER_SUIT] + " ";
                        break;
                }
            }
            //下面4语句将花色字符串连接到结果中
            result += "\n\t" + spades;
            result += "\n\t" + hearts;
            result += "\n\t" + clubs;
            result += "\n\t" + diamonds;
        }
        result += spliter;
        return result;
    }

    // private 方法--------------------------------------------------------------
    /**
     * 创建存放每个玩家牌的二维数组hands
     */
    private int[][] createHandsArray() {
        int[][] hands;
        hands = new int[this.numberOfPlayers][];  //创建玩家手牌数组的第一维,每行代表一个玩家
        //计算每个玩家几张牌,注意是整除
        int numberOfCardsPerPlayers = (NUMBER_OF_CARDS * this.numberOfPacks) / this.numberOfPlayers;
        //计算发牌的余数
        int remainder = (NUMBER_OF_CARDS * this.numberOfPacks) % this.numberOfPlayers;
        for (int i = 0; i < this.numberOfPlayers; i++) {
            if (i < remainder) {  //前面remainder个玩家多1张牌
                hands[i] = new int[numberOfCardsPerPlayers + 1];
            } else {
                hands[i] = new int[numberOfCardsPerPlayers];
            }
        }
        return hands;
    }

    /**
     * 重置存放本次游戏使用牌的数组cards为原始顺序,
     */
    private void resetCards() {
        for (int i = 0; i < this.cards.length; i++) {
            this.cards[i] = i % PokerGame.NUMBER_OF_CARDS;
        }
    }

    /**
     * 洗牌方法,采用思路为随机交换, 每次交换随机产生在下标范围内的2个下标, 然后交换这2张牌.
     *
     * @param times 交换的次数
     */
    private void shuffle(int times) {
        Random rand = new Random();
        for (int n = 0; n < times; n++) {
            int i = rand.nextInt(this.cards.length); //产生第1个随机下标
            int j = rand.nextInt(this.cards.length); //产生第2个随机下标
            int temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    /**
     * 发牌方法
     */
    private void deal() {
        //下面循环进行发牌
        for (int i = 0; i < this.cards.length; i++) {
            int player = i % this.numberOfPlayers;      //计算当前牌分给哪个玩家
            int card = i / this.numberOfPlayers;        //计算当前牌分给哪个玩家的第几张牌
            this.hands[player][card] = this.cards[i];   //发一张牌
        }
        //下面循环模拟打牌时按花色和点数理牌
        for (int player = 0; player < hands.length; player++) {
            Arrays.sort(hands[player]);                 //为每个玩家升序理牌
        }
    }

}
