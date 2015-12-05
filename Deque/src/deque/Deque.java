/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deque;

import java.util.Iterator;

/**
 * 双向链表实现的双向队列
 *
 * @author Administrator
 */
public class Deque<Item> implements Iterable<Item> {

    private int n; //记录个数
    private Node theLeft;  //最左
    private Node theRight; //最右

    private class Node {

        Item item;
        Node left;
        Node right;
    }

    public boolean isEmpty() {
        return this.theLeft == null;
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
            theRight = theLeft;
        } else {
            Node temp = new Node();
            temp.item = item;
            temp.right = theLeft;
            theLeft.left = temp;
            theLeft = temp;

        }

        n++;
    }

    public void add2Right(Item item) {
        if (isEmpty()) {
            theRight = new Node();
            theRight.item = item;
            theRight.right = null;
            theRight.left = null;
            theLeft = theRight;
        } else {
            Node temp = new Node();
            temp.item = item;
            temp.left = theRight;
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
            Node temp = theLeft;
            result = temp.item;
            theLeft = theLeft.right;
        }
        n--;
        return result;
    }

    public Item deleteTheRight() throws Exception {
        Item result;
        if (isEmpty()) {
            throw new Exception("Valid Operation!");
        } else {
            Node temp = theRight;
            result = temp.item;
            theRight = theRight.left;
        }
        n--;
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverArrayList();
    }

    class ReverArrayList implements Iterator<Item> {

        Node i = theLeft;

        @Override
        public boolean hasNext() {
            return i != null;
        }

        @Override
        public Item next() {
            Item result = i.item;
            i = i.right;
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
//            list.delete(4);
            for (int x : list) {
                System.out.println(">>" + x);
            }
            System.out.println(list.deleteTheLeft() + " has been removed!");
            System.out.println(list.deleteTheLeft() + " has been removed!");
            System.out.println(list.deleteTheLeft() + " has been removed!");
            System.out.println(list.deleteTheLeft() + " has been removed!");

        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("size: " + list.size());
    }
}
