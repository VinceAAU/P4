package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

public class CoordNode extends AstNode {
    //Because it's final, coords end up being immutable
    //Since it's only two numbers, we should be fine
    @Getter private int x;
    @Getter private int y;

    public CoordNode(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    //THIS METHOD IS A BAND-AID BECAUSE WE MUTATE PRIMITIVES. PRIMITIVES SHOULD NOT BE MUTABLE. DON'T MUTATE PRIMITIVES. WHY WAS THIS THING PROGRAMMED THIS WAS? TODO FIX THAT. NO PRIMITIVE MUTATION. DOWN WITH THE UPPER CLASSES
    public void mutate(CoordNode newValues){
        this.x = newValues.getX();
        this.y = newValues.getY();
    }
}
