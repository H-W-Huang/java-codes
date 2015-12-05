/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise.EX17_9;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 双向链表实现的双向队列 12.04 改为可循环读取的列表
 *
 * @author Administrator
 */
public class Deque<Item> implements Iterable<Item>, Serializable {

    private int n; //记录个数
    private Node theLeft;  //最左
    private Node theRight; //最右
    private Node current;  //默认在第一位，每次添加元素后归位

    /**
     * @return the theLeft
     */
    public Node getTheLeft() {
        current = theLeft;
        return current;
    }

    /**
     * @return the theRight
     */
    public Node getTheRight() {
        current = theRight;
        return current;
    }

    public Node getNext() {
        if (current.right == null) {
            current = theLeft;
        } else {
            current = current.getRight();
        }
        return current;
    }

    public Node getPrevious() {
        if (current.left == null) {
            current = theRight;
        } else {
            current = current.getLeft();
        }
        return current;
    }

    protected class Node implements Serializable {

        private Item item;
        private Node left;
        private Node right;

        /**
         * @return the left
         */
        public Node getLeft() {
            return left;
        }

        /**
         * @return the right
         */
        public Node getRight() {
            return right;
        }

        /**
         * @return the item
         */
        public Item getItem() {
            return item;
        }
    }

    public boolean isEmpty() {
        return this.getTheLeft() == null;
    }

    public int size() {
        return n;
    }

    /**
     * 使用头插法
     *
     * @param item
     */
    public void add2Left(Item item) {
        if (isEmpty()) {
            theLeft = new Node();
            theLeft.item = item;
            theLeft.right = null;
            theLeft.left = null;
            theRight = getTheLeft();
            current = getTheLeft();
        } else {
            Node temp = new Node();
            temp.item = item;
            temp.right = getTheLeft();
            theLeft.left = temp;
            theLeft = temp;
            current = theLeft;
        }

        n++;
    }

    public void add2Right(Item item) {
        if (isEmpty()) {
            theRight = new Node();
            theRight.item = item;
            theRight.right = null;
            theRight.left = null;
            theLeft = getTheRight();
            current = getTheLeft();
        } else {
            Node temp = new Node();
            temp.item = item;
            temp.left = getTheRight();
            theRight.right = temp;
            theRight = temp;
        }
        n++;
    }

    /**
     * 删除第i个元素
     *
     * @return
     */
    public Item deleteTheLeft() throws Exception {
        Item result;
        if (isEmpty()) {
            throw new Exception("Valid Operation!");
        } else {
            Node temp = getTheLeft();
            result = temp.getItem();
            theLeft = getTheLeft().getRight();
        }
        n--;
        return result;
    }

    public Item deleteTheRight() throws Exception {
        Item result;
        if (isEmpty()) {
            throw new Exception("Valid Operation!");
        } else {
            Node temp = getTheRight();
            result = temp.getItem();
            theRight = getTheRight().getLeft();
        }
        n--;
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverArrayList();
    }

    class ReverArrayList implements Iterator<Item> {

        Node i = getTheLeft();

        @Override
        public boolean hasNext() {
            return i != null;
        }

        @Override
        public Item next() {
            Item result = i.getItem();
            i = i.getRight();
            return result;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> list = new Deque<Integer>();
        try {
            list.add2Left(1);
            list.add2Right(2);
            list.add2Left(5);
            list.add2Right(7);
//            5 1 2 7 
//            list.delete(4);
            System.out.println("遍历如下：");
            for (int x : list) {
                System.out.println(">>" + x);
            }

//            System.out.println(list.deleteTheLeft() + " has been removed!");
//            System.out.println(list.deleteTheLeft() + " has been removed!");
//            System.out.println(list.deleteTheLeft() + " has been removed!");
//            System.out.println(list.deleteTheLeft() + " has been removed!");
            System.out.println("10次getNext");
            for (int i = 0; i < 10; i++) {
                System.out.println(list.getNext().item);
            }

            System.out.println("10次gerPrevious()");
            for (int i = 0; i < 10; i++) {
                System.out.println(list.getPrevious().item);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("size: " + list.size());
    }
}
