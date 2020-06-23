import java.util.Stack;

/**
 * 简易计算器实现中缀表达式计算
 * 实现 + - * / 四则运算
 *
 * @author Amosen
 * @create 2020-06-22 19:40
 */

public class Calculator {
    private Stack numberStack = new Stack();
    private Stack operatorStack = new Stack();

    public static final int PLUS = 1;
    public static final int MINUS = 1;
    public static final int DIVISION = 2;
    public static final int MULTIPLY = 2;

    public boolean isOperator(char operator) {
        return operator == '*' || operator == '/' || operator == '+' || operator == '-';
    }

    public boolean isNumber(char Number) {
        return (Number >= 48 && Number <= 57);
    }

    public boolean isError(char character) {
        return !(isNumber(character) || isOperator(character));
    }

    public int getPriority(char operator) {
        if (!isOperator(operator)) {
            throw new RuntimeException("Unknown operator for " + operator);
        }
        if(operator == '+') {
            return PLUS;
        }
        if(operator == '-') {
            return MINUS;
        }
        if(operator == '*') {
            return MULTIPLY;
        }
        if(operator == '/') {
            return DIVISION;
        }
        return -1;
    }

    private double cal(double Number1, double Number2, char operator) {
        if(operator == '*') {
            return Number1 * Number2;
        } else if(operator == '/') {
            return Number2 / Number1;
        } else if(operator == '+') {
            return Number1 + Number2;
        } else if(operator == '-') {
            return Number2 - Number1;
        } else {
            throw new RuntimeException("Unknown operator for " + operator);
        }
    }

    private int transferCharToInt(char character) {
        if(isNumber(character)) {
            return character - 48;
        }
        throw new ExpressionError();
    }

    private double[] getFirstNumber(String subexpression) {
        if(!isNumber(subexpression.charAt(0))) {
            throw new ExpressionError();
        }
        double number = 0;
        double bit = 0;
        boolean isDecimal = false;
        int index = 0;
        for(index = 0; index < subexpression.length(); index++) {
            char curNumberChar = subexpression.charAt(index);
            int curNumber = 0;
            if(isOperator(curNumberChar)) {
                break;
            }
            if(curNumberChar == '.') {
                isDecimal = true;
            }
            if(curNumberChar != '.') {
                curNumber = transferCharToInt(curNumberChar);
            } else {
                continue;
            }
            if(isNumber(curNumberChar) && !isDecimal) {
                number = number * 10 + curNumber;
            }
            if(isNumber(curNumberChar) && isDecimal) {
                number = number + curNumber * 0.1;
            }

        }
        if(!operatorStack.empty()) {
            if ((char) operatorStack.lastElement() == '-') {
                operatorStack.pop();
                operatorStack.push('+');
                number = -number;
            }
            if ((char) operatorStack.lastElement() == '/') {
                operatorStack.pop();
                operatorStack.push('*');
                number = 1 / number;
            }
        }
        return new double[] {number, index};
    }

    private char getOperator(String subexpression) {
        int index = 0;
        for(index = 0; index < subexpression.length(); index++) {
            char curOperatorChar = subexpression.charAt(index);
            if(isOperator(curOperatorChar)) {
                return curOperatorChar;
            }
        }
        throw new ExpressionError();
    }

    public double calculate(String expression) {
        char lastOperatorInStack = ' ';
        while(expression.length() > 0) {
            double number[] = getFirstNumber(expression);
            double calNumber = number[0];
            numberStack.push(calNumber);
            expression = expression.substring((int) number[1], expression.length());
            if(expression.length() == 0) {
                break;
            }
            char operator = getOperator(expression);
            expression = expression.substring(1, expression.length());

            if(!operatorStack.empty()) {
                lastOperatorInStack = (char) operatorStack.lastElement();
            }
            if(operatorStack.empty()) {
                operatorStack.push(operator);
            }else if(getPriority(operator) <= getPriority(lastOperatorInStack)) {
                double number1 = (double) numberStack.pop();
                double number2 = (double) numberStack.pop();
                char calOperator = (char) operatorStack.pop();
                double res = cal(number1, number2, calOperator);
                numberStack.push(res);
                operatorStack.push(operator);
            } else if(getPriority(operator) > getPriority(lastOperatorInStack)) {
                operatorStack.push(operator);
            }
        }
        while(!operatorStack.empty()) {
            double number1 = (double) numberStack.pop();
            double number2 = (double) numberStack.pop();
            char operator = (char) operatorStack.pop();
            double res = cal(number1, number2, operator);
            numberStack.push(res);
        }
        return (double) numberStack.pop();
    }



}

class ExpressionError extends RuntimeException {
    public String toString() {
        return "Expression Error";
    }
}
