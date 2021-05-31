## About

Java implementation of a memory allocator which takes in commands for allocating and freeing memory using best-fit and first-fit algorithms.

It maintains two data structures (AVL trees or BS trees or Doubly Linked Lists) to denote memory that has already been allocated elsewhere and memory that is free.

The data structure containing allocated memory has the starting address of the memory as the key and the data structure with the free memory contains the size of the memory block as the key.

## Using makefile
```make all```

To compile your .java files

```make clean```

To remove the generated .class files

## Using run.sh
```run.sh {input_file} {output_file}```

Example:
```run.sh test.in res.out```

Both arguments are optional, inputfile is the file containing the test cases and output file is where you want the result to be written into.
In the case any argument is missing, console is used for input or output.

A res_gold.out has been added which can be used to compare your results against the standard results.

## Format of test file

number of test cases

size

number of commands

command1

command2

...

size (next test case)

number of commands

command1

command2

...


One sample test file test.in is attached alongside

```Allocate Size```

```Free Address```

```Defragment -1```

This is the format for commands required

