/**
 *
 * 简单单链表的实现
 * 利用泛型实现各种对象的添加(add addAll)、删除(remove, removeAll)、查找(search)、更改(modify)操作
 *
 * @author Amosen
 * @create 2020-06-17 20:44
 */

public class SingleLinkedList<T> {
    private Node head = new Node(null);
    private Node end = head;

    private Node swap(T value) {
        return new Node(value);
    }

    public T search(T value) {
        Node n = swap(value);
        Node temp = head;
        while(temp != null) {
            if(n.equals(temp)){
                return (T) temp.getValue();
            }
            temp = temp.next;
        }
        return null;
    }

    public void add(T value) throws ElementHasExistsException {
        Node n = new Node(value);
        if(search(value) == null) {
            end.next = n;
            end = n;
            return;
        }
        throw new ElementHasExistsException();
    }

    public void addAll(T[] values) {
        for(T value: values) {
            try {
                add(value);
            } catch (ElementHasExistsException e) {
                continue;
            }
        }
    }

    private Node searchBeforeNode(Node destNode) {
        Node temp = head;
        while(temp.next != null) {
            if(destNode.equals(temp.next)){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    private void change(Node isSearched, Node destNode) {
        destNode.next = isSearched.next.next;
        isSearched.next = destNode;
    }

    public boolean modify(T sourceValue, T destValue) {
        Node sourceNode = swap(sourceValue);
        Node destNode = swap(destValue);
        Node isSearched = searchBeforeNode(sourceNode);
        if(isSearched != null) {
            if(searchBeforeNode(destNode) == null) {
                change(isSearched,destNode);
                return true;
            }
        }
        return false;
    }

    public boolean remove(T value) {
        Node searchNode = swap(value);
        Node isSearched = searchBeforeNode(searchNode);
        if(isSearched == null) {
            return false;
        }
        isSearched.next = isSearched.next.next;
        return true;
    }

    public void removeAll(T[] values) {
        for(T value: values) {
            remove(value);
        }
    }

    public void clear() {
        head = new Node(null);
    }

    public void show() {
        Node temp = head;
        while(temp.next != null) {
            temp.next.show();
            temp = temp.next;
        }
    }

}

class Node<T> {
    private T value;
    public Node<T> next = null;

    public Node(T obj) {
        this.value = obj;
    }

    public void show() {
        System.out.println(value);
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Node)) {
            return false;
        }
        Node n = (Node) obj;
        return value.equals(n.value);
    }

    public T getValue() {
        return this.value;
    }
}

class ElementHasExistsException extends Exception {
    public String toString() {
        return "Element has been existed in the list";
    }
}