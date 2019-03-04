import org.junit.Test;
import javafx.util.Pair;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TreeTest {

    @Test
    public void equals(){
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree1 = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);
        Tree tree2 = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        Map<Integer, Inf> map1 = new HashMap<>();
        map1.put(8, new Inf(null, 5, 10));
        map1.put(5, new Inf(8, 3, null));
        map1.put(3, new Inf(5, null, null));
        map1.put(10, new Inf(8, null, null));
        Tree tree3 = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map1);

        Pair<Integer, Inf> a = new Pair<>(8, new Inf(null, 5, 10));
        Pair<Integer, Inf> b = new Pair<>(8, new Inf(null, 5, 10));
        Tree treeA = new Tree(a, map);
        Tree treeB = new Tree(b, map1);

        Map<Integer, Inf> map2 = new HashMap<>();
        map2.put(8, new Inf(null, 5, 10));
        map2.put(5, new Inf(8, null, null));
        map2.put(10, new Inf(8, null, null));
        Tree treeN = new Tree(a, map2);

        assertEquals(tree1, tree1);
        assertEquals(tree1, tree2);
        assertEquals(tree1, tree3);
        assertEquals(treeA, treeB);
        assertEquals(tree1, treeA);
        assertNotEquals(tree1, treeN);
    }

    @Test
    public void belongs() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertTrue(tree.belongs(5));
        assertTrue(tree.belongs(3));
        assertTrue(tree.belongs(10));
        assertFalse(tree.belongs(1));
        assertFalse(tree.belongs(7));
        assertFalse(tree.belongs(12));
    }

    @Test
    public void binaryTree() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals(map, tree.binaryTree());
    }

    @Test
    public void root(){
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals(new Pair<>(8, new Inf(null, 5, 10)), tree.root());
    }

    @Test
    public void parent() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals((Integer) 8, tree.parent(5));
        assertEquals((Integer) 8, tree.parent(10));
        assertEquals((Integer) 5, tree.parent(3));
        assertNull(tree.parent(8));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void getParent() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals(new Pair<>(8, new Inf(null, 5, 10)), tree.getParent(5));
        assertEquals(new Pair<>(5, new Inf(8, 3, null)), tree.getParent(3));
        assertNull(tree.getParent(8));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void left() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals((Integer) 5, tree.left(8));
        assertEquals((Integer) 3, tree.left(5));
        assertNull(tree.left(3));
        assertNull(tree.left(10));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void getLeft() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals(new Pair<>(5, new Inf(8, 3, null)), tree.getLeft(8));
        assertEquals(new Pair<>(3, new Inf(5, null, null)), tree.getLeft(5));
        assertNull(tree.getLeft(3));
        assertNull(tree.getLeft(10));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void right() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals((Integer) 10, tree.right(8));
        assertNull(tree.right(5));
        assertNull(tree.right(3));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void getRight() {
        Map<Integer, Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        assertEquals(new Pair<>(10, new Inf(8, null, null)), tree.getRight(8));
        assertNull(tree.right(5));
        assertNull(tree.right(3));
        assertThrows(IllegalArgumentException.class, () -> tree.getParent(15));    // исключение при обращении к элементу, которого нет в дереве
    }

    @Test
    public void add() {
        // 1) Добавление элемента в пустое дерево
        // (изменение корня и добавление элемента в мапу)
        Map<Integer, Inf> map1 = new HashMap<>();   // Дерево, в которое будем добавлять элемент
        Tree treeBefore = new Tree(new Pair<>(null, new Inf(null, null, null)), map1);

        Map<Integer, Inf> map2 = new HashMap<>();   // Дерево - ожидаемый результат
        map2.put(8, new Inf(null, null, null));
        Tree treeAfter = new Tree(new Pair<>(8, new Inf(null, null, null)), map2);

        treeBefore.add(8);
        assertEquals(treeAfter, treeBefore);

        // 2) Добавление элемента в качестве потомка корня
        // (изменение корня, изменение информации о корне в мапе, добавление элемента в мапу)
        map2.put(8, new Inf(null, 5, null));
        map2.put(5, new Inf(8, null, null));
        treeAfter = new Tree(new Pair<>(8, new Inf(null, 5, null)), map2);

        treeBefore.add(5);
        assertEquals(treeAfter, treeBefore);

        // 3) Добавление элемента в качестве второго потомка корня
        // (изменение корня, изменение информации о корне в мапе, добавление элемента в мапу и отсутствие потерь элементов)
        map2.put(8, new Inf(null, 5, 10));
        map2.put(5, new Inf(8, null, null));
        map2.put(10, new Inf(8, null, null));
        treeAfter = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map2);

        treeBefore.add(10);
        //assertEquals(treeAfter, treeBefore);
        //assertEquals(treeAfter.root(), treeBefore.root());
        assertEquals(treeAfter.binaryTree(), treeBefore.binaryTree());

        // 4) Добавление элемента не к корню
        // (изменение информации в мапе о предке и добавление в мапу самого элемента)
        map2.put(8, new Inf(null, 5, 10));
        map2.put(5, new Inf(8, 3, null));
        map2.put(3, new Inf(5, null, null));
        map2.put(10, new Inf(8, null, null));
        treeAfter = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map2);

        treeBefore.add(3);
        assertEquals(treeAfter, treeBefore);

        // 5) Добавление элемента, который уже есть в мапе
        // (ничего не должно произойти)
        treeBefore.add(3);
        assertEquals(treeAfter, treeBefore);
    }

    @Test
    public void del() {
        // 1) Удаляем корень, имеющий потомков
        // (обновить корень; удалить элемент из мапы; обновить информацию в мапе об элементе, который будет поставлен
        // на место удалённого элемента; обновить информацию в мапе о потомках удалённого элемента, изменив у них предка)
        Map<Integer,Inf> map1 = new HashMap<>();
        map1.put(8, new Inf(null, 5, 10));
        map1.put(5, new Inf(8, 3, null));
        map1.put(3, new Inf(5, null, null));
        map1.put(10, new Inf(8, null, null));
        Tree treeBefore = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map1);

        Map<Integer, Inf> map = new HashMap<>();
        map.put(10, new Inf(null, 5, null));
        map.put(5, new Inf(10, 3, null));
        map.put(3, new Inf(5, null, null));
        Tree treeAfter = new Tree(new Pair<>(10, new Inf(null, 5, null)), map);

        treeBefore.del(8);
        assertEquals(treeAfter, treeBefore);

        // 2) Удаляем элемент, которого вообще нет в дереве
        // (исключение)
        assertThrows(IllegalArgumentException.class, () -> treeBefore.del(11));

        // 3) Удаляем элемент, находящийся в середине дерева, т.е. у него есть предок и хотя бы один потомок
        // (удалить элемент из мапы; обновить информацию в мапе об элементе, который будет поставлен на место
        // удалённого элемента; обновить в мапе информацию о потомках удалённого элемента, изменив у них предка;
        // обновить в мапе информацию о предке удалённого элемента, изменив у него нужного потомка)
        map.put(10, new Inf(null, 3, null));
        map.remove(5);
        map.put(3, new Inf(10, null, null));
        treeAfter = new Tree(new Pair<>(10, new Inf(null, 3, null)), map);

        treeBefore.del(5);
        assertEquals(treeAfter, treeBefore);

        // 4) Удаляем элемент, находящийся в конце дерева, т.е. не имеет потомков
        // (удалить элемент из мапы; обновить информацию о предке удалённого элемента)
        map.put(10, new Inf(null, null, null));
        map.remove(3);
        treeAfter = new Tree(new Pair<>(10, new Inf(null, null, null)), map);

        treeBefore.del(3);
        assertEquals(treeAfter, treeBefore);

        // 5) Удаляем единственный элемент дерева
        // (изменение информации о корне, очищение мапы)
        map.remove(10);
        treeAfter = new Tree(new Pair<>(null, new Inf(null, null, null)), map);

        treeBefore.del(10);
        assertEquals(treeAfter, treeBefore);
    }

    @Test
    public void str() {
        Map<Integer,Inf> map = new HashMap<>();
        map.put(8, new Inf(null, 5, 10));
        map.put(5, new Inf(8, 3, null));
        map.put(3, new Inf(5, null, null));
        map.put(10, new Inf(8, null, null));
        Tree tree = new Tree(new Pair<>(8, new Inf(null, 5, 10)), map);

        String expected = "root = 8 : Inf (parent = null; left = 5; right = 10)\n" +
                "3 : Inf(parent = 5; left = null; right = null)\n" +
                "5 : Inf(parent = 8; left = 3; right = null)\n" +
                "8 : Inf(parent = null; left = 5; right = 10)\n" +
                "10 : Inf(parent = 8; left = null; right = null)\n";

        assertEquals(expected, tree.str());
    }
}