/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylinkedlist;

import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class MyLinkList<Item> implements Iterable<Item> {

    private int n; //记录个数
    private Node first;

    private class Node {

        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public int size() {
        return n;
    }

    /**
     * 使用头插法
     *
     * @param item
     */
    public void add(Item item) {
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.next = null;
        } else {
            Node temp = new Node();
            temp.item = item;
            temp.next = first;
            first = temp;
        }

        n++;
    }

    /**
     * 删除第i个元素
     *
     * @return
     */
    public Item delete(int i) throws Exception {
        Item result;
        if (isEmpty() || i == 0 || i > n) {
            throw new Exception("Valid Operation!");
        } else {
            Node temp = first;

            if (i == 1) {
                result = temp.item;
                first = first.next;
            } else {
                for (int j = 0; j < i - 2; j++) {
                    temp = temp.next;
                }
                result = temp.next.item;
                temp.next = temp.next.next;
            }
        }
        n--;
        return result;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverArrayList();
    }

    class ReverArrayList implements Iterator<Item> {

        Node i = first;

        @Override
        public boolean hasNext() {
            return i != null;
        }

        @Override
        public Item next() {
            Item result = i.item;
            result = i.item;
            i = i.next;
            return result;
        }

        @Override
        public void remove() {

        }
    }

    public static void main(String[] args) {
        MyLinkList<Integer> list = new MyLinkList<Integer>();
        try {
            list.add(1);
            list.add(2);
            list.add(5);
            list.add(7);
//            list.delete(4);s
            for (int x : list) {
                System.out.println(">>" + x);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println(list.size());
    }
}
