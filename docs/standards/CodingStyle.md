# Coding Style

## Naming

All inner class variable names should be camel case.

```java
recipeId
```

All Interface classes shall be prefixed with 'I'.

```java
IDatabase
```

## Format

All opening curly brackets shall sit _beside_ the line, not under, followed by a newline.

```java
public boolean doMethod() {
```

### Tabs
Use the tab key to indent your code.

### Braces

Matching braces always line up vertically in the same column as their construct.

```java
if(condition) {
    do thing
}
```

All classes shall be setup as follows:

```java
class Order {
    //fields

    //constructors

    //methods

    @override
    //methods
}
```

### Whitespaces and new lines

Don't do this:
```java
public void someMethod()        {
    int  x =     0;
    
    
    
    
    
    System.out.println(x);   
    
    
    
}
```