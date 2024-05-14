# Programming in the CARL scripting language

In general, CARL is a simple imperative language. Statements are 
separated by newlines (no semicolons), and are executed one after the 
other.

## Variables

### Declaration

New variables are defined using the syntax:

    var <identifier>: <type> = <value>

They can be edited without the `var` keyword or specifying type:

    <identifier> = <value>

As an example, this code:

    var foo: int = 3
    print(foo)
    foo = foo + 1
    print(foo)

would output:
    
    3
    4

### Types

CARL has the following primitive types:

| Type      | Explanation                                                                                                | Examples                             |
|-----------|------------------------------------------------------------------------------------------------------------|--------------------------------------|
| `bool`    | Boolean value: either `true` or `false`.                                                                   | `true`, `false`                      |
| `int`     | Integer value: Can be any whole number, either positive or negative (currently limited to 32-bit integers) | `1`, `2`, `32194`, `-900000`         |
| `float`   | Floating-point value: Can be any real number, with 64-bit precision.                                       | `1.0`, `0.0`, `-10000.0`, `1.404001` |
| `string`  | Any text, represented as a string of characters.                                                           | "Hello, World!", "123"                |

