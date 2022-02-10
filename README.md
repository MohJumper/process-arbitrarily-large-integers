# process-arbitrarily-large-integers

create a Java program to add and subtract arbitrarily large integers. It will take un-parenthesized arithmetic expressions with two operands, such as the following, one per line.

## Example of large integer

```
12345678902938652837528754 + -2873464896598346539846539871245254
```

# Notes

Arithmetic review: 8 - (-3) = 11; -8 - (-3) = -5; -8 - (3) = -11;

Attacking the problem:
Figure out which is the larger operand and set "bigger" and "smaller".
Note that you might need to swap the operands before performing an operation. For instance, if the absolute value of the subtrahend is larger than the minuend, you must reverse them before proceeding.

Decide if you need to add or subtract: 8 + -3 is actually subtraction even though the operator is addition.
Your program might compute a negative result. You must account for this possibility and place the minus at the start of your result.
