package com.itsrv23.hw215.mysimlearraylist;

import java.util.Arrays;
import java.util.NoSuchElementException;


// Не понял из задания, почему нельзя быть Null. И как тогда считать size
// Взял дефолтное значение "" вместо null
public class MyArrayList implements StringList {
    private int capacity = 16;
    private int size = 0;
    private String[] data;


    public MyArrayList() {
        data = new String[capacity];
        // реализация условия, не хранить null
        Arrays.fill(data, "");
    }

    public MyArrayList(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
            data = new String[this.capacity];
            // реализация условия, не хранить null
            Arrays.fill(data, "");
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
    }

    // Добавление элемента.
    // Вернуть добавленный элемент
    // в качестве результата выполнения.
    @Override
    public String add(String item) {
        itemCheckForAdd(item);
        growIfNeed();
        data[size++] = item;
        return item;
    }

    // Добавление элемента на определенную позицию списка.
    // Если выходит за пределы фактического количества элементов или массива, выбросить исключение.
    // Вернуть добавленный элемент в качестве результата выполнения.
    @Override
    public String add(int index, String item) {
        checkIndexRange(index);
        growIfNeed();
        System.arraycopy(data, index, data, index + 1, capacity - index - 1);
        data[index] = item;
        size++;
        return item;
    }


    // Установить элемент на определенную позицию, затерев существующий.
    // Выбросить исключение, если индекс больше фактического количества элементов или выходит за пределы массива.
    @Override
    public String set(int index, String item) {
        checkItemIsNull(item);
        checkIndexRange(index);
        data[index] = item;
        return item;
    }

    // Удаление элемента. Вернуть удаленный элемент или исключение, если подобный элемент отсутствует в списке.
    @Override
    public String remove(String item) {
        checkItemIsNull(item);
        int index = indexOf(item);
        if (index >= 0) {
            return remove(index);
        } else {
            throw new NoSuchElementException("Element is not found: " + item);
        }
    }

    // Удаление элемента по индексу.
    // Вернуть удаленный элемент или исключение, если подобный элемент отсутствует в списке.
    @Override
    public String remove(int index) {
        checkIndexRange(index);
        String item = data[index];
        System.arraycopy(data, index + 1, data, index, capacity - index - 1);
        size--;
        return item;
    }

    // Проверка на существование элемента.
    // Вернуть true/false;
    @Override
    public boolean contains(String item) {
        checkItemIsNull(item);
        int index = indexOf(item);
        return index != -1;
    }


    // Поиск элемента.  Вернуть индекс элемента или -1 в случае отсутствия.
    @Override
    public int indexOf(String item) {
        checkItemIsNull(item);
        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    // Поиск элемента с конца.
    // Вернуть индекс элемента или -1 в случае отсутствия.
    @Override
    public int lastIndexOf(String item) {
        checkItemIsNull(item);
        for (int i = data.length - 1; i >= 0; i--) {
            if (data[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    // Получить элемент по индексу.
    // Вернуть элемент или исключение, если выходит за рамки фактического количества элементов.
    @Override
    public String get(int index) {
        // Подразумеваю что тут ошибка, или мое недопонимание в описании
        // Фактическое количество элементов это size, все остальные элементы по умолчанию в массиве строк будут Null
        // А значит, не существуют. А мы можем добавить элемент на индекс, минуя добавление по порядку и
        // инкремента size. Что будет выбрасывать лишний раз эксепшины.
        // Я бы написал: "если выходит за рамки массива"
        checkIndexRange(index);
        return data[index];
    }

    // Сравнить текущий список с другим.
    // Вернуть true/false или исключение, если передан null.
    @Override
    public boolean equals(StringList otherList) {
        if (otherList == null) {
            throw new NullPointerException("com.itsrv23.hw215.mysimlearraylist.StringList is not be Null");
        }
        if (size != otherList.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!data[i].equals(otherList.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Вернуть фактическое количество элементов.
    @Override
    public int size() {
        return size;
    }

    // Вернуть true, если элементов в списке нет, иначе false.
    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    // Удалить все элементы из списка.
    @Override
    public void clear() {
        for (int i = 0; i < data.length; i++) {
            data[i] = "";
            size = 0;
        }
    }

    // Создать новый массив из строк в списке и вернуть его.
    @Override
    public String[] toArray() {
        return Arrays.copyOf(data, size);
    }

    // Для отладки
    @Override
    public int getCapacity() {
        return capacity;
    }

    // Для отладки
    @Override
    public void print() {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println("");
    }

    private void itemCheckForAdd(String item) {
        if (item == null) {
            throw new NullPointerException("String is not be Null");
        }
    }

    private void checkItemIsNull(String item) {
        if (item == null) {
            throw new NullPointerException("String is not be Null");
        }
        if (item.equals("")) {
            throw new NullPointerException("String is not be empty");
        }
    }

    private void checkIndexRange(int index) {
        if (index > capacity || index < 0)
            throw new IndexOutOfBoundsException("Неверный индекс: " + index + ", максимальный: " + capacity);
    }

    private void growIfNeed(){
        if (size == capacity) {
            // Создаем массив в 2 раза больше старого.
            capacity *= 2;
            data = Arrays.copyOf(data, capacity);
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null){
                    data[i] = "";
                }
            }
        }
    }
    // Я не стал заморачиваться и писать несколько своих исключений. Но сделал лист расширяемым.


}
