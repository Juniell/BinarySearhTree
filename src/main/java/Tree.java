import javafx.util.Pair;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

public final class Tree {
    private Pair<Integer, Inf> Root;             // корень дерева (по сути - первый добавленный элемент)
    private Map<Integer, Inf> BinaryTree;

    public Tree(Pair<Integer, Inf> root, Map<Integer, Inf> binaryTree) {
        Root = root;
        BinaryTree = binaryTree;
    }

    /** Функция сравнения деревьев **/
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Tree) {
            Tree other = (Tree) obj;
            return Root.getKey() == other.Root.getKey() && Root.getValue().equals(other.Root.getValue()) && BinaryTree.equals(other.BinaryTree);
        } else return false;
    }

    /** Функция, показывающая, есть ли элемент в дереве **/
    public boolean belongs(Integer other) {
        return BinaryTree.containsKey(other);
    }

    /** Функции, выдающие корень и мапу элементов **/
    public Map<Integer, Inf> binaryTree() {
        return BinaryTree;
    }
    public Pair<Integer, Inf> root() {
        return Root;
    }

    /** Функции вывода предка, левого и правого потомка **/
    public Integer parent(int other) {                      // Вывод в виде числа
        if (!BinaryTree.containsKey(other)) throw new IllegalArgumentException("Element missing from tree");
        return BinaryTree.get(other).parent();
    }
    public Pair<Integer, Inf> getParent(int other) {        // Вывод в в виде пары
        if (!BinaryTree.containsKey(other)) throw new IllegalArgumentException("Element missing from tree");
        Integer par = parent(other);
        if (par == null)
            return null;        Inf parInf = BinaryTree.get(par);
        return new Pair<>(par, parInf);
    }

    public Integer left(int other) {
        return BinaryTree.get(other).left();
    }
    public Pair<Integer, Inf> getLeft(int other) {
        if (!BinaryTree.containsKey(other)) throw new IllegalArgumentException("Element missing from tree");
        Integer left = left(other);
        if (left == null)
            return null;
        Inf leftInf = BinaryTree.get(left);
        return new Pair<>(left, leftInf);
    }

    public Integer right(int other) {
        return BinaryTree.get(other).right();
    }
    public Pair<Integer, Inf> getRight(int other) {
        if (!BinaryTree.containsKey(other)) throw new IllegalArgumentException("Element missing from tree");
        Integer right = right(other);
        if (right == null)
            return null;
        Inf rightInf = BinaryTree.get(right);
        return new Pair<>(right, rightInf);
    }

    /** Рекурсивная функция поиска нужного места и добавления элемента в дерево **/
    public void search(int el, Pair<Integer, Inf> r) {          // r = root дерева первый раз
        int rKey = r.getKey();      // Запоминаем ключ корня (т.е. само число) т.к. будем часто к нему обращаться
        // Сначала рассматриваем два случая, когда r - тот корень, который станет родителем заданного элемента
        // 1) Наш элемент меньше корня, и у корня нет левых детей.
        // Добавляем в мапу информацию о новом элементе и обновляем информацию о корне
        if (el < rKey && left(rKey) == null) {
            if (r == Root)
                Root = new Pair<>(rKey, new Inf(null, el, right(rKey)));
            BinaryTree.put(el, new Inf(rKey, null, null));
            BinaryTree.put(rKey, new Inf(parent(rKey), el, right(rKey)));
        }
        // 2) Наше элемент больше корня, и у корня нет правых детей.
        // Добавляем в мапу информацию о новом элементе и обновляем информацию о корне
        if (el > rKey && right(rKey) == null) {
            if (r == Root)
                Root = new Pair<>(rKey, new Inf(null, left(rKey), el));
            BinaryTree.put(el, new Inf(rKey, null, null));
            BinaryTree.put(rKey, new Inf(parent(rKey), left(rKey), el));
        }
        // Остались ещё случаи, когда корень уже имеет потомков

        // 1) Наш элемент меньше корня, но у него уже есть левый ребёнок.
        // Создаём новый корень, типа Pair(значение_левого_ребёнка_старого_корня, Inf_левого_ребёнка_старого_корня)
        // и вызываем эту функцию ещё раз, но уже с обновлённым корнем
        if (el < rKey && left(rKey) != null && right(rKey) != null) {
            Pair<Integer, Inf> nr = getLeft(rKey);
            search(el, nr);
        }
        // 2) Наш элемент больше корня или равен ему, но у него уже есть правый ребёнок.
        // Создаём новый корень, типа Pair<значение_правого_ребёнка_старого_корня, Inf_правого_ребёнка_старого_корня)
        // и вызываем эту функцию ещё раз, но уже с обновлённым корнем
        if (el > rKey && left(rKey) != null && right(rKey) != null) {
            Pair<Integer, Inf> nr = getRight(rKey);
            search(el, nr);
        }
    }

    /** Функция добавления элемента в дерево
     (если элемент уже есть в дереве, то его не добавят ещё раз) **/
    public void add(int other) {
        if (!BinaryTree.containsKey(other)) {       // Если дерево не содержит добавляемый элемент
            if (BinaryTree.isEmpty()) {             // Если дерево пустое
                Pair<Integer, Inf> newRoot = new Pair<>(other, new Inf(null, null, null));
                Root = newRoot;    // делаем добавляемый элемент корнем
                BinaryTree.put(other, new Inf(null, null, null));                   // и добавляем его в мапу
            } else                                  // Если же дерево уже содержит элементы
                search(other, Root);                // вызываем функцию поиска
        }
    }

    /** Функция удаления элемента из дерева **/
    public void del(int other) {
        if (!BinaryTree.containsKey(other)) throw new IllegalArgumentException("Element missing from tree");
        if (BinaryTree.size() == 1) {                                       // Если other - единственный элемент дерева,
            Root = new Pair<>(null, new Inf(null, null, null));             // очищаем корень
            BinaryTree = new HashMap<>();                                   // и мапу
            return;
        }
        Integer left = left(other);                 // левый потомок other
        Integer right = right(other);               // правый потомок other
        Integer parent = parent(other);             // предок other
        BinaryTree.remove(other);                   // Удаляем other
        // Далее изменяем информацию о предках и потомках other
        // Случай, когда у other нет потомков
        if (left == null && right == null) {
            Inf newParent;
            if (other < parent) {                                           // Если other - левый потомок,
                newParent = new Inf(parent(parent), null, right(parent));   // обновляем информацию у parent, удаляя левого потомка
                if (parent == Root.getKey())                                                        // Если предок other - корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, null, right(Root.getKey())));    // обновляем информацию о корне, удаляя у него левого потомка
            }
            else {                                                          // Если other - правый потомок,
                newParent = new Inf(parent(parent), left(parent), null);    // обновляем информацию у parent, удаляя правого потомка
                if (parent == Root.getKey())                                                        // Если предок other - корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, left(Root.getKey()), null));     // обновляем информацию о корне, удяляя у него правого потомка
            }
            BinaryTree.put(parent, newParent);                              // Обновляем информацию в дереве о parent
        }
        // Случай, когда у other есть только левый потомок
        if (left != null && right == null) {
            Inf newEl = new Inf(parent, left(left), right(left));           // Меняем информацию об элементе (левый потомок other), который займет место other
            Inf newParent;
            if (other < parent) {                                           // Если other - левый потомок
                newParent = new Inf(parent(parent), left, right(parent));   // Левым потомком parent стал left (единственный потомок other)
                if (parent == Root.getKey())                                                      // Если предок other - корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, left, right(Root.getKey())));  // обновляем информацию о корне, меняя его левого потомка
            }
            else {                                                          // Если other - правый потомок
                newParent = new Inf(parent(parent), left(parent), left);    // Правым потомком parent стал left (единственный потомок other)
                if (parent == Root.getKey())                                                      // Если предок other - корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, left(Root.getKey()), left));   // обновляем информацию о корне, меняя его правого потомка
            }
            BinaryTree.put(left, newEl);            // Обновляем инф-ию в дереве о left, делая его потомком parent (предок other)
            BinaryTree.put(parent, newParent);      // Обновляем инф-ию в дереве о parent
            if (other == Root.getKey())                                     // Если удаляемый элемент был root дерева,
                Root = new Pair<>(left, newEl);                             // обновляем информацию о root дерева
        }
        // Случай, когда у other есть только правый потомок
        if (right != null && left == null) {
            Inf newEl = new Inf(parent, left(right), right(right));         // Меняем информацию об элементе (правый потомок other), который займет место other
            Inf newParent;
            if (other < parent) {                                           // Если other - левый потомок
                newParent = new Inf(parent(parent), right, right(parent));  // Левым потомком parent стал right (единственный потомок other)
                if (parent == Root.getKey())                                                       // Если предок other - корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, right, right(Root.getKey())));  // обновляем информацию о корне, меняя его левого потомка
            }
            else {                                                          // Если other - правый потомок
                newParent = new Inf(parent(parent), left(parent), right);   // Правым потомком parent стал right (единственный потомок other)
                if (parent == Root.getKey())                                                      // Если предок other -  корень дерева,
                    Root = new Pair<>(Root.getKey(), new Inf(null, left(Root.getKey()), right));  // обновляем информацию о корне, меняя его правого потомка
            }
            BinaryTree.put(right, newEl);          // Обновляем инф-ию в дереве о right, делая его потомком parent (предок other)
            BinaryTree.put(parent, newParent);     // Обновляем инф-ию в дереве о parent
            if (other == Root.getKey())            // Если удаляемый элемент был корнем,
                Root = new Pair<>(right, newEl);   // Обновляем информацию о root дерева
        }
        // Случай, когда у other есть оба ребёнка
        if (left != null && right != null) {
            Set<Integer> keySet = BinaryTree.keySet();    // множество всех элементов дерева (уже без удаляемого)
            // Элементом, который займёт место other будет min среди тех, которые "росли" от правого потомка other
            Integer intNewEl = right;                     // Присваеваем этому элементу значение правого потомка other (на случай, если у right нет потомков)
            for (Integer key: keySet)                                      // Проходим по элементам дерева
                if (other < key && key < right && key < intNewEl)          // Ищем подходящие элементы
                    intNewEl = key;                                        // и выбираем min среди них
            Inf newEl;
            if (intNewEl == right)                        // Если найденный элемент - правый потомок right,
                newEl = new Inf(parent, left, null);      // то ставим новый элемент на место other, убирая у него правого потомка
            else                                          // Если же найденный элемент не является потомком other,
                newEl = new Inf(parent, left, right);     // то просто ставим его на место other
            BinaryTree.put(intNewEl, newEl);              // Добавляем изменённый элемент в дерево на место other
            if (Root.getKey() == other)                   // Если удаляемый элемент был корнем дерева,
                Root = new Pair<>(intNewEl, newEl);       // то ставим найденный элемент на место корня
            Inf newParent;
            // Обновляем информацию в дереве о parent (если он есть), ставя newChild на место other
            if (parent != null) {
                if (other < parent)                                                // Если other - левый потомок parent
                    newParent = new Inf(parent(parent), intNewEl, right(parent));  // ставим новый элемент на место левого потомка parent
                else                                                               // Если other - правый потомок parent
                    newParent = new Inf(parent(parent), left(parent), intNewEl);   // ставим новый элемент на место правого потомка parent
                BinaryTree.put(parent, newParent);                                 //Обновляем в дереве информацию о parent
            }
            // Обновляем информацию в дереве о предке найденного элемента, удаляя у него левого потомка
            // (если найденный элемент = right, то ничего не меняем)
            if (intNewEl != right)
                BinaryTree.put(parent(intNewEl), new Inf(parent(parent(intNewEl)), null, right(parent(intNewEl))));
            // Обновляем информацию в дереве о left, меняя у него предка
            BinaryTree.put(left, new Inf(intNewEl, left(left), right(left)));
            // Обновляем информацию в дереве о right, меняя у него предка
            // (если right = найденный элемент, то информация о нём уже была изменена, поэтому ничего не меняем)
            if (intNewEl != right)
                BinaryTree.put(right, new Inf(intNewEl, left(left), right(left)));
        }
    }

    /** Функция вывода дерева в виде строки **/
    public String str() {
        String a = "root = " + Root.getKey() + " : Inf (parent = " + Root.getValue().parent() + "; left = " + Root.getValue().left() + "; right = " + Root.getValue().right() + ")";
        StringBuilder c = new StringBuilder();
        Set<Integer> setKey = BinaryTree.keySet();
        for (Integer key: setKey)
            c.append(key).append(" : Inf(parent = ").append(BinaryTree.get(key).parent()).append("; left = ").append(BinaryTree.get(key).left()).append("; right = ").append(BinaryTree.get(key).right()).append(")\n");
        return a + "\n" + c;
        // Т.к. значения элементов помещаются в Set, то записываться в строку элементы будут в порядке возрастания
    }
}