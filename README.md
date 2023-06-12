# Piva Interpreter #
___
This is my second attempt to write a new language called _Piva_. The earlier compiler I made _(See my [repository](https://github.com/WolfBoy55014/Piva-Compiler))_ was **_hideous_**. Though, I have now found new ground with this beautiful [blog by Ruslan](https://ruslanspivak.com/lsbasi-part1/) that explains how to make a simple interpreter. Thank you Ruslan!

To give you (and me) an idea of how this language will look, here is some example code:

``` java
main main: // Initialized a main function w/ name 'main'

x := 5;
// When initializing a variable with ':=', the interpreter implies the variable type
// Piva is still statically typed, just you don't have to specify

int y = 6 * 7;
// Another way to declare a variable but this time specifying the type

// Resigning a variable should be familiar
x = 7;
y = 3 + 5;

printInput(y);
. // a function is closed with a period

func printInput(int input):
// New functions can also be in different files,
// those files are specified on run
// All functions are public

println(input);
.
```
More examples will be found under [examples](https://github.com/WolfBoy55014/Piva-Interpreter/tree/master/examples).
___
### Variable Types ###
- Booleans _(bool)_
- Characters _(char)_
- Integers _(int)_
- Floats _(float)_
- Doubles _(double)_
- Strings _(str)_


**If you have any cool ideas, or tips, please open an issue.**
