package dk.aau.cs_24_sw_4_16.carl.Interpreter;

import dk.aau.cs_24_sw_4_16.carl.CstToAst.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class InbuildClasses {
    public static void print(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, Deque<Integer> activeScope) {
        StringBuilder toPrint = new StringBuilder();
        for (AstNode argument : node.getArguments()) {
            if (argument instanceof StatementNode) {
                toPrint.append(((StatementNode) argument).getNode()).append(" ");
            } else if (argument instanceof IdentifierNode) {
               
                boolean found = false;
                for (int i = scopes.size() - 1; i >= 0; i--) {
                    if (scopes.get(i).containsKey(argument.toString())) {
                        toPrint.append(scopes.get(i).get(argument.toString()).toString()).append(" ");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    int from = 0;
                    if (!activeScope.isEmpty()) {
                        from = activeScope.getFirst();
                    } else {
                        from = scopes.size() - 1;
                    }
                    for (int i = from; i >= 0; i--) {
                        if (scopes.get(i).containsKey(argument.toString())) {
                            toPrint.append(scopes.getFirst().get(argument.toString()).toString()).append(" ");
                        }
                    }

                }

            } else if (argument instanceof FloatNode) {
                toPrint.append(((FloatNode) argument).getValue());
            } else if (argument instanceof IntNode) {
                toPrint.append(((IntNode) argument).getValue());
            } else if (argument instanceof StringNode) {
                toPrint.append(((StringNode) argument).getValue());
            } else if (argument instanceof BoolNode) {
                toPrint.append(((BoolNode) argument).getValue());
            }
        }
        System.out.println(toPrint);
    }

    public static void generateGrid(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationWall) {
        if (node.getArguments().size() == 2) {
            ArrayList<Integer> indices = new ArrayList<>();
            indices.add(((IntNode) node.getArguments().get(0)).getValue());
            indices.add(((IntNode) node.getArguments().get(1)).getValue());
            ArrayNode map = new ArrayNode(new TypeNode("string"), indices);
            scopes.getFirst().put("map", map);
            if (!tileInformationWall.isEmpty()) {
                var key = tileInformationWall.keySet().toArray()[0];
                StringNode symbol = ((StringNode) tileInformationWall.get(key).get("symbol"));
                for (int i = 0; i < ((IntNode) node.getArguments().get(0)).getValue(); i++) {
                    for (int j = 0; j < ((IntNode) node.getArguments().get(1)).getValue(); j++) {
                        map.set(symbol, i, j);
                    }
                }
            } else {
                for (int i = 0; i < ((IntNode) node.getArguments().get(0)).getValue(); i++) {
                    for (int j = 0; j < ((IntNode) node.getArguments().get(1)).getValue(); j++) {
                        map.set(new StringNode("w"), i, j);
                    }
                }
            }
        } else {
            throw new RuntimeException("invalid argument count in generateGrid");
        }
    }

    public static void generateRooms(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationFloor, List<HashMap<String, AstNode>> rooms) {
        if (node.getArguments().size() == 3) {
            int roomCount = ((IntNode) node.getArguments().get(0)).getValue();
            int minRoomSize = ((IntNode) node.getArguments().get(1)).getValue();
            int maxRoomSize = ((IntNode) node.getArguments().get(2)).getValue();
            ArrayNode map = ((ArrayNode) scopes.getFirst().get("map"));

            while (roomCount > rooms.size()) {
                int roomWidth = EvaluatorExecutor.rand.nextInt(minRoomSize, maxRoomSize);
                int roomHeight = EvaluatorExecutor.rand.nextInt(minRoomSize, maxRoomSize);
                int x = EvaluatorExecutor.rand.nextInt(1, map.getSizes().get(1) - roomWidth);
                int y = EvaluatorExecutor.rand.nextInt(1, map.getSizes().get(0) - roomHeight);
                HashMap<String, AstNode> room = new HashMap<>();
                if (!overlap(x, y, roomWidth, roomHeight, map)) {
                    for (int i = y; i < y + roomHeight; i++) {
                        for (int j = x; j < x + roomWidth; j++) {
                            if (tileInformationFloor.isEmpty()) {
                                map.set(new StringNode("f"), i, j);
                            } else {
                                var key = tileInformationFloor.keySet().toArray()[0];
                                StringNode symbol = ((StringNode) tileInformationFloor.get(key).get("symbol"));
                                map.set(symbol, i, j);
                            }

                        }
                    }
                    room.put("x", new IntNode(x));
                    room.put("y", new IntNode(y));
                    room.put("width", new IntNode(roomWidth));
                    room.put("height", new IntNode(roomHeight));
                    rooms.add(room);
                }
            }
        } else {
            throw new RuntimeException("invalid argument count in generateRooms");
        }
    }

    public static void generateCorridors(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationFloor, List<HashMap<String, AstNode>> rooms) {
        if (node.getArguments().isEmpty()) {
            ArrayNode map = ((ArrayNode) scopes.getFirst().get("map"));
            for (int i = 0; i < rooms.size() - 1; i++) {
                int centerX1 = ((IntNode) rooms.get(i).get("x")).getValue() + ((IntNode) rooms.get(i).get("width")).getValue() / 2;
                int centerY1 = ((IntNode) rooms.get(i).get("y")).getValue() + ((IntNode) rooms.get(i).get("height")).getValue() / 2;
                int centerX2 = ((IntNode) rooms.get(i + 1).get("x")).getValue() + ((IntNode) rooms.get(i + 1).get("width")).getValue() / 2;
                int centerY2 = ((IntNode) rooms.get(i + 1).get("y")).getValue() + ((IntNode) rooms.get(i + 1).get("height")).getValue() / 2;
                int min = Math.min(centerX1, centerX2);
                int max = Math.max(centerX1, centerX2);
                for (int j = min; j < max; j++) {
                    if (tileInformationFloor.isEmpty()) {
                        map.set(new StringNode("f"), centerY2, j);
                    } else {
                        var key = tileInformationFloor.keySet().toArray()[0];
                        StringNode symbol = ((StringNode) tileInformationFloor.get(key).get("symbol"));
                        map.set(symbol, centerY2, j);
                    }
                }
                min = Math.min(centerY1, centerY2);
                max = Math.max(centerY1, centerY2);
                for (int j = min; j < max; j++) {
                    if (tileInformationFloor.isEmpty()) {
                        map.set(new StringNode("f"), j, centerX1);
                    } else {
                        var key = tileInformationFloor.keySet().toArray()[0];
                        StringNode symbol = ((StringNode) tileInformationFloor.get(key).get("symbol"));
                        map.set(symbol, j, centerX1);
                    }
                }
            }
        } else {
            throw new RuntimeException("generateCorridors does not take any arguments");
        }
    }

    public static void generateSpawns(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationEnemy, List<HashMap<String, AstNode>> rooms) {
        ArrayNode map = ((ArrayNode) scopes.getFirst().get("map"));
        if (!node.getArguments().isEmpty()) {
            if(node.getArguments().size() == 1 && node.getArguments().get(0) instanceof IntNode) {
                int difficulty = ((IntNode) node.getArguments().get(0)).getValue();
                while (difficulty > 0) {
                    int roomSpawn = EvaluatorExecutor.rand.nextInt(0,rooms.size() - 1);
                    int x = EvaluatorExecutor.rand.nextInt(((IntNode) rooms.get(roomSpawn).get("x")).getValue(), (((IntNode) rooms.get(roomSpawn).get("x")).getValue() + ((IntNode) rooms.get(roomSpawn).get("width")).getValue()));
                    int y = EvaluatorExecutor.rand.nextInt(((IntNode) rooms.get(roomSpawn).get("y")).getValue(), (((IntNode) rooms.get(roomSpawn).get("y")).getValue() + ((IntNode) rooms.get(roomSpawn).get("height")).getValue()));
                    var key = tileInformationEnemy.keySet().toArray()[EvaluatorExecutor.rand.nextInt(0,tileInformationEnemy.size())];
                    StringNode symbol = ((StringNode) tileInformationEnemy.get(key).get("symbol"));
                    int difficultyMonster = ((IntNode) tileInformationEnemy.get(key).get("difficulty")).getValue();
                    if (difficulty >= difficultyMonster) {
                        if (((StringNode) map.get(y,x)).getValue().equals("f")) {
                            map.set(new StringNode(symbol.getValue()), y, x);
                            difficulty -= difficultyMonster;
                        }
                    }
                }
            }
        }

        int yPlayer = EvaluatorExecutor.rand.nextInt(((IntNode) rooms.get(rooms.size() - 1).get("x")).getValue(), (((IntNode) rooms.get(rooms.size() - 1).get("x")).getValue() + ((IntNode) rooms.get(rooms.size() - 1).get("width")).getValue()));
        int xPlayer = EvaluatorExecutor.rand.nextInt(((IntNode) rooms.get(rooms.size() - 1).get("y")).getValue(), (((IntNode) rooms.get(rooms.size() - 1).get("y")).getValue() + ((IntNode) rooms.get(rooms.size() - 1).get("height")).getValue()));
        map.set(new StringNode("p"), xPlayer, yPlayer);
    }

    public static void printMap(Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationFloor, HashMap<String, HashMap<String, AstNode>> tileInformationWall, HashMap<String, HashMap<String, AstNode>> tileInformationEnemy) {

        System.out.println(generatePrint(scopes, tileInformationFloor, tileInformationWall, tileInformationEnemy));
    }

    public static void writeToFile(FunctionCallNode node, Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationFloor, HashMap<String, HashMap<String, AstNode>> tileInformationWall, HashMap<String, HashMap<String, AstNode>> tileInformationEnemy) throws IOException {
        Files.writeString(Path.of("map.json"), generatePrint(scopes, tileInformationFloor, tileInformationWall, tileInformationEnemy));
    }

    private static String generatePrint(Stack<HashMap<String, AstNode>> scopes, HashMap<String, HashMap<String, AstNode>> tileInformationFloor, HashMap<String, HashMap<String, AstNode>> tileInformationWall, HashMap<String, HashMap<String, AstNode>> tileInformationEnemy) {
        ArrayNode map = ((ArrayNode) scopes.getFirst().get("map"));
        StringBuilder sb = new StringBuilder("""
                {"map" : {
                    "size":{
                    "height" : 10,
                    "width" : 10
                     },
                     "tiles": [

                """);
        for (int i = 0; i < map.getSizes().get(0); i++) {
            sb.append("""
                    {"row":[""");
            for (int j = 0; j < map.getSizes().get(1); j++) {
                sb.append("\"").append(((StringNode) map.get(i, j)).getValue()).append("\"");
                if (j < map.getSizes().get(1) - 1) {
                    sb.append(",");
                }
            }
            sb.append("]}");
            if (i < map.getSizes().get(0) - 1) {
                sb.append(",\n");
            }
        }
        sb.append("""
                ],
                "level" : 1,
                "type" : "Room",
                "tileInformation":[
                """);
        if (tileInformationWall.isEmpty()) {
            sb.append("""
                    {
                    "symbol" : "w" ,
                    "info" : {
                        "name" : "Wall"
                       }
                    },
                    """);
        } else {
            tileInformationStringBuilder(tileInformationWall, sb);
        }
        if (tileInformationFloor.isEmpty()) {
            sb.append("""
                    {
                    "symbol" : "f" ,
                    "info" : {
                        "name" : "Floor"
                       }
                    },
                    """);
        } else {
            tileInformationStringBuilder(tileInformationFloor, sb);
        }
        if (!tileInformationEnemy.isEmpty()) {

            tileInformationStringBuilder(tileInformationEnemy, sb);
        }
        sb.append("""
                {
                "symbol" : "p" ,
                "info" : {
                    "name" : "Player"
                   }
                }
                """);
        sb.append("]}}");
        return sb.toString();
    }

    private static void tileInformationStringBuilder(HashMap<String, HashMap<String, AstNode>> tileInformation, StringBuilder sb) {
            for (HashMap<String, AstNode> innerHashMap : tileInformation.values()) {
                sb.append("""
                            {"symbol" :
                        """).append("\"").append(((StringNode) innerHashMap.get("symbol")).getValue()).append("""
                        ","info":{
                        """);
                for (String key : innerHashMap.keySet()) {
                    if (!key.equals("symbol")) {
                        sb.append("\"").append(key).append("\":");
                        if (innerHashMap.get(key) instanceof IntNode) {
                            sb.append(((IntNode) innerHashMap.get(key)).getValue());
                        } else if (innerHashMap.get(key) instanceof FloatNode) {
                            sb.append(((FloatNode) innerHashMap.get(key)).getValue());
                        } else if (innerHashMap.get(key) instanceof BoolNode) {
                            sb.append(((BoolNode) innerHashMap.get(key)).getValue());
                        } else if (innerHashMap.get(key) instanceof StringNode) {
                            sb.append("\"").append(((StringNode) innerHashMap.get(key)).getValue()).append("\"");
                        }
                        sb.append(",");
                    }


                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("}},");
            }
    }

    private static boolean overlap(int x, int y, int width, int height, ArrayNode map) {
        int startY = Math.max(y - 1, 0);
        int startX = Math.max(x - 1, 0);
        int endY = Math.min(y + height + 1, map.getSizes().get(0));
        int endX = Math.min(x + width + 1, map.getSizes().get(1));
        for (int i = startY; i < endY; i++) {
            for (int j = startX; j < endX; j++) {
                if (!((StringNode) map.get(i, j)).getValue().equals("w")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setSeed(FunctionCallNode node) {
        if (node.getArguments().size() == 1) {
            if (node.getArgument(0) instanceof IntNode) {
                EvaluatorExecutor.rand = new Random(((IntNode) node.getArgument(0)).getValue());
            } else {
                throw new RuntimeException("setSeed only supports int arguments");
            }
        } else if (node.getArguments().isEmpty()) {
            EvaluatorExecutor.rand = new Random();
        } else {
            throw new RuntimeException("setSeed called accepts only a singular int argument or none");
        }
    }
}
