package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;
    private static final int INITIAL_CAP = 4;
    private static final int NOT_FOUND = -1;

    public List() {
        this.objects = (E[]) new Object[INITIAL_CAP];
        this.size = 0;
    } //default constructor with an initial capacity of 4.

    private int find(E e) {
        for(int i = 0; i < size; i++) {
            if(objects[i].equals(e)) return i;
        }
        return NOT_FOUND;
    }

    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + INITIAL_CAP];
        for(int i = 0; i < objects.length; i++) {
            newObjects[i] = objects[i]; //copies objects to new array
        }
        objects = newObjects;
    }

    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    public void add(E e) {
        //check list to see if object is already there
        if(!contains(e)) {
            if(size >= objects.length) {
                grow(); //grows the list if the capacity is exceeded
            }
            objects[size] = e; //add new object to the list
            size++;
        }
    }

    public void remove(E e) {
        int index = find(e);
        if(index != NOT_FOUND) {
            //shift appointments to the left to fill in gap
            for(int i = index; i < (size -1); i++) {
                objects[i] = objects[i+1]; //next object
            }
            objects[size - 1] = null; //clear last element
            size--; //reduce size by 1;
        }
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator<E>();
    }

    public E get(int index) {
        return objects[index];
    } //return the object at the index

    public void set(int index, E e) {
        objects[index] = e;
    } //put object e at the index

    public int indexOf(E e) {
        return find(e);
    } //return the index of the object or return -1

    private class ListIterator<E> implements Iterator<E> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return (currentIndex < size);
        }//return false if it’s empty or end of list

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            return (E) objects[currentIndex++];
        } //return the next object in the list
    }
}