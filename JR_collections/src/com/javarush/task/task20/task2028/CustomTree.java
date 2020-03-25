package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    ArrayList<Entry> entries = new ArrayList<>();
    int length;

    public CustomTree() {
        this.root = new Entry<>("0");
        entries.add(root);
    }

    public String getParent(String s) {
        String result = "null";
        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).elementName.equals(s)) {
                result = entries.get(i).parent.elementName;
            }
        }
        return result;
    }

    public void printTree() {
        for (Entry entry : entries) {
            System.out.println(entry.elementName);
        }
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean add(String elementName) {
        Entry newElement = new Entry(elementName);
        Entry tmp = null;
        for (int i = 0; i < entries.size(); i++) {
            tmp = entries.get(i);
            if (tmp.isAvailableToAddChildren()) {
                if (tmp.availableToAddLeftChildren) {
                    return addNewLeftChild(tmp, newElement);
                } else if (tmp.availableToAddRightChildren) {
                    return addNewRightChild(tmp, newElement);
                }
            } else {
                if (tmp.nodeHasToBeRefreshed()) {
                    tmp.resetChildrenAvailability();
                }
            }
        }
        return add(elementName);
    }

    public Boolean addNewLeftChild(Entry tmp, Entry newElement) {
        tmp.availableToAddLeftChildren = false;
        tmp.leftChild = newElement;
        newElement.parent = tmp;
        entries.add(newElement);
        length++;
        return true;
    }

    public Boolean addNewRightChild(Entry tmp, Entry newElement) {
        tmp.availableToAddRightChildren = false;
        tmp.rightChild = newElement;
        newElement.parent = tmp;
        entries.add(newElement);
        length++;
        return true;
    }

   /* public boolean refreshTree(String elementName) {
        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).nodeHasToBeRefreshed()) {
                entries.get(i).resetChildrenAvailability();
            }
        }
        return add(elementName);
    }*/

    @Override
    public boolean remove(Object o) {
        String nodeName = "";
        Entry deadNode;
        try {
            nodeName = (String) o;
        } catch (Exception ex) {
            throw new UnsupportedOperationException();
        }

        if ((deadNode = removeCheck(nodeName)) == null) {
            return false;
        } else {
            return removeRec(deadNode);
        }
    }

    public Entry removeCheck(String nodeName) {
        Entry deadNode = null;
        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).elementName.equals(nodeName)) {
                deadNode = entries.get(i);
                return deadNode;
            }
        }
        return deadNode;
    }

    public boolean removeRec(Entry deadNode) {
        int index = entries.indexOf(deadNode);
        if (deadNode.nodeHasRealChildren()) {
            if (deadNode.leftChild != null) {
                removeRec(deadNode.leftChild);
            }
            if (deadNode.rightChild != null) {
                removeRec(deadNode.rightChild);
            }
        }
        if (deadNode == deadNode.parent.leftChild) {
            deadNode.parent.leftChild = null;
        } else {
            deadNode.parent.rightChild = null;
        }
        entries.remove(index);
        length--;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    static class Entry<T> implements Serializable {
        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren() {
            return (availableToAddLeftChildren || availableToAddRightChildren);
        }

        public boolean nodeHasChildren() {
            return (!availableToAddLeftChildren || !availableToAddRightChildren);
        }

        public boolean nodeHasRealChildren() {
            return ((this.leftChild != null) || (this.rightChild != null));
        }

        public boolean nodeHasToBeRefreshed() {
            return (!availableToAddLeftChildren && !availableToAddRightChildren && (this.leftChild == null) && (this.rightChild == null));
        }

        public void resetChildrenAvailability() {
            this.availableToAddRightChildren = true;
            this.availableToAddLeftChildren = true;
        }

    }
}
