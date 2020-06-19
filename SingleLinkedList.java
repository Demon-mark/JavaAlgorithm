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

    public int getLength() {
        Node temp = head.next;
        int length = 0;
        while(temp != null) {
            length++;
            temp = temp.next;
        }
        return length;
    }

    private Node searchPositive(int index) {
        Node temp = head.next;
        int value = 0;
        while(temp != null) {
            value++;
            if(value == index) {
                break;
            }
            temp = temp.next;
        }
        return temp;
    }

    private Node searchNegative(int index) {
        Node tempAhead = head.next;
        Node tempBehind = head.next;

        int value = 0;
        while(tempAhead != null) {
            value++;
            tempAhead = tempAhead.next;
            if(value == -index + 2) {
                value--;
                tempBehind = tempBehind.next;
            }
        }
        return tempBehind;
    }

    public T search(int index) {

        if(getLength() == 0) {
            return null;
        }

        if(Math.abs(index) > getLength()) {
            return null;
        }

        if(index > 0 && index > getLength() - 1) {
            return null;
        }

        if(index >= 0) {
            Node pn = searchPositive(index);
            return (T) pn.next.getValue();
        }

        if(index < 0) {
            Node nn = searchNegative(index);
            return (T) nn.next.getValue();
        }
        return null;
    }

    public boolean insert(int index, T value) {
        if(index > getLength() - 1 || Math.abs(index) > getLength()) {
            throw new IndexOutOfBoundsException("The List doesn't has the index of " + index);
        }

        if(search(value) != null) {
            return false;
        }

        Node insertNode = swap(value);
        if(index >= 0) {
            Node np = searchPositive(index);
            insertNode.next = np.next;
            np.next = insertNode;
            return true;
        }

        if(index < 0) {
            Node nn = searchNegative(index);
            insertNode.next = nn.next;
            nn.next = insertNode;
            return true;
        }
        return false;
    }

    public void reverse() {
        if(getLength() == 0 || getLength() == 1) {
            return;
        }
        Node reverseNode = new Node(null);
        Node temp = head.next;
        Node cur = reverseNode.next;
        while(temp != null) {
            head.next = temp.next;
            temp.next = cur;
            cur = temp;
            reverseNode.next = cur;
            temp = head.next;
        }
        head.next = reverseNode.next;
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