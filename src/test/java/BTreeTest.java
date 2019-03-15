import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BTreeTest {

    BTree tree = new BTree();
    BTree treeEmpty = new BTree();

    @Before
    public void fun() {
        tree.add(10);
        tree.add(7);
        tree.add(5);
        tree.add(9);
        tree.add(8);
        tree.add(6);
    }

    @After
    public void fun1() {
        tree.clear();
        treeEmpty.clear();
    }

    @Test
    public void isEmpty() {
        assertTrue(treeEmpty.isEmpty());
        assertFalse(tree.isEmpty());
    }

    @Test
    public void clear() {
        tree.clear();
        assertEquals(treeEmpty, tree);
    }

    @Test
    public void search() {
        assertTrue(tree.search(10));            // поиск элемента, который есть в дереве
        assertTrue(tree.search(8));
        assertFalse(tree.search(3));            // поиск элемента, которого нет в дереве
        assertFalse(treeEmpty.search(1));       // поиск элемента в путом дереве
        assertFalse(treeEmpty.search(null));    // поиск null
        assertFalse(tree.search(null));
    }

    @Test
    public void get() {
        assertThrows(IllegalArgumentException.class, () -> tree.get(15));    // исключение при обращении к элементу, которого нет в дереве
        // Нет возможности проверить имеенно узлы, но можно отдельно проверить значение узла и его соседей
        // полная проверка всех соседей для узла 7
        assertEquals((Integer)7, tree.get(7).toInteger());      // значение
        assertEquals((Integer)10, tree.get(7).parent());        // предок
        assertEquals((Integer)5, tree.get(7).left());           // левый потомок
        assertEquals((Integer)9, tree.get(7).right());          // правый потомок
    }

    @Test
    public void parent() {
        assertNull(tree.parent(10));                            // предок корня
        assertEquals(tree.get(10), tree.parent(7));             // предок элемента, который есть в дереве
        assertEquals(tree.get(7), tree.parent(5));
        assertEquals(tree.get(5), tree.parent(6));
        assertThrows(IllegalArgumentException.class, () -> tree.parent(15));         // исключение при обращении к элементу, которого нет в дереве
        assertThrows(IllegalArgumentException.class, () -> treeEmpty.parent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void left() {
        assertEquals(tree.get(7), tree.left(10));                // левый потомок элемента, который есть в дереве
        assertEquals(tree.get(5), tree.left(7));
        assertNull(tree.left(8));                                // левый потомок элемента, у которого нет потомков
        assertThrows(IllegalArgumentException.class, () -> tree.left(15));         // исключение при обращении к элементу, которого нет в дереве
        assertThrows(IllegalArgumentException.class, () -> treeEmpty.left(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void right() {
        assertEquals(tree.get(6), tree.right(5));                // правый потомок элемента, который есть в дереве
        assertEquals(tree.get(9), tree.right(7));
        assertNull(tree.right(10));                              // правый потомок элемента, у которого нет праовго потмока
        assertThrows(IllegalArgumentException.class, () -> tree.right(15));         // исключение при обращении к элементу, которого нет в дереве
        assertThrows(IllegalArgumentException.class, () -> treeEmpty.right(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void min() {
        assertEquals(tree.get(5), tree.min(tree.get(10)));       // минимум во всём дереве
        assertEquals(tree.get(8), tree.min(tree.get(9)));        // минимум в поддереве
        assertThrows(IllegalArgumentException.class, () -> tree.min(tree.get(15)));            // исключение при обращении к элементу, которого нет в дереве
        assertThrows(IllegalArgumentException.class, () -> treeEmpty.min(treeEmpty.get(15)));  // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void add() {
        assertNotEquals(tree, treeEmpty);
        treeEmpty.add(10);
        treeEmpty.add(7);
        treeEmpty.add(5);
        treeEmpty.add(9);
        treeEmpty.add(8);
        treeEmpty.add(6);
        assertEquals(tree, treeEmpty);
        treeEmpty.add(12);
        assertNotEquals(tree, treeEmpty);
    }

    @Test
    public void del() {
        assertThrows(IllegalArgumentException.class, () -> tree.del(15));    // исключение при обращении к элементу, которого нет в дереве
        // удаление корня, у которого есть оба потомка
        tree.add(13);
        tree.del(10);
        treeEmpty.add(13);
        treeEmpty.add(7);
        treeEmpty.add(5);
        treeEmpty.add(6);
        treeEmpty.add(9);
        treeEmpty.add(8);
        assertEquals(tree.toList(), treeEmpty.toList());
        // удаление элемента, у которого есть только левый потомок
        tree.del(9);
        treeEmpty.clear();
        treeEmpty.add(13);
        treeEmpty.add(7);
        treeEmpty.add(5);
        treeEmpty.add(6);
        treeEmpty.add(8);
        // удаление элемента, у которого есть только правый потомок
        tree.del(5);
        treeEmpty.clear();
        treeEmpty.add(13);
        treeEmpty.add(7);
        treeEmpty.add(6);
        treeEmpty.add(8);
        assertEquals(tree, treeEmpty);
        // удаление элемента, у которого есть оба потомка, но он не корень
        tree.del(7);
        treeEmpty.clear();
        treeEmpty.add(13);
        treeEmpty.add(8);
        treeEmpty.add(6);
        assertEquals(tree, treeEmpty);
        // удаление элемента, у которого нет потомков
        tree.del(6);
        treeEmpty.clear();
        treeEmpty.add(13);
        treeEmpty.add(8);
        assertEquals(tree, treeEmpty);
    }

    @Test
    public void toList() {
        List<Integer> list = new ArrayList<>();
        assertEquals(treeEmpty.toList(), list);
        list.add(10);
        list.add(7);
        list.add(5);
        list.add(6);
        list.add(9);
        list.add(8);
        assertEquals(tree.toList(), list);
    }
}