package com.sameperson.logic;

public class Calculator {

    public double calculate(String uri) {
        String[] sides = uri.split("[?]");
        String[] operations = sides[1].split("[&]");
        String operation = operations[0].split("[=]")[1];

        int operandOne = Integer.parseInt(operations[1].split("=")[1]);
        int operandTwo = Integer.parseInt(operations[2].split("=")[1]);

        if(operation.equals("add")) {
            return add(operandOne, operandTwo);
        } else if(operation.equals("multiply")) {
            return multiply(operandOne, operandTwo);
        } else if(operation.equals("substract")) {
            return subtract(operandOne, operandTwo);
        } else if(operation.equals("divide")) {
            return divide(operandOne, operandTwo);
        }

        return 0;
    }

    private int add(int o1, int o2) {
        return o1+o2;
    }

    private int multiply(int o1, int o2) {
        return o1*o2;
    }

    private int subtract(int o1, int o2) {
        return o1-o2;
    }

    private double divide(int o1, int o2) {
        return (double)o1/o2;
    }

}
