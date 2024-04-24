package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class CoordNode extends AstNode {
    //Because it's final, coords end up being immutable
    //Since it's only two numbers, we should be fine
    @Getter private final int x;
    @Getter private final int y;

    public CoordNode(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
