package dk.aau.cs_24_sw_4_16.carl.Statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariableDeclaration extends Statement {
    private String Id;
    private String type;
    private int number;
    private String text;

    public VariableDeclaration(String id, String type, int number) {
        Id = id;
        this.type = type;
        this.number = number;
    }

    public VariableDeclaration(String id, String type, String text) {
        Id = id;
        this.type = type;
        this.text = text;
    }
}
