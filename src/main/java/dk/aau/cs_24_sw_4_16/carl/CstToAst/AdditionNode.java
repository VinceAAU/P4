package dk.aau.cs_24_sw_4_16.carl.CstToAst;

public class AdditionNode extends AstNode {
    public AstNode left;
    public AstNode right;

    public AdditionNode(AstNode left, AstNode right) {
        this.left = left;
        this.right = right;
        System.out.println(this);
    }

    public AstNode getLeft() {
        return left;
    }

    public AstNode getRight() {
        return right;
    }

//    @Override
//    public String toString() {
//        return "AdditionNode{" +
//                "left=" + left +
//                ", right=" + right +
//                '}';
//    }
}
