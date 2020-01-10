package com.github.metalloid.pagefactory;

import com.github.metalloid.pagefactory.utils.InstanceCreator;
import com.github.metalloid.pagefactory.utils.ListUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MetalloidControlList<T> implements List<T> {
    private List<T> list = new ArrayList<>();
    private WebDriver driver;
    private SearchContext searchContext;
    private By by;

    public MetalloidControlList(WebDriver driver, SearchContext searchContext, By locator) {
        this.driver = driver;
        this.searchContext = searchContext;
        this.by = locator;
    }

    @Override
    public int size() {
        return executeAround(() -> list.size());
    }

    @Override
    public boolean isEmpty() {
        return executeAround(() -> list.isEmpty());
    }

    @Override
    public boolean contains(Object o) {
        return executeAround(() -> list.contains(o));
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return executeAround(x -> list.containsAll(x), c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    private void findElements() {
        for (int i = 0; i < this.searchContext.findElements(by).size(); i++) {
            @SuppressWarnings("unchecked")
            T t = (T) InstanceCreator.instanceOfControl(ListUtils.getListType(getField()), driver, searchContext, by, i);
            this.add(t);
        }
    }

    private Field getField() {
        try {
            return this.getClass().getDeclaredField("list");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private <R> R executeAround(Supplier<R> action) {
        findElements();
        return action.get();
    }

    private <R> boolean executeAround(Predicate<R> action, R r) {
        findElements();
        return action.test(r);
    }
}
