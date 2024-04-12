package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Interpreter {
    HashMap<String, FunctionDefinitionNode> fTable;
    HashMap<String, AstNode> vTable;
    Stack<HashMap<String, AstNode>> scopes;

    public Interpreter() {
        fTable = new HashMap<>();
        vTable = new HashMap<>();
        scopes = new Stack<>();
        scopes.add(vTable);
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
            visit((AssignmentNode) node.getNode());
        } else if (node.getNode() instanceof VariableDeclarationNode) {
            visit((VariableDeclarationNode) node.getNode());
        } else if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        } else if (node.getNode() instanceof FunctionDefinitionNode) {
            return visit((FunctionDefinitionNode) node.getNode());
        } else if (node.getNode() instanceof IfStatementNode) {
            visit((IfStatementNode) node.getNode());
        }
        return null;
    }

    public void visit(IfStatementNode node) {
        for (int i = 0; i < node.getExpressions().size(); i++) {
            System.out.println(node.getExpressions().get(i));
        }

    }

    public void visit(AssignmentNode node) {

        for (HashMap<String, AstNode> vTable : scopes) {
            if (vTable.containsKey(node.getIdentifier().toString())) {
                AstNode nodeToChange = vTable.get(node.getIdentifier().toString());
                AstNode toChange = node.getValue();
                if (toChange instanceof IdentifierNode) {
                    for (HashMap<String, AstNode> vTable2 : scopes) {
                        if (vTable2.containsKey(node.getValue().toString())) {

                            toChange = vTable2.get(node.getValue().toString());
                        }
                    }
                }

                System.out.println("current value" + nodeToChange + " new value " + toChange.toString());
                AstNode finalToChange = toChange;
                switch (nodeToChange) {
                    case IntNode intNode when finalToChange instanceof IntNode ->
                            intNode.setValue(((IntNode) finalToChange).getValue());
                    case FloatNode floatNode when finalToChange instanceof FloatNode ->
                            floatNode.setValue(((FloatNode) finalToChange).getValue());
                    case StringNode stringNode when finalToChange instanceof StringNode ->
                            stringNode.setValue(((StringNode) finalToChange).getValue());
                    case BoolNode boolNode when finalToChange instanceof BoolNode ->
                            boolNode.setValue(((BoolNode) finalToChange).getValue());
                    case null, default -> System.out.println("type mismatch");
                }
                return;
            }
        }
    }


    public void visit(VariableDeclarationNode node) {
        boolean found = false;
        for (HashMap<String, AstNode> vTable : scopes) {
            if (vTable.containsKey(node.getIdentifier().toString())) {
                found = true;
            }
        }
        if (!found) {
            System.out.println("stored" + node);
            if (node.getValue() instanceof IdentifierNode) {
                for (HashMap<String, AstNode> vTable : scopes) {
                    if (vTable.containsKey(node.getValue().toString())) {
                        System.out.println("stored " + node.getIdentifier() + "value " + vTable.get(node.getValue().toString()));
                        scopes.get(scopes.size() - 1).put(node.getIdentifier().toString(), vTable.get(node.getValue().toString()));
                    }
                }
            } else {

                scopes.get(scopes.size() - 1).put(node.getIdentifier().toString(), node.getValue());
            }

        } else {
            System.out.println("node already exist" + node);
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
        scopes.add(localTable);
        if (fTable.containsKey(node.getFunctionName().toString())) {
            FunctionDefinitionNode function = fTable.get(node.getFunctionName().toString());
            List<ParameterNode> arguments = function.getArguments().getParameters();
            for (int i = 0; i < function.getArguments().getParameters().size(); i++) {
                visit(new VariableDeclarationNode(arguments.get(i).getIdentifier(), arguments.get(i).getType(), node.getArgument(i)));
            }
            visit(function.getBlock());
        }
        if (node.getFunctionName().toString().equals("print")) {
            String toPrint = "";
            for (AstNode argument : node.getArguments()) {
                if (argument instanceof StatementNode) {
                    toPrint += ((StatementNode) argument).getNode()+ " ";
                } else if (argument instanceof IdentifierNode) {
                    for (HashMap<String, AstNode> vTable : scopes) {
                        if (vTable.containsKey(argument.toString())) {
                            toPrint += vTable.get(argument.toString()).toString()+ " ";
                        }

                    }

                }
            }
            System.out.println(toPrint);

        }
        scopes.remove(localTable);
        return node;
    }

    public void visit(BlockNode node) {
        for (AstNode statement : node.getStatements()) {
            visit((StatementNode) statement);
        }
    }

    public AstNode visit(ExpressionNode node) {
        if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        }
        return node;
    }

    public AstNode visit(FunctionDefinitionNode node) {
        if (!fTable.containsKey(node.getIdentifier().toString())) {
            System.out.println("stored" + node);
            fTable.put(node.getIdentifier().toString(), node);
        } else {
            System.out.println("node already exist" + node);
        }
        return node;
    }
}
