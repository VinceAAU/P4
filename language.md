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

### Primitive Types

CARL has the following primitive types:

| Type      | Explanation                                                                                                       | Examples                             |
|-----------|-------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| `bool`    | Boolean value: either `true` or `false`.                                                                          | `true`, `false`                      |
| `int`     | Integer value: Can be any whole number, either positive or negative (currently limited to signed 32-bit integers) | `1`, `2`, `32194`, `-900000`         |
| `float`   | Floating-point value: Can be any real number, with 64-bit precision.                                              | `1.0`, `0.0`, `-10000.0`, `1.404001` |
| `string`  | Any text, represented as a string of characters.                                                                  | "Hello, World!", "123"               |

On top of this, the language also supports [arrays](#Arrays) and [structs](#Structs).

### Arrays
CARL supports arrays. Arrays have a static size, can have multiple 
dimensions, and require all elements to have the same type. 

A one-dimensional array containing five integers is declared like this:

    var myArray: int[5]

As stated before, there can be as many dimensions as needed. For 
example, to create a three-dimensional array where the dimensions have 
sizes of 10, 20, and 30 respectively, the following code is used:

    var threeDimensionalArray: int[10][20][30]

An array can be of any primitive type, which does not include the 
array type or any of the struct types. If an array within an array is 
needed, a two-dimensional array can be utilised instead.

Arrays are zero-indexed, and can be accessed by writing their 
identifier, followed by the indices in square brackets. For example, 
to print the 4th element of `myArray` (note the zero-indexing):

    print(myArray[3])

### Structs

There are currently four structured data types in CARL: `enemy`, 
`floor`, `wall`, and `room`. Each of these structs has a similar construction:

    var <identifier>: <structType> = {
        var <property>: <type> = <value>
        var <property>: <type> = <value>
        ...
    }

#### Enemy
The `enemy` struct is used to represent non-player characters on the 
map. It has three properties: `difficulty`, `health`, and `symbol`

| Name         | Type     | Explanation |
|--------------|----------|-------------|
| `difficulty` | `int`    | ??          |
| `health`     | `int`    | ??          |
| `symbol`     | `string` | ??          |

The following is an example enemy, an orc:

    var Orc : enemy = {
        var difficulty : int = 1
        var health : int = 200
        var symbol : string= "O"
    }

#### Floor

#### Wall

#### Room

## Operators

CARL supports the following arithmetic operators:

| Operator | Example     | Explanation                                                                                                                                           |
|----------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| `*`      | `foo * bar` | Multiplies the two operands.                                                                                                                          |
| `/`      | `foo / bar` | Divides the first operand by the second.                                                                                                              |
| `%`      | `foo % bar` | Divides the first operand by the second, and returns the remainder from the operation.                                                                |
| `+`      | `foo + bar` | Adds the two operands.                                                                                                                                |
| `-`      | `foo - bar` | Subtracts the first operand by the second.                                                                                                            |
| `..`     | `foo..bar`  | Returns a random number between the two operands. Will return an integer if both operands are integers, or a float if one of the operands are floats. |

CARL supports the following relational operators:

| Operator | Example      | Explanation                                                              |
|----------|--------------|--------------------------------------------------------------------------|
| `<`      | `foo < bar`  | Returns `true` if the first operand is less than the second.             |
| `<=`     | `foo <= bar` | Returns `true` if the first operand is less than or equal to the second. |
| `==`     | `foo == bar` | Returns `true` if the first operand is equal to the second.              |
| `>=`     | `foo >= bar` | Returns `true` if the first operand is more than or equal to the second. |
| `>`      | `foo > bar`  | Returns `true` if the first operand is more than the second.             |
| `!=`     | `foo != bar` | Returns `true` if the first operand is not equal to the second.          |

CARL supports the following logical operators:

| Operator | Example       | Explanation                                                                                                                     |
|----------|---------------|---------------------------------------------------------------------------------------------------------------------------------|
| `!`      | `!foo`        | Negation operator. If the operand evaluates to `true`, returns `false`, and vice versa.                                         |
| `AND`    | `foo AND bar` | Returns `true` if the first operand and the second operand are both `true`. Returns `false` otherwise.                          |
| `OR`     | `foo OR bar`  | Returns `true` if either the first operand or the second operand are `true`. Returns `false` only if both operands are `false`. |

## Control structures
CARL has two main ways to control program flow: if statements, and while 
loops.

### If statements
An if statement allows your program to choose whether to execute one 
or more pieces of code, based on one or more conditionals. The 
following is the structure of a simple if statement:

    if <condition> {
        <code>
    }

If `<condition>` is true, then `<code>` will execute. Otherwise, the 
interpreter will skip `<code>` and simply continue interpretation 
after the statement.

An if statement can also have an else clause, or one or more else-if 
clauses. If the first condition is false, the interpreter will go 
through each else-if statement's condition, until one of them 
evaluates to `true`. If all of them are `false`, the interpreter will 
execute the code in the else clause. If there is no else clause, the 
interpreter will continue interpretation after the whole structure.
Here is an example of an if statement with several else-if's and 
else's:

    if x == 5 {
        print("x is five")
    } else if x == 10 {
        print("x is ten")
    } else {
        print("x is neither five or ten")
    }

Note that parentheses around the condition are not required.

### While loops
While loops have a similar structure to if statements:

    while <condition> {
        <code>
    }

It repeats `<code>` for as long as `<condition>` holds.
Here is an example of a while loop, that will print the numbers 1 to 5:

    var i: int = 1
    while i <= 5 {
        print(i)
        i = i + 1
    }

## Functions

### Defining custom functions

### Builtin functions


#### print

#### generateMap

#### generateRooms

#### generateCorridors

#### generateSpawns

#### printMap

#### writeToFile