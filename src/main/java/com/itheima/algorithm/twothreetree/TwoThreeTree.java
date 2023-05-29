package com.itheima.algorithm.twothreetree;

public class TwoThreeTree {
    static class Node {
        int[] data = new int[3];
        Node[] child = new Node[4];
        int numChild;
        boolean isLeaf;

        void insertNonFull(int value) {
            int i = numChild - 1;
            if (isLeaf) {
                while (i >= 0 && data[i] > value) {
                    data[i + 1] = data[i];
                    i--;
                }
                data[i + 1] = value;
                numChild++;
            } else {
                while (i >= 0 && data[i] > value) {
                    i--;
                }
                if (child[i + 1].numChild == 3) {
                    splitChild(i + 1, child[i + 1]);
                    if (data[i + 1] < value) {
                        i++;
                    }
                }
                child[i + 1].insertNonFull(value);
            }
        }

        void splitChild(int i, Node node) {
            Node newNode = new Node();
            newNode.isLeaf = node.isLeaf;
            newNode.numChild = 1;
            newNode.data[0] = node.data[2];

            if (!node.isLeaf) {
                newNode.child[0] = node.child[2];
                newNode.child[1] = node.child[3];
            }

            node.data[2] = 0;
            node.numChild--;

            for (int j = numChild; j >= i + 1; j--) {
                child[j + 1] = child[j];
            }
            child[i + 1] = newNode;

            for (int j = numChild - 1; j >= i; j--) {
                data[j + 1] = data[j];
            }
            data[i] = node.data[1];
            numChild++;
        }

        void traverse() {
            int i;
            for (i = 0; i < numChild; i++) {
                if (!isLeaf) {
                    child[i].traverse();
                }
                System.out.print(data[i] + " ");
            }
            if (!isLeaf) {
                child[i].traverse();
            }
        }
    }

    Node root;

    public TwoThreeTree() {
        root = null;
    }

    void insert(int value) {
        if (root == null) {
            root = new Node();
            root.data[0] = value;
            root.numChild = 1;
        } else {
            if (root.numChild == 3) {
                Node newNode = new Node();
                newNode.isLeaf = false;
                newNode.numChild = 0;
                newNode.child[0] = root;
                splitChild(0, root, newNode);
                int i = 0;
                if (newNode.data[0] < value) {
                    i++;
                }
                newNode.child[i].insertNonFull(value);
                root = newNode;
            } else {
                root.insertNonFull(value);
            }
        }
    }

    void splitChild(int i, Node node, Node newNode) {
        Node child = node.child[i];
        newNode.data[newNode.numChild] = node.data[i];
        newNode.numChild++;
        node.data[i] = child.data[1];

        for (int j = 2; j < 3; j++) {
            newNode.data[newNode.numChild] = child.data[j];
            newNode.numChild++;
            child.data[j] = 0;
        }

        if (!child.isLeaf) {
            for (int j = 2; j < 4; j++) {
                newNode.child[newNode.numChild] = child.child[j];
                newNode.numChild++;
                child.child[j] = null;
            }
        }

        child.numChild = 1;
        node.numChild++;
    }

    void traverse() {
        if (root != null) {
            root.traverse();
        }
    }
}
