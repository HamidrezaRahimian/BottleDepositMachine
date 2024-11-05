package BottleDepositMachine.Software;



import java.util.Arrays;

public class MyStackArray<E> {
    private int size = 0;
    private Object[] elements;

    public MyStackArray(int initialCapacity) {
        elements = new Object[initialCapacity];
    }

    public void push(E e) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = e;
    }

    @SuppressWarnings("unchecked")
    public E pop() {
        E e = (E) elements[--size];
        elements[size] = null;
        return e;
    }

    public E peek() {
        if (size == 0) {
            return null;
        }
        @SuppressWarnings("unchecked") E e = (E) elements[size - 1];
        return e;
    }

    private void ensureCapacity() {
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int myStackArraySize() {
        return size;
    }

//    @SuppressWarnings("unchecked")
//    public Box giveO(int index) {
//        if (index < 0 || index >= size) {
//            throw new IndexOutOfBoundsException("Invalid index");
//        }
//        Box box = (Box) elements[index];
//        return box;
//    }


}