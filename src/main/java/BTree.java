import java.util.ArrayList;
import java.util.List;

public class BTree {
    public static class Node {
        private Integer value;
        private Node parent;     // предок
        private Node left;       // левый (меньший) потомок
        private Node right;      // правый (больший) потомок

        public Node(Integer value) {
            this.value = value;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        public Integer toInteger() {
            return value;
        }

        public Integer parent() {
            if (parent == null)
                return null;
            return parent.value;
        }

        public Integer left() {
            if (left == null)
                return null;
            return left.value;
        }

        public Integer right() {
            if (right == null)
                return null;
            return right.value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;
            if (obj instanceof Node) {
                Node other = (Node) obj;

                return this.value.equals(other.value) && this.parent == other.parent && this.left == other.left && this.right == other.right;
            } else return false;
        }
    }

    private Node root = null;

    public BTree() {
        root = null;
    }

    /** Функция, проверяющая дерево на пустоту **/
    public boolean isEmpty() {
        return root == null;        // Пустоту дерева можно определить по отсутствию корня (т.е. корень равен null)
    }

    /** Функция, очищающая дерево **/
    public void clear() {
        root = null;
    }

    /** Рекурсивная функция поиска элемента в дереве **/
    // Предполагается, что поиск обычно будет начинаться с корня дерева, но можно также и искать элемент в поддереве
    public boolean search(Integer el, Node node) {
        if (el == null || node == null)
            return false;
        int rKey = node.value;                     // запоминаем ключ узла
        if (el.equals(rKey))                       // Если искомый элемент равен нынешнему узлу, то он есть в дереве,
            return true;                           // поэтому возвращаем true
        if (el < rKey && node.left != null)        // Если искомый элемент меньше нынешнего узла, и у узла уже есть леывй потомок,
            return search(el, node.left);          // вызываем функцию поиска, только новым узлом станет левый потом ныншенего узла
        if (el > rKey && node.right != null)       // Аналогично для элемента, большего узла
            return search(el, node.right);
        return false;
    }

    // Та же функция, только на вход функции давать только элемент, a поиск всегда осуществляется с корня дерева
    public boolean search(Integer el) {
        return search(el, root);
    }

    /** Функция вывода узла дерева **/
    public Node get(Integer el) {
        if (!search(el, root) || isEmpty()) throw new IllegalArgumentException("Element missing from tree");
        Node node = root;                            // поиск будет начинаться с корня дерева
        Integer nKey = root.value;
        while (!el.equals(nKey)) {                   // Цилк завершится, когда значение нынешнего узла станет равно искомому элементу
            if (el < nKey && node.left != null)      // Если элемент меньше узла, и у узла есть левый потомок,
                node = node.left;                    // то следующим узлом для сравнения станет левый потомок нынешного узла
            else                                     // Если же элемент больше узла (при равенстве элемента и узла мы не войдём в цикл),
                node = node.right;                   // то следующим узлом для сравнения станет правый потомок нынешнего узла
            nKey = node.value;                       // обновляем информацию о значении узла
        }
        return node;
    }

    /** Функция, показывающая предка элемента в виде узла **/
    // Можно вывести предка как Integer с помощью метода toInteger() для Node
    public Node parent(Integer el) {
        return get(el).parent;
    }

    /** Функция, показывающая левого потомка элемента в виде узла**/
    public Node left(Integer el) {
        return get(el).left;
    }

    /** Функция, показывающая левого потомка элемента в виде узла**/
    public Node right(Integer el) {
        return get(el).right;
    }

    /** Функция поиска минимального элемента в поддереве **/
    public Node min(Node root) {
        if (root.left == null)
            return root;
        return min(root.left);
    }

    /** Функция добавления элемента в дерево **/
    public void add(Integer el) {
        if (!search(el, root)) {                    // Элемент будет добавляться только в том случае, если его нет в дереве
            if (isEmpty())                          // Если дерево пустое,
                root = new Node(el);                // ты мы делаем новый элемент корнем.
            else {                                  // Если же дерево не пустое
                Node elNode = new Node(el);         // Создаём новый узел
                Node root = this.root;
                while (root != null) {              // Будем проходиться по узлам дерева
                // Случай, когда добавляемый элемент меньше корня поддерева
                    if (el < root.value)
                        if (root.left == null) {    // Если у корня поддерева нет левого потомка,
                            root.left = elNode;     // меняем левого потомка у корня
                            elNode.parent = root;   // и добавляем узел в дерево, меняя у него предка
                            break;                  // На этом прерываем цикл while.
                        } else                      // Если же у корня есть левый потомок
                            root = root.left;       // Опять идём в while, но уже с новым корнем
                // Случай, когда добавляемый элемент больше корня поддерва
                    else
                        if (root.right == null) {   // Если у корня поддерева нет правого потомка,
                            root.right = elNode;    // меняем левого потомка у корня
                            elNode.parent = root;   // и добавляем узел в дерево, меняя у него предка
                            break;                  // На этом прерываем цикл while.
                        } else                      // Если же у корня есть левый потомок
                            root = root.right;
                }
            }
        }
    }

    /** Функция удаления элемента из дерева **/
    public void del(Integer el) {
        // Если el нет в дереве, бросаем исключение
        if (!search(el, root)) throw new IllegalArgumentException("Element missing from tree");
        // Чтобы не возникало исключение NullPointerException, задаём parent, left и right через if..else
        Integer parent;
        if (get(el).equals(root))
            parent = null;
        else
            parent = parent(el).toInteger();
        Integer left;
        if (get(el).left == null)
            left = null;
        else
            left = left(el).toInteger();
        Integer right;
        if (get(el).right == null)
            right = null;
        else
            right = right(el).toInteger();
        // Случай, когда у удаляемого элемента нет потомков
        if (left == null && right == null) {
            if (parent == null)                     // Если удаляем корень,
                clear();                            // очищаем дерево.
            else {
                if (el < parent)                    // Узнаём, каким потомком (левым или правым) является el,
                    get(parent).left = null;        // и меняем информацию о соответствующем потомке у предка.
                else
                    get(parent).right = null;
            }
        }
        // Случай, когда у удаляемого элемента есть только левый потомок,
        // то на место удаляемого элемента вставляем левого потомка этого элемента
        if (left != null && right == null) {
            if (parent == null) {                   // Если удаляем корень,
                get(left).parent = null;            // то у левого потомка убираем предка
                root = get(left);                   // и делаем его корнем дерева.
            }
            else {
                if (el < parent)                    // Узнаём, каким потомком (левым или правым) является el,
                    get(parent).left = left(el);    // и меняем информацию о соответствующем потомке у его предка.
                else
                    get(parent).right = left(el);
                get(left).parent = get(parent);     // Меняем информацию о предке
            }
        }
        // Случай, когда у удаляемого элемента есть только правый потомок
        if (left == null && right != null) {
            if (parent == null) {                   // Если удаляем корень,
                get(right).parent = null;           // то у правого потомка убираем предка
                root = get(right);                  // и делаем его корнем дерева.
            }
            else {
                if (el < parent)                    // Узнаём, каким потомком (левым или правым) является el,
                    get(parent).left = right(el);   // и меняем информацию о соответствующем потомке у его предка.
                else
                    get(parent).right = right(el);
                get(right).parent = get(parent);    // Меняем информацию о предке
            }
        }
        // Случай, когда у удаляемого элемента есть оба потомка
        // Элементом, который займёт место el будет min среди тех, которые "росли" от el
        if (left != null && right != null) {
            Node newEl = min(get(right));              // находим новый элемент
            newEl.left = get(left);                    // меняем потомков у newEl
            newEl.right = get(right);
            get(left).parent = newEl;                  // меняем предка у потомков удаляемого элемента на нового
            get(right).parent = newEl;
            if (newEl.value < newEl.parent.value)      // Узнаём, каким потомком (левым или правым) является newEl
                get(newEl.parent.value).left = null;   // и удаляем соответствующего потомка (сам newEl) у предка newEl
            else
                get(newEl.parent.value).right = null;
            if (parent == null) {                      // Если удаляем корень,
                newEl.parent = null;                   // то убираем у нового элемента предка
                root = newEl;                          // и делаем его корнем дерева.
            }
            else {
                newEl.parent = get(parent);
                if (el < parent)                       // меняем нужного потомка у предка удаляемого элемента
                    get(parent).left = newEl;
                else
                    get(parent).right = newEl;
            }
        }
    }

    /** Функция преобразования дерева в последовательный список элементов **/
    // Элементы добавляются в порядке: вершина, левое поддерево, правое поддерево.
    public List<Integer> toList(Node node, List<Integer> list) {
        if (node != null) {
            list.add(node.value);
            toList(node.left, list);
            toList(node.right, list);
        }
        return list;
    }

    // Та же функция, только ей ничего не надо подавать на вход, а добавление элементов начинается от корня
    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        return toList(root, list);
    }

    /** Функция сравнения деревьев **/
    // Сравнение через функцию toList
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof BTree) {
            BTree other = (BTree) obj;
            return this.toList().equals(other.toList());
        } else return false;
    }
}