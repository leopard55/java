package com.itheima.algorithm.twothreetree;

// 定义节点类
public class BTree {
class Node {
    int count;// 关键字个数
    int[] key;// 关键字数组
    Node[] child;// 子节点数组
    boolean leaf;// 是否为叶节点

    public Node(int t, boolean leaf) {
        this.count = 0;
        this.leaf = leaf;
        this.key = new int[2 * t - 1];
        this.child = new Node[2 * t];
    }

    public int getKey(int index) {
        return key[index];
    }

    public void setKey(int index, int value) {
        this.key[index] = value;
    }

    public Node getChild(int index) {
        return child[index];
    }

    public void setChild(int index, Node child) {
        this.child[index] = child;
    }
}

private Node root;// 根节点
private int t;// 一个节点最少关键字数

public BTree(int t) {
    this.t = t;
    this.root = new Node(t, true);
}

// 查找指定的关键字
public Node search(int key) {
    return search(root, key);
}

// 查找指定的关键字，递归实现
private Node search(Node node, int key) {
    int i = 0;
    while (i < node.count && key > node.key[i]) {
        i++;
    }
    if (i < node.count && key == node.key[i]) {
        return node;
    }
    if (node.leaf) {
        return null;
    } else {
        return search(node.getChild(i), key);
    }
}

// 插入指定的关键字
public void insert(int key) {
    Node root = this.root;
    if (root.count == 2 * t - 1) {// 根节点已满，需要拆分
        Node newRoot = new Node(t, false);
        newRoot.setChild(0, root);
        splitChild(newRoot, 0, root);
        this.root = newRoot;
        insertNonFull(newRoot, key);
    } else {
        insertNonFull(root, key);
    }
}

// 将指定节点的指定子节点拆分成两个子节点
private void splitChild(Node node, int index, Node child) {
    Node newChild = new Node(t, child.leaf);
    newChild.count = t - 1;
    // 复制子节点的右半部分关键字到新创建的子节点
    for (int i = 0; i < t - 1; i++) {
        newChild.key[i] = child.key[i + t];
    }
    // 如果子节点不是叶节点，则需要复制右半部分子节点到新创建的子节点
    if (!child.leaf) {
        for (int i = 0; i < t; i++) {
            newChild.child[i] = child.child[i + t];
        }
    }
    // 调整原有节点的关键字和子节点，腾出空间插入新创建的子节点
    for (int i = node.count; i > index; i--) {
        node.child[i + 1] = node.child[i];
    }
    node.child[index + 1] = newChild;
    for (int i = node.count - 1; i >= index; i--) {
        node.key[i + 1] = node.key[i];
    }
    node.key[index] = child.key[t - 1];
    child.count = t - 1;
    node.count++;
}

// 插入关键字到非满节点
private void insertNonFull(Node node, int key) {
    int i = node.count - 1;
    if (node.leaf) {// 如果是叶节点，直接插入
        while (i >= 0 && key < node.key[i]) {
            node.key[i + 1] = node.key[i];
            i--;
        }
        node.key[i + 1] = key;
        node.count++;
    } else {// 非叶节点需要选择子节点插入
        while (i >= 0 && key < node.key[i]) {
            i--;
        }
        i++;
        if (node.child[i].count == 2 * t - 1) {// 子节点已满，需要拆分
            splitChild(node, i, node.child[i]);
            if (key > node.key[i]) {// 确定插入到哪个子节点
                i++;
           
