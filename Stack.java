import org.omg.CORBA.Object;

/**
 *
 * 利用数组模拟栈的基本实现
 * 提供添加、弹出、展示、判断是否为空或满的功能
 *
 * @author Amosen
 * @create 2020-06-20 16:11
 */
public class Stack<T> {
    private T[] values;
    private int maxSize;
    private int dataNumber;
    private static final int DEFAULT_SIZE = 16;

    public Stack() {
        this.maxSize = DEFAULT_SIZE;
        values = (T[]) new Object[DEFAULT_SIZE];
        dataNumber = 0;
    }

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        values = (T[]) new java.lang.Object[maxSize];
        dataNumber = 0;
    }

    public void add(T value) throws StackFullException {
        if(isFull()){
            throw new StackFullException();
        }
        values[dataNumber] = value;
        dataNumber++;
    }

    public boolean isEmpty() {
        return dataNumber == 0;
    }

    public boolean isFull() {
        return dataNumber == maxSize;
    }

    public T get() throws StackEmptyException {
        if(isEmpty()) {
            throw new StackEmptyException();
        }
        return values[--dataNumber];
    }

    public void show() {
        System.out.print("Stack[");
        for(int i = 0; i < dataNumber; i++) {
            System.out.print(values[i] + ", ");
        }
        System.out.println("]");
    }
}

class StackFullException extends Exception {
    public String toString() {
        return "The stack has been full already";
    }
}

class StackEmptyException extends Exception {
    public String toString() {
        return "The stack is empty now";
    }
}