package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.HashMap;
import java.util.List;

public class Interpreter {
    HashMap<String, FunctionDefinitionNode> fTable;
    HashMap<String, AstNode> vTable;

    public Interpreter() {
        fTable = new HashMap<>();
        vTable = new HashMap<>();
    }

    public AstNode visit(AstNode node) {
        if (node instanceof ProgramNode) {
            return visit((ProgramNode) node);
        } else if (node instanceof StatementNode) {
            return visit((StatementNode) node);
        } else if (node instanceof ExpressionNode) {
            return visit((ExpressionNode) node);
        }
        return node;
    }


    public AstNode visit(StatementNode node) {
        if (node.getNode() instanceof AssignmentNode) {
            return visit((AssignmentNode) node.getNode());
        } else if (node.getNode() instanceof VariableDeclarationNode) {
            visit((VariableDeclarationNode) node.getNode());
        } else if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        } else if (node.getNode() instanceof FunctionDefinitionNode) {
            return visit((FunctionDefinitionNode) node.getNode());
        }
        return null;
    }


    public AstNode visit(AssignmentNode node) {
        System.out.println("Assigning " + node.getValue() + " to " + node.getIdentifier());
        System.out.println(node);
        return node;
    }

    public void visit(VariableDeclarationNode node) {
        if (!vTable.containsKey(node.getIdentifier().toString())) {
            System.out.println(STR."stored:\{node}");
            vTable.put(node.getIdentifier().toString(), node.getValue());
        } else {
            System.out.println(STR."node already exist:\{node}");
        }
    }

    public void visit(VariableDeclarationNode node, HashMap<String, AstNode> localTable) {
        if (!vTable.containsKey(node.getIdentifier().toString()) && !localTable.containsKey(node.getIdentifier().toString())) {
            System.out.println(STR."stored:\{node}");
            localTable.put(node.getIdentifier().toString(), node.getValue());
        } else {
            System.out.println(STR."node already exist:\{node}");
        }
    }


    public AstNode visit(TypeNode node) {
        return node;
    }


    public AstNode visit(IntNode node) {
        return node;
    }


    public AstNode visit(FloatNode node) {
        return node;
    }


    public AstNode visit(ProgramNode node) {
        for (AstNode statement : node.getStatements()) {
            visit(statement);
        }
        return node;
    }

    public AstNode visit(FunctionCallNode node) {
        HashMap<String, AstNode> localTable = new HashMap<>();
        if (fTable.containsKey(node.getFunctionName().getIdentifier())) {
            FunctionDefinitionNode function = fTable.get(node.getFunctionName().getIdentifier());
            List<ParameterNode> arguments = function.getArguments().getParameters();
            for (int i = 0; i < function.getArguments().getParameters().size(); i++)
                visit(new VariableDeclarationNode(arguments.get(i).getIdentifier(), arguments.get(i).getType(), node.getArgument(i)), localTable);
            System.out.println("hgfsahsdagasfdsa");
            System.out.println(function.getBlock().getStatements());
            for (AstNode statement:function.getBlock().getStatements())
            {
                System.out.println("tgest");
                visit((StatementNode) statement);
            }
        }
        if (node.getFunctionName().getIdentifier().equals("print")) {
            String toPrint = "";
            for (AstNode argument : node.getArguments()) {
                if (argument instanceof StatementNode) {
                    toPrint += ((StatementNode) argument).getNode();
                } else {
                    toPrint += argument.toString();
                }
            }
            System.out.println(toPrint);

        }
        return node;
    }

    public AstNode visit(AdditionNode node) {
        AstNode left = visit(node.getLeft());
        AstNode right = visit(node.getRight());

        if (left instanceof IntNode && right instanceof IntNode) {
            int result = Integer.parseInt(left.toString()) + Integer.parseInt(right.toString());

            return new IntNode(Integer.toString(result));
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            float result = Float.parseFloat(left.toString()) + Float.parseFloat(right.toString());
            return new FloatNode(Float.toString(result));
        }
        return null;
    }

    public AstNode visit(ExpressionNode node) {
        if (node.getNode() instanceof AdditionNode) {
            return visit((AdditionNode) node.getNode());
        }
        if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        }
        return node;
    }

    public AstNode visit(FunctionDefinitionNode node) {
        if (!fTable.containsKey(node.getIdentifier().toString())) {
            System.out.println(STR."stored:\{node}");
            fTable.put(node.getIdentifier().toString(), node);
        } else {
            System.out.println(STR."node already exist:\{node}");
        }
        return node;
    }
}
