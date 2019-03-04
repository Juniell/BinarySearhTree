public final class Inf {
    private Integer Parent;     // предок
    private Integer Left;       // левый (меньший) потомок
    private Integer Right;      // правый (больший) потомок

    public Inf(Integer parent, Integer left, Integer right) {
        Parent = parent;
        Left = left;
        Right = right;
    }

    public Integer parent() {    // выдаст предка
        return Parent;
    }
    public Integer left() {      // выдаст левого потомка
        return Left;
    }
    public Integer right() {     // выдаст правого потомка
        return Right;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Inf) {
            Inf other = (Inf) obj;
            return Parent == other.Parent && Left == other.Left && Right == other.Right;
        } else return false;
    }

    public String str() {
        return "(parent = " + Parent + "; left = " + Left + "; right = " + Right + ")";
    }
}