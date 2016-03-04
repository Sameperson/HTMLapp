package com.sameperson;

import com.sameperson.logic.Calculator;
import com.sameperson.logic.ServerLogic;

public class Main {

    public static void main(String[] args) {

        ServerLogic serverLogic = new ServerLogic();
        serverLogic.start();

        //Calculator calc = new Calculator();
        //double result = calc.calculate("calculate?operation=multiply&operand1=5&operand2=10");
        //System.out.println(result);

    }
}