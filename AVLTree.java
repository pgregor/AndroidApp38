package com.example.androidproject;

import java.util.ArrayList;

public class AVLTree<T extends ID> {
    public final T value;
    public AVLTree<T> leftNode;
    public AVLTree<T> rightNode;

    public AVLTree() {
        value = null;
    }

    public AVLTree(T value) {
        this.value = value;
        this.leftNode = new EmptyAVL<>();
        this.rightNode = new EmptyAVL<>();
    }

    public AVLTree(T value, AVLTree<T> leftNode, AVLTree<T> rightNode) {
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * @return balance factor of the current node.
     */
    public int getBalanceFactor() {
        return leftNode.getHeight() - rightNode.getHeight();
    }

    public int getHeight() {
        return 1+Math.max(leftNode.getHeight(),rightNode.getHeight());
    }

    public T get(String id) {
        if (value.getID().compareTo(id)>0) return leftNode.get(id);
        if (value.getID().compareTo(id)<0) return rightNode.get(id);
        return value;
    }

    public AVLTree<T> insert(T element) {
        if (element == null)
            throw new IllegalArgumentException("Input cannot be null");
        AVLTree<T> newTree;
        if (element.getID().compareTo(value.getID())>0) {
            AVLTree<T> newRightNode = rightNode.insert(element);
            if (newRightNode.getBalanceFactor()>0) newRightNode = newRightNode.rightRotate();
            newTree = new AVLTree<>(value,leftNode,newRightNode);
        } else if (element.getID().compareTo(value.getID())<0) {
            AVLTree<T> newLeftNode = leftNode.insert(element);
            if (newLeftNode.getBalanceFactor()<0) newLeftNode = newLeftNode.leftRotate();
            newTree = new AVLTree<>(value,newLeftNode,rightNode);
        } else {
            return this;
        }
        while (Math.abs(newTree.getBalanceFactor())>1) {
            if (newTree.getBalanceFactor()>1) newTree = newTree.rightRotate();
            else newTree = newTree.leftRotate();
        }
        return newTree;
    }

    public AVLTree<T> remove(String id) {
        AVLTree<T> newTree;
        if (value.getID().equals(id)) {
            if (leftNode.getHeight()==-1&&rightNode.getHeight()==-1) newTree = new EmptyAVL<>();
            else if (leftNode.getHeight()==-1) newTree = rightNode;
            else if (rightNode.getHeight()==-1) newTree = leftNode;
            else {
                AVLTree<T> replacement = rightNode;
                while (replacement.leftNode.getHeight()!=-1) {
                    replacement = replacement.leftNode;
                }
                newTree = new AVLTree<>(replacement.value,leftNode,rightNode.remove(
                        replacement.value.getID()));
            }
        } else if (id.compareTo(value.getID())>0) {
            newTree = new AVLTree<>(value,leftNode,rightNode.remove(id));
        } else {
            newTree = new AVLTree<>(value,leftNode.remove(id),rightNode);
        }
        while (Math.abs(newTree.getBalanceFactor())>1) {
            if (newTree.getBalanceFactor()>1) newTree = newTree.rightRotate();
            else newTree = newTree.leftRotate();
        }
        return newTree;
    }

    /**
     * Conducts a left rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> leftRotate() {
        AVLTree<T> newParent = this.rightNode;
        AVLTree<T> newRightOfCurrent = newParent.leftNode;
        AVLTree<T> newLeftNode = new AVLTree<>(value,this.leftNode,newRightOfCurrent);

        return new AVLTree<>(newParent.value,newLeftNode,newParent.rightNode);
    }

    /**
     * Conducts a right rotation on the current node.
     *
     * @return the new 'current' or 'top' node after rotation.
     */
    public AVLTree<T> rightRotate() {
        AVLTree<T> newParent = this.leftNode;
        AVLTree<T> newLeftOfCurrent = newParent.rightNode;
        AVLTree<T> newRightNode = new AVLTree<>(value,newLeftOfCurrent,this.rightNode);

        return new AVLTree<>(newParent.value,newParent.leftNode,newRightNode);
    }

    public ArrayList<T> asList() {
        ArrayList<T> out = leftNode.asList();
        out.add(value);
        out.addAll(rightNode.asList());
        return out;
    }

    @Override
    public String toString() {
        return "{" + value +
                ", " + leftNode +
                ", " + rightNode +
                '}';
    }

    public static class EmptyAVL<T extends ID> extends AVLTree<T> {

        @Override
        public AVLTree<T> insert(T element) {
            return new AVLTree<>(element);
        }

        @Override
        public AVLTree<T> remove(String id) {
            return this;
        }

        @Override
        public T get(String id) {
            return null;
        }

        @Override
        public int getHeight() {
            return -1;
        }

        @Override
        public String toString() {
            return "{}";
        }

        @Override
        public int getBalanceFactor() {
            return 0;
        }

        @Override
        public ArrayList<T> asList() {
            return new ArrayList<>();
        }
    }
}
