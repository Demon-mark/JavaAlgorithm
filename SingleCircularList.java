/**
 * 单向环形列表的实现
 * 提供Josephu方法解决约瑟夫环问题
 * @author Amosen
 * @create 2020-06-20 22:20
 */
public class SingleCircularList<T> {
    private Node head  = new Node(null);
    private Node pointer = head;
    private int size = 0;
    private Node cur = head.next;
    private Node beforeCur = head;

    private Node swap(T value) {
        return new Node(value);
    }

    private void initCur() {
        cur = head.next;
        beforeCur = head;
    }

    public void next() {
        if(head.next == null) {
            return;
        }
        if(cur == null) {
            initCur();
        }
        beforeCur = cur;
        cur = cur.next;
    }

    public int getSize() {
        return this.size;
    }

    public void remove() {
        beforeCur.next = beforeCur.next.next;
        cur = beforeCur.next;
        size--;
    }

    public T getCurrentValue() {
        if(cur == null) {
            initCur();
        }
        return (T) cur.getValue();
    }

    public void add(T value) {
        Node addNode = swap(value);
        pointer.next = addNode;
        pointer = addNode;
        addNode.next = head.next;
        size++;
    }

    public void addAll(T[] values) {
        for(T value: values) {
            add(value);
        }
    }

    public int search(T value) {
        Node temp = head.next;
        int index = 0;
        while(temp.next != head.next) {
            if(temp.getValue().equals(value)) {
                return index;
            }
            index++;
            temp = temp.next;
        }
        if(temp.getValue().equals(value)) {
            return index;
        }
        return -1;
    }

    public void remove(T value) {
        Node temp = head.next;
        while(temp.next != head.next) {
            if(temp.next.getValue().equals(value)) {
                temp.next = temp.next.next;
                size--;
                break;
            }
            temp = temp.next;
        }
        if(temp.next.getValue().equals(value)) {
            head.next = temp.next.next;
            temp.next = temp.next.next;
        }
    }

    public static String Josephu(int totalNumber, int FirstNumber, int removeNumber) {
        SingleCircularList<Integer> scl = new SingleCircularList<>();
        String result = "";
        for(int i = 1; i <= totalNumber; i++) {
            scl.add(i);
        }
        for(int initIndex = 0; initIndex + 1 < FirstNumber % totalNumber; initIndex++) {
            scl.next();
        }
        for(int i = 1; scl.getSize() > 1; i++) {
            if(i % removeNumber == 0) {
                result += scl.getCurrentValue().toString();
                scl.remove();
                continue;
            }
            scl.next();
        }
        result += scl.getCurrentValue().toString();
        return result;
    }

    public void show() {
        Node temp = head.next;
        while(temp.next != head.next) {
            System.out.println(temp.getValue());
            temp = temp.next;
        }
        System.out.println(temp.getValue());
    }
}
