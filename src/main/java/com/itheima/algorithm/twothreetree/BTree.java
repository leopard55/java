package com.itheima.algorithm.twothreetree;

public class BTree {

    private Node root;
    private final int t;

    public BTree(int t) {
        this.t = t;
        this.root = new Node(t, true);
    }

    // 查找关键字
    public boolean search(int key) {
        return root != null && root.search(key);
    }

    // 插入关键字
    public void insert(int key) {
        if (root.isFull()) {
            Node newRoot = new Node(t, false);
            newRoot.setChild(0, root);
            newRoot.splitChild(0, root);
            root = newRoot;
        }
        root.insertNonFull(key);
    }

    // 删除关键字
    public void remove(int key) {
        if (root == null) {
            return;
        }
        root.remove(key);
        if (root.getKeyCount() == 0) {
            if (root.isLeaf()) {
                root = null;
            } else {
                root = root.getChild(0);
            }
        }
    }

    // 打印树
    public void print() {
        if (root != null) {
            root.print("");
        }
    }

    // 节点类
    private class Node {
        private int[] keys;
        private int keyCount;
        private Node[] children;
        private boolean leaf;
        private final int t;

        public Node(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.keys = new int[2 * t - 1];
            this.children = new Node[2 * t];
        }

        public int getKeyCount() {
            return keyCount;
        }

        public int getKey(int index) {
            return keys[index];
        }

        public Node getChild(int index) {
            return children[index];
        }

        public void setChild(int index, Node node) {
            children[index] = node;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public boolean isFull() {
            return keyCount == 2 * t - 1;
        }

        public void insertNonFull(int key) {
            int index = keyCount - 1;
            if (isLeaf()) {
                while (index >= 0 && key < keys[index]) {
                    keys[index + 1] = keys[index];
                    index--;
                }
                keys[index + 1] = key;
                keyCount++;
            } else {
                while (index >= 0 && key < keys[index]) {
                    index--;
                }
                if (children[index + 1].isFull()) {
                    splitChild(index + 1, children[index + 1]);
                    if (key > keys[index + 1]) {
                        index++;
                    }
                }
                children[index + 1].insertNonFull(key);
            }
        }

        public void splitChild(int index, Node node) {
            Node newNode = new Node(t, node.isLeaf());
            newNode.keyCount = t - 1;
            for (int i = 0; i < t - 1; i++) {
                newNode.keys[i] = node.keys[i + t];
            }
            if (!node.isLeaf()) {
                for (int i = 0; i < t; i++) {
                    newNode.children[i] = node.children[i + t];
                }
            }
            node.keyCount = t - 1;
            for (int i = keyCount; i >= index + 1; i--) {
                children[i + 1] = children[i];
            }
            children[index + 1] = newNode;
            for (int i = keyCount - 1; i >= index; i--) {
                keys[i + 1] = keys[i];
            }
            keys[index] = node.keys[t - 1];
            keyCount++;
        }

        public boolean search(int key) {
            int index = 0;
            while (index < keyCount && key > keys[index]) {
                index++;
            }
            if (index < keyCount && key == keys[index]) {
                return true;
            }
            return leaf ? false : children[index].search(key);
        }

        public void remove(int key) {
            int index = 0;
            while (index < keyCount && key > keys[index]) {
                index++;
            }
            if (index < keyCount && key == keys[index]) {
                if (leaf) {
                    removeFromLeaf(index);
                } else {
                    removeFromNonLeaf(index);
                }
            } else {
                if (leaf) {
                    return;
                }
                boolean flag = index == keyCount;
                if (children[index].getKeyCount() < t) {
                    fill(index);
                }
                if (flag && index > keyCount) {
                    children[index - 1].remove(key);
                } else {
                    children[index].remove(key);
                }
            }
        }

 