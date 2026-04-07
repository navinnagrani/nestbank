package com.nestbank.example;

import java.util.HashMap;
import java.util.Map;

public class Example {
    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key,int value) {
            this.key = key;
            this.value = value;
        }
    }
    private int capacity;
    private Map<Integer, Node> map;
    private Node head;
    private Node tail;

    public Example(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        head = new Node(0,0);
        tail = new Node(0,0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        removeNode(node);
        addToHead(node);

        return node.value;
    }

    public void put(int key,int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;

            removeNode(node);
            addToHead(node);
        } else {
            Node newNode = new Node(key,value);
            if(map.size() == capacity) {
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);
            }
            addToHead(newNode);
            map.put(key,newNode);
        }
    }

    private void removeNode(Node node) {

        node.prev.next = head.next;
        node.next.prev = node.prev;
    }

    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private void printCache() {
        Node curr = head.next;
        while(curr!=tail) {
            System.out.print("["+curr.key+":"+curr.value+"]");
            curr = curr.next;
        }
        System.out.println();
    }

    public int getMostRecentKey() {
        if(head.next ==  tail) {
            return -1;
        }
        return head.next.key;
    }


        public static void main(String[] args) {
        Example cache = new Example(2);
        cache.put(1,2);
        cache.put(2,2);

        System.out.println(cache.get(1));

        cache.put(3,3);

        System.out.println(cache.get(2));

        cache.put(4,4);

        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
}
