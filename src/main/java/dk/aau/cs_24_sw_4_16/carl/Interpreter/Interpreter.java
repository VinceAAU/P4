package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.util.*;

public class Interpreter {
    HashMap<String, FunctionDefinitionNode> fTable;
    HashMap<String, AstNode> vTable;
    Stack<HashMap<String, AstNode>> scopes;
    Deque<Integer> activeScope;

    public Interpreter() {
        fTable = new HashMap<>();
        vTable = new HashMap<>();
        scopes = new Stack<>();
        activeScope = new ArrayDeque<>();
        activeScope.push(0);
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
        } else if (node.getNode() instanceof WhileLoopNode) {
            return visit((WhileLoopNode) node.getNode());
        } else if (node.getNode() instanceof IfStatementNode) {
            visit((IfStatementNode) node.getNode());
        } else if (node.getNode() instanceof BinaryOperatorNode) {
            visit((BinaryOperatorNode) node.getNode());
        } else if (node.getNode() instanceof ReturnStatementNode) {
            return visit((ReturnStatementNode) node.getNode());
        }
        return null;
    }

    public AstNode visit(ReturnStatementNode node) {
        return node;
    }

    public void visit(IfStatementNode node) {
        HashMap<String, AstNode> localTable = new HashMap<>();
        scopes.add(localTable);
        boolean visited = false;
        for (int i = 0; i < node.getExpressions().size(); i++) {
            AstNode toCheck = node.getExpressions().get(i).getNode();
            if (node.getExpressions().get(i).getNode() instanceof IdentifierNode) {
                toCheck = getVariable((IdentifierNode) node.getExpressions().get(i).getNode());
            } else if (node.getExpressions().get(i).getNode() instanceof RelationsAndLogicalOperatorNode) {
                toCheck = visit((RelationsAndLogicalOperatorNode) node.getExpressions().get(i).getNode());
            }
            if (toCheck instanceof BoolNode && ((BoolNode) toCheck).getValue()) {
                visit(node.getBlocks().get(i));
                visited = true;
                break;
            }
        }
        if (!visited && node.getExpressions().size() < node.getBlocks().size()) {
            visit(node.getBlocks().get(node.getBlocks().size() - 1));
        }
        scopes.remove(localTable);
    }

    public AstNode getVariable(IdentifierNode node) {
//        for (HashMap<String, AstNode> vTable : scopes) {
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {
            return scopes.getFirst().get(node.getIdentifier().toString());
        }

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                return scopes.get(i).get(node.getIdentifier().toString());
            }
        }
        throw new RuntimeException("could not find the variable " + node.getIdentifier());
    }

    public void visit(AssignmentNode node) {

        for (HashMap<String, AstNode> vTable : scopes) {
            if (vTable.containsKey(node.getIdentifier().toString())) {
                AstNode nodeToChange = vTable.get(node.getIdentifier().toString());
                AstNode toChange = node.getValue();
                if (toChange instanceof BinaryOperatorNode) {
                    toChange = visit((BinaryOperatorNode) toChange);
                }
                if (toChange instanceof FunctionCallNode) {
                    toChange = visit((FunctionCallNode) toChange);
                }
                if (toChange instanceof IdentifierNode) {
                    toChange = getVariable((IdentifierNode) toChange);
                }
                if (node.getValue() instanceof RelationsAndLogicalOperatorNode) {
                    toChange = visit((RelationsAndLogicalOperatorNode) toChange);
                }
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
                    case null, default -> throw new RuntimeException("Type mismatch");
                }
                return;
            }
        }

        throw new RuntimeException("Variable '" + node.getIdentifier() + "' has not been defined yet.");
    }


    public void visit(VariableDeclarationNode node) {
        boolean found = false;
        if (scopes.getFirst().containsKey(node.getIdentifier().toString())) {
            found = true;
        }

        for (int i = activeScope.getLast(); i < scopes.size(); i++) {
            if (scopes.get(i).containsKey(node.getIdentifier().toString())) {
                found = true;
            }
        }
        if (!found) {

            AstNode toChange = node.getValue();
            if (toChange instanceof FunctionCallNode) {
                toChange = visit((FunctionCallNode) toChange);
            }
            if (node.getValue() instanceof BinaryOperatorNode) {
                toChange = visit((BinaryOperatorNode) node.getValue());
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            } else if (toChange instanceof IdentifierNode) {
                for (HashMap<String, AstNode> vTable : scopes) {
                    if (vTable.containsKey(toChange.toString())) {
                        scopes.getLast().put(node.getIdentifier().toString(), vTable.get(toChange.toString()));
                    }
                }
            } else {
                scopes.getLast().put(node.getIdentifier().toString(), toChange);
            }

        } else {
            throw new RuntimeException("variable " + node.getIdentifier() + " already exists");
        }
    }

    public AstNode visit(TypeNode node) {
        return node;
    }

    public AstNode visit(BoolNode node) {
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
        if (node.getFunctionName().toString().equals("print")) {
            InbuildClasses.print(node, scopes, activeScope);
        } else {
            HashMap<String, AstNode> localTable = new HashMap<>();
            scopes.add(localTable);
            activeScope.add(scopes.size() - 1);

            if (fTable.containsKey(node.getFunctionName().toString())) {
                FunctionDefinitionNode function = fTable.get(node.getFunctionName().toString());
                List<ParameterNode> arguments = function.getArguments().getParameters();
                for (int i = 0; i < function.getArguments().getParameters().size(); i++) {
                    visit(new VariableDeclarationNode(arguments.get(i).getIdentifier(), arguments.get(i).getType(), node.getArgument(i)));
                }
                AstNode test = visit(function.getBlock());

                if (test instanceof ReturnStatementNode) {
                    AstNode returnValue = ((ReturnStatementNode) test).getReturnValue();
                    if (returnValue instanceof IdentifierNode) {
                        returnValue = getVariable((IdentifierNode) returnValue);
                    }
                    scopes.remove(localTable);
                    activeScope.removeLast();
                    if (function.getReturnType().getType().equals("void")) {
                        if (returnValue != null) {
                            throw new RuntimeException("cannot return a value in void function call or have code after return statement");
                        }
                        return node;
                    }

                    return returnValue;
                }
            }
        }
        return node;
    }

    public AstNode visit(BlockNode node) {
        for (AstNode statement : node.getStatements()) {

            if (((StatementNode) statement).getNode() instanceof ReturnStatementNode) {
                return visit((StatementNode) statement);
            } else {
                visit((StatementNode) statement);
            }
        }
        return node;
    }

    public AstNode visit(ExpressionNode node) {
        if (node.getNode() instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node.getNode());
        } else if (node.getNode() instanceof RelationsAndLogicalOperatorNode) {
            return visit((RelationsAndLogicalOperatorNode) node.getNode());
        } else if (node.getNode() instanceof BinaryOperatorNode) {
            return visit((BinaryOperatorNode) node.getNode());
        }
        return node;
    }

    public AstNode visit(FunctionDefinitionNode node) {
        if (!fTable.containsKey(node.getIdentifier().toString())) {
            fTable.put(node.getIdentifier().toString(), node);
        } else {
            throw new RuntimeException("function " + node.getIdentifier() + " already exists");
        }
        return node;
    }

    public AstNode visit(BinaryOperatorNode node) {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();
        if (left instanceof IdentifierNode) {
            left = getVariable((IdentifierNode) left);
        } else if (left instanceof BinaryOperatorNode) {
            left = visit((BinaryOperatorNode) left);
        }
        if (right instanceof IdentifierNode) {
            right = getVariable((IdentifierNode) right);
        } else if (right instanceof BinaryOperatorNode) {
            right = visit((BinaryOperatorNode) right);
        }

        if (left instanceof IntNode && right instanceof IntNode) {
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            return BinaryOperatorNode.getAstNodeValue(left, right, node.getOperator());
        }
        throw new RuntimeException("BinaryOperator not implemented clause");
    }

    public AstNode visit(RelationsAndLogicalOperatorNode node) {
        AstNode left = node.getLeft();
        AstNode right = node.getRight();
        if (left instanceof IdentifierNode) {
            left = getVariable((IdentifierNode) left);
        } else if (left instanceof RelationsAndLogicalOperatorNode) {
            left = visit((RelationsAndLogicalOperatorNode) left);
        }
        if (right instanceof IdentifierNode) {
            right = getVariable((IdentifierNode) right);
        } else if (right instanceof RelationsAndLogicalOperatorNode) {
            right = visit((RelationsAndLogicalOperatorNode) right);
        }
        if (left instanceof IntNode && right instanceof IntNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof FloatNode && right instanceof FloatNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        } else if (left instanceof BoolNode && right instanceof BoolNode) {
            return RelationsAndLogicalOperatorNode.getAstNodeValue(left, right, node.getOperator());
        }
        throw new RuntimeException("RelationsAndLogicalOperator not implemented clause");
    }


    public AstNode visit(WhileLoopNode node) {
        AstNode toCheck = (node.getExpression()).getNode();
        if (toCheck instanceof IdentifierNode) {
            toCheck = getVariable((IdentifierNode) node.getExpression().getNode());
        } else if (toCheck instanceof RelationsAndLogicalOperatorNode) {
            toCheck = visit((RelationsAndLogicalOperatorNode) toCheck);
        }
        while ((toCheck instanceof BoolNode)) {
            if (((BoolNode) toCheck).getValue()) {
                HashMap<String, AstNode> localTable = new HashMap<>();
                scopes.add(localTable);
                visit(node.getBlock());
                toCheck = visit(node.getExpression().getNode());
                if (toCheck instanceof IdentifierNode) {
                    toCheck = getVariable((IdentifierNode) node.getExpression().getNode());
                } else if (toCheck instanceof RelationsAndLogicalOperatorNode) {
                    toCheck = visit((RelationsAndLogicalOperatorNode) toCheck);
                }
                scopes.remove(localTable);
            } else {
                return node;
            }

        }
        throw new RuntimeException("Did not get into while statement");
    }
}
