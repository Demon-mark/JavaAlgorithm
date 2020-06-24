import java.util.Stack;

/**
 * 逆波兰表达式计算器
 *
 * 利用转换中缀表达式的类实现中缀表达式和后缀表达式的统一计算
 * （仅支持 + - * / 混合运算）
 *
 * @author Amosen
 * @create 2020-06-24 15:04
 */
public class InversePolishCalculator {
    Stack<Double> numberStack = new Stack<>();
    Stack<String> operatorStack = new Stack<>();

    private String handlePostfixExpression(String expression) {
        String arrangedExpression = "";
        expression = expression.trim();
        for(int index = 0; index < expression.length(); index++) {
            if(expression.charAt(index) != ' ') {
                for (int i = 0; i <= expression.length() - index; i++) {
                    if(index + i < expression.length()) {
                        if (expression.charAt(index + i) == ' ') {
                            arrangedExpression = arrangedExpression + expression.substring(index, index + i) + " ";
                            index = index + i;
                            break;
                        }
                    } else {
                        arrangedExpression += expression.substring(index, expression.length());
                    }
                }
            }
        }
        return arrangedExpression.trim();
    }

    private String handleInfixExpression(String expression) {
        TransferInfixToPostfix tran = new TransferInfixToPostfix();
        String arrangedExpression = "";
        arrangedExpression = tran.transfer(expression);
        return arrangedExpression;
    }

    private boolean judgeExpression(String expression) {
        boolean isInfix = true;
        String[] split = expression.split(" ");
        for(int index = 0; index < split.length - 1; index++) {
            if(isOperator(split[index]) && isOperator(split[index + 1])) {
                isInfix = false;
                break;
            }
            if(isNumber(split[index]) && isNumber(split[index + 1])) {
                isInfix = false;
                break;
            }
            if(expression.contains("(") || expression.contains(")")) {
                break;
            }
        }
        return isInfix;
    }

    private String arrangeExpression(String expression) {
        if(judgeExpression(expression)) {
            return handleInfixExpression(expression);
        } else {
            return handlePostfixExpression(expression);
        }
    }

    private String[] getFirstItem(String subexpression) {
        int index = 0;
        StringBuilder firstItem = new StringBuilder();
        for(index = 0; index < subexpression.length(); index++) {
            if(subexpression.charAt(index) != ' ') {
                firstItem.append(subexpression.charAt(index));
                continue;
            }
            break;
        }

        return new String[]{firstItem.toString(), String.valueOf(index)};
    }

    private boolean isNumber(String item) {
        boolean isNum = true;
        for(int index = 0; index < item.length(); index++) {
            if(!((item.charAt(index) >= 48 && item.charAt(index) <= 57) || item.charAt(index) == '.')) {
                isNum = false;
                break;
            }
        }
        return isNum;
    }

    private boolean isOperator(String item) {
        return item.equals("+") || item.equals("-") || item.equals("*") || item.equals("/");
    }

    private double cal(double number1, double number2, String operator) {
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number2 - number1;
            case "*":
                return number1 * number2;
            case "/":
                return number2 / number1;
        }
        throw new RuntimeException("Wrong Expression!");
    }

    public double calculate(String expression) {
        expression = arrangeExpression(expression);
        while(expression.length() > 0) {
            String[] firstItemAndBit = getFirstItem(expression);
            String firstItem = firstItemAndBit[0];
            int bit = Integer.parseInt(firstItemAndBit[1]);
            expression = expression.substring(bit, expression.length()).trim();
            if(isNumber(firstItem)) {
                try {
                    numberStack.push(Double.parseDouble(firstItem));
                }catch (Exception e) {
                    System.out.println("表达式错误！");
                    return -1;
                }
            } else if(isOperator(firstItem)) {
                try {
                    double number1 = numberStack.pop();
                    double number2 = numberStack.pop();
                    double result = cal(number1, number2, firstItem);
                    numberStack.push(result);
                } catch (Exception e) {
                    System.out.println("表达式错误！");
                    return -1;
                }
            } else {
                throw new RuntimeException("Wrong Expression!");
            }
        }
        return numberStack.pop();
    }
}
