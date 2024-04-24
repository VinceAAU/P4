package dk.aau.cs_24_sw_4_16.carl.CstToAst;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayNode extends AstNode {
    @Getter final private TypeNode type;
    @Getter private AstNode[] value;

    ArrayList<Integer> sizes;

    int dimension(){
        return sizes.size();
    }

    public ArrayNode(TypeNode type, ArrayList<Integer> sizes){
        this.type = type;
        this.sizes = sizes;

        if(sizes.contains(-1)){
            throw new RuntimeException("Dynamically sized arrays are not supported yet");
            // Translation: Dynamically sized arrays are a pain in the arse to implement
        }

        if(dimension()!=1) { //If it's an array of arrays
            value = new AstNode[sizes.getFirst()];

            for (int i = 0; i < sizes.getFirst(); i++) {
                value[i] = new ArrayNode(type, new ArrayList<>(sizes.subList(1, sizes.size())));
            }

        } else {
            value = new AstNode[sizes.get(0)];

            for (int i = 0; i < value.length; i++) {
                value[i] = typeStringToDefaultAstNode(type.getType());
            }

        }
    }

    private static AstNode typeStringToDefaultAstNode(String type){
        switch(type){
            case "bool":
                return new BoolNode("false");
            case "coord":
                throw new Error(); //TODO: WHY DON'T WE HAVE A COORDNODE WTF
            case "int":
                return new IntNode("0");
            case "string":
                return new StringNode("");
            case "float":
                return new FloatNode("0.0");
            default:
                //TODO: Figure out identifier stuff
                throw new RuntimeException("What the hell is a " + type + " and why are you trying to array it");
        }
    }

    public AstNode get(int... indices){
        int i = indices[0];

        if(indices.length>1){
            if(!(value[i] instanceof ArrayNode)) throw new RuntimeException("Cannot get index of non-array type");

            return ((ArrayNode) value[i]) .get(Arrays.copyOfRange(indices, 1, indices.length));
        }

        return value[i];
    }

    public void set(AstNode value, int... indices){
        int i = indices[0];

        if(indices.length>1){
            if(!(this.value[i] instanceof ArrayNode)) throw new RuntimeException("Cannot get index of non-array type");

            ((ArrayNode) this.value[i]) .set(value, Arrays.copyOfRange(indices, 1, indices.length));
        } else {
            this.value[i] = value;
        }

        //Potential todo: type checking


    }
}
