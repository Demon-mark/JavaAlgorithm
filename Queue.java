import java.util.Scanner;

/**
 * @author Amosen
 * @create 2020-06-14 8:12
 */
public class Queue<T> {

    private int maxSize;
    private int head;
    private int end;
    private Object[] values;

    public Queue() {
        maxSize = 16;
        values = new Object[16];
    }

    public Queue(int maxSize) {
        this.maxSize = maxSize;
        values = new Object[maxSize];
    }

    public boolean isEmpty() {
        return head == end;
    }

    public boolean isFull() {
        return (end + 1) % maxSize == head;
    }

    public boolean put(T t) throws QueueFullException {
        if (isFull()) {
            throw new QueueFullException("队列已满");
        }
        values[end] = t;
        end = (end + 1) % maxSize;
        return true;
    }

    public T get() throws QueueEmptyException {
        if (isEmpty()) {
            throw new QueueEmptyException("队列为空");
        }
        T temp = (T) values[head];
        head = (head + 1) % maxSize;
        return temp;
    }

    public void show() {
        int i = head;
        String str = "Queue{";
        while(i != end) {
            str += values[i] + ", ";
            i = (i + 1) % maxSize;
        }
        str += "}";
        System.out.println(str);
    }
}

class QueueFullException extends Exception {
    static final long serialVersionUID = 999999999L;

    public QueueFullException() {

    }

    public QueueFullException(String msg) {
        super(msg);
    }
}

class QueueEmptyException extends Exception {
    static final long serialVersionUID = 111111111L;

    public QueueEmptyException() {

    }

    public QueueEmptyException(String msg) {
        super(msg);
    }
}

class QueueTest {
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<Integer>(8);

        System.out.println("e 退出\n"+
                "s 展示\n" +
                "p put\n" +
                "g get\n");

        Scanner scan = new Scanner(System.in);

        boolean flag = true;
        while(flag) {
            char chosen = scan.next().charAt(0);
            switch (chosen) {
                case 'e':
                    flag = false;
                    break;
                case 's':
                    queue.show();
                    break;
                case 'p':
                    int value = scan.nextInt();
                    try {
                        queue.put(value);
                    } catch (QueueFullException e) {
                        System.out.println(e.getMessage());
                    }finally {
                        break;
                    }
                case 'g':
                    try {
                        int v = queue.get();
                        System.out.println(v);
                    } catch (QueueEmptyException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        break;
                    }
            }
        }

    }
}