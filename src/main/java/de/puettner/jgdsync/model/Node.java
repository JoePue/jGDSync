package de.puettner.jgdsync.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Source:
 *
 * @param <T>
 */
public class Node<T> {
    private List<Node<T>> children = null;
    private Node<T> parent = null;
    private T data = null;

    public Node(T data, boolean isFolder) {
        this.data = data;
        if (isFolder) {
            this.children = new ArrayList<Node<T>>();
        }
    }

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void addChild(T data) {
        Node<T> child = new Node<T>(data, false);
        child.setParent(this);
        this.children.add(child);
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return (this.children == null);
    }

    public void removeParent() {
        this.parent = null;
    }

    @Override
    public String toString() {
        return (this.data == null ? "null" : this.data.toString());
    }

}
