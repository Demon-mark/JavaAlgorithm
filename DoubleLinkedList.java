/**
 *
 * 不循环双向链表的实现
 * 定义了双向结点，在链表内部将数据包装为双向结点
 * 实现了基本的遍历操作
 * 提供的功能如下：
 *      add     添加
 *      addAll  添加数组
 *      remove  删除
 *      search  查找
 *      modify  修改
 *      insert  插入
 *
 * 需要使用的查找的位置均提供两种查找方式：根据值查找以及根据索引查找（支持负数倒序查找）
 * 如：   remove(T value)为按照给定的值进行删除
 *       remove(int index)为按照给定的索引进行删除
 *
 * @author Amosen
 * @create 2020-06-20 17:27
 */
public class DoubleLinkedList<T> {
    private DoubleNode head;
    private DoubleNode temp;
    private int size = 0;

    public DoubleLinkedList() {
        head = new DoubleNode(null);
        temp = head;

    }

    /**
     * 将给定的数据包装为双向结点
     * @param value 用户给定的数据
     * @return 新创建的双向结点
     */

    private DoubleNode swap(T value) {
        return new DoubleNode<T>(value);
    }

    /**
     * 判断链表是否为空
     * @return
     */

    public boolean isEmpty() {
        return head.next == null;
    }

    /**
     * 添加数据到链表末尾
     * @param value 将用户给定的数据
     */

    public void add(T value) {
        DoubleNode node = swap(value);
        temp.next = node;
        node.pre = temp;
        temp = node;
        size++;
    }

    /**
     * 将数据数组中的数据分别添加到链表末尾
     * @param values 用户给定的数据数组
     */

    public void addAll(T[] values) {
        for(T value: values) {
            add(value);
        }
    }

    /**
     * 返回当前链表有效值的个数
     * @return 当前链表有效值的个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 根据传入的index实施正向查找
     * @param index 用户给定的索引（正值）
     * @return 给定索引所指位置的双向结点
     */
    private DoubleNode searchPositive(int index) {
        int value = 0;
        DoubleNode cur = head.next;
        while(cur != null) {
            if(value == index) {
                return cur;
            }
            value++;
            cur = cur.next;
        }
        return null;
    }

    /**
     * 根据传入的index实施反向查找
     * @param index 用户给定的索引（负值）
     * @return 给定索引位置的双向结点
     */
    private DoubleNode searchNegative(int index) {
        int value = 1;
        DoubleNode cur = temp;
        while(cur != head) {
            if(value == -index) {
                return cur;
            }
            value++;
            cur = cur.pre;
        }
        return null;
    }

    /**
     * 查找功能的调度方法
     * @param index 用户给定的索引（任意整数）
     * @return 指定索引位置的具体数据值
     */
    public T search(int index) {
        if(index >= size && index > 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index >= 0) {
            return (T) searchPositive(index).getValue();
        }
        if(-index > size && index < 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index < 0) {
            return (T) searchNegative(index).getValue();
        }
        return null;
    }

    /**
     * 根据用户给定的值进行查找
     * @param value 用户指定的索引位置（正值）
     * @return 指定值的索引位置
     */
    public int search(T value) {
        DoubleNode searchNode = swap(value);
        int index = 0;
        DoubleNode temp = head.next;
        while(temp != null) {
            if(temp.equals(searchNode)) {
                return index;
            }
            index++;
            temp = temp.next;
        }
        return -1;
    }

    /**
     * 根据用户给定的索引进行正向查询插入
     * @param index 给定的索引（正值）
     * @param destValue 想要插入的值
     */
    private void insertPositive(int index, T destValue) {
        DoubleNode insertNode = swap(destValue);
        DoubleNode cur = searchPositive(index);
        insertNode.pre = cur.pre;
        cur.pre.next = insertNode;
        insertNode.next = cur;
        cur.pre = insertNode;
    }

    /**
     * 根据用户给定的索引进行反向查询插入
     * @param index 给定的索引（负值）
     * @param destValue 想要插入的值
     */
    private void insertNegative(int index, T destValue) {
        DoubleNode insertNode = swap(destValue);
        DoubleNode cur = searchNegative(index);
        insertNode.pre = cur;
        insertNode.next = cur.next;
        cur.next.pre = insertNode;
        cur.next = insertNode;
    }

    /**
     * 插入操作的调度方法
     * @param index 给定的索引位置（任意整数）
     * @param destValue 想要插入的值
     */
    public void insert(int index, T destValue) {
        if(index >= size && index > 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index >= 0) {
            insertPositive(index, destValue);
        }
        if(-index > size && index < 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index < 0) {
            insertNegative(index, destValue);
        }
    }

    /**
     * 根据给出的值进行查询，并在查询到的结点后插入指定值
     * @param existsValue 链表中已经存在的值，根据此值所对应的结点进行查找
     * @param inputValue 想要插入的值
     */
    public void insert(T existsValue, T inputValue) {
        DoubleNode searchNode = swap(existsValue);
        DoubleNode insertNode = swap(inputValue);
        DoubleNode temp = head.next;
        while(temp != null) {
            if(temp.equals(searchNode)) {
                insertNode.pre = temp;
                insertNode.next = temp.next;
                temp.next.pre = insertNode;
                temp.next = insertNode;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 根据用户给定的值进行查找并进行修改
     * @param sourceValue 给定的链表中已经存在的值
     * @param destValue 更改后的目标值
     * @return 返回是否修改成功
     */
    public boolean modify(T sourceValue, T destValue) {
        DoubleNode sourceNode = swap(sourceValue);
        DoubleNode temp = head.next;
        while(temp != null) {
            if(temp.equals(sourceNode)) {
                boolean modify = temp.modify(destValue);
                return modify;
            }
            temp = temp.next;
        }
        return false;
    }

    /**
     * 根据用户给定的索引进行正向查找并修改
     * @param index 给定的索引值（正值）
     * @param destValue 更改后的目标值
     * @return 返回是否修改成功
     */

    private boolean modifyPositive(int index, T destValue) {
        int value = 0;
        DoubleNode cur = head.next;
        while(cur != null) {
            if(value == index) {
                return cur.modify(destValue);
            }
            value++;
            cur = cur.next;
        }
        return false;
    }

    /**
     * 根据用户给定的索引进行反向查找并修改
     * @param index 给定的索引值（负值）
     * @param destValue 更改后的目标值
     * @return 返回是否修改成功
     */
    private boolean modifyNegative(int index, T destValue) {
        int value = 1;
        DoubleNode cur = temp;
        while(cur != head) {
            if(value == -index) {
                return cur.modify(destValue);
            }
            value++;
            cur = cur.pre;
        }
        return false;
    }

    /**
     * 根据用户给定的索引进行修改的调度方法
     * @param index 用户给定的索引值（任意整数）
     * @param destValue 更改后的目标值
     * @return 返回是否修改成功
     */
    public boolean modify(int index, T destValue) {
        if(index >= size && index > 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index >= 0) {
            return modifyPositive(index, destValue);
        }
        if(-index > size && index < 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index < 0) {
            return modifyNegative(index, destValue);
        }
        return false;
    }

    /**
     * 根据用户给定的值进行删除
     * @param value 给定的值
     */
    public void remove(T value) {
        if(value == null) {
            return;
        }
        DoubleNode removeNode = swap(value);
        DoubleNode temp = head.next;
        while(temp != null) {
            if(temp.equals(removeNode)) {
                temp.pre.next = temp.next;
                temp.next.pre = temp.pre;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 根据用户给定的索引值进行删除
     * @param index 给定的索引值（任意整数）
     */
    public void remove(int index) {
        DoubleNode cur = null;
        if(index >= size && index > 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index >= 0) {
            cur = searchPositive(index);
        }
        if(-index > size && index < 0) {
            throw new IndexOutOfBoundsException("The index of " + index + " does not exists");
        }
        if(index < 0) {
            cur = searchNegative(index);
        }
        remove((T) cur.getValue());
    }

    public void show() {
        DoubleNode cur = head.next;
        while(cur != null) {
            System.out.println(cur.getValue());
            cur = cur.next;
        }
    }

    public void reverseShow() {
        DoubleNode cur = temp;
        while(cur.pre != null) {
            System.out.println(cur.getValue());
            cur = cur.pre;
        }
    }
}

class DoubleNode<T> {
    private T value;
    public DoubleNode pre;
    public DoubleNode next;

    public DoubleNode (T value) {
        this.value = value;
        pre = null;
        next = null;
    }

    public T getValue() {
        return this.value;
    }

    public boolean modify(T value) {
        if(value == null) {
            return false;
        }
        this.value = value;
        return true;
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }

        if(! (obj instanceof DoubleNode)) {
            return false;
        }

        DoubleNode doubleNode = (DoubleNode) obj;
        return doubleNode.getValue().equals(this.getValue());
    }
}