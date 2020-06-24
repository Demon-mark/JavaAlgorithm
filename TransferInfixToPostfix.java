import java.util.Stack;

/**
 * 此类下的transfer方法设法将中缀表达式转换为后缀表达式
 * （仅支持 + - * / 混合运算）
 * @author Amosen
 * @create 2020-06-24 21:27
 */
public class TransferInfixToPostfix {
    Stack<String> operatorStack = new Stack<>();
    Stack<String> numberStack = new Stack<>();

    public boolean isNumber(String item) {
        boolean isNum = true;
        for (int index = 0; index < item.length(); index++) {
            if (!((item.charAt(index) >= 48 && item.charAt(index) <= 57) || item.charAt(index) == '.')) {
                isNum = false;
                break;
            }
        }
        return isNum;
    }

    public boolean isOperator(String item) {
        return item.equals("+") || item.equals("-")
                || item.equals("*") || item.equals("/")
                || item.equals("(") || item.equals(")");
    }

    public String checkExpression(String expression) {
        String[] checkingExpressionList = expression.split(" ");
        String wrongDetails = "";
        for (int index = 0; index < checkingExpressionList.length - 1; index++) {
            if (isNumber(checkingExpressionList[index]) && isNumber(checkingExpressionList[index + 1])) {
                wrongDetails = "连续两个数字之间需要用运算符连接";
                break;
            } else if (isOperator(checkingExpressionList[index]) && isOperator(checkingExpressionList[index + 1])) {
                wrongDetails = "不能连续出现两个运算符";
                break;
            }
        }
        if (wrongDetails.length() == 0) {
            int leftBracket = 0;
            int rightBracket = 0;
            for (int index = 0; index < expression.length(); index++) {
                if (expression.charAt(index) == '(') {
                    leftBracket++;
                } else if (expression.charAt(index) == ')') {
                    rightBracket++;
                } else if (!(isNumber(expression.charAt(index) + "")
                        || isOperator(expression.charAt(index) + "")
                        || expression.charAt(index) == '('
                        || expression.charAt(index) == ')'
                        || expression.charAt(index) == ' ')) {
                    wrongDetails = "未知的符号：'" + expression.charAt(index) + "'";
                    break;
                }
            }
            if (leftBracket != rightBracket) {
                wrongDetails = "括号不匹配";
            }
        }
        return wrongDetails;
    }

    private String arrangeExpression(String expression) {
        expression = expression.trim();
        String result = "";
        String[] splitExpression = expression.split(" ");
        for (String piece : splitExpression) {
            result += piece;
        }
        return result;
    }

    private String[] getFirstItem(String subExpression) {
        String firstItem = "";
        int index = 0;
        if (isNumber(subExpression.charAt(0) + "")) {
            for (index = 0; index <= subExpression.length(); index++) {
                if (index < subExpression.length()) {
                    if (isOperator(subExpression.charAt(index) + "")) {
                        firstItem = subExpression.substring(0, index);
                        break;
                    }
                } else {
                    firstItem = subExpression.substring(0, index);
                }
            }
        } else if (isOperator(subExpression.charAt(0) + "") && subExpression.charAt(0) != '(') {
            for (index = 0; index < subExpression.length(); index++) {
                if (isNumber(subExpression.charAt(index) + "")) {
                    firstItem = subExpression.substring(0, index);
                    break;
                }
            }
        } else if (subExpression.charAt(0) == '(') {
            firstItem = "(";
            index = 1;
        }
        if (subExpression.charAt(0) == ')') {
            firstItem = ")";
            index = 1;
        }
        return new String[]{firstItem, String.valueOf(index)};
    }

    private int getPriority(String operator) {
        if (operator.equals("+")) {
            return 1;
        } else if (operator.equals("-")) {
            return 1;
        } else if (operator.equals("*") || operator.equals("x")) {
            return 2;
        } else if (operator.equals("/")) {
            return 2;
        }
        return 0;
    }

    public String transfer(String expression) {
        String result = "";
        String wrong = checkExpression(expression);
        if (wrong.length() != 0) {
            throw new RuntimeException(wrong);
        }
        expression = arrangeExpression(expression);
        while (expression.length() > 0) {
            String[] firstItemAndBit = getFirstItem(expression);
            String firstItem = firstItemAndBit[0];
            int bit = Integer.parseInt(firstItemAndBit[1]);
            if (bit < expression.length()) {
                expression = expression.substring(bit, expression.length());
            } else {
                expression = "";
            }
            if (isNumber(firstItem)) {
                numberStack.push(firstItem);
            } else if (isOperator(firstItem)) {
                if (operatorStack.empty() || firstItem.equals("(")) {
                    operatorStack.push(firstItem);
                } else if (firstItem.equals(")")) {
                    while (!operatorStack.lastElement().equals("(")) {
                        numberStack.push(operatorStack.pop());
                    }
                    operatorStack.pop();
                } else if (getPriority(firstItem) <= getPriority(operatorStack.lastElement())) {
                    numberStack.push(operatorStack.pop());
                    operatorStack.push(firstItem);
                } else {
                    operatorStack.push(firstItem);
                }
            }
        }
        while (!operatorStack.empty()) {
            numberStack.push(operatorStack.pop());
        }
        while (!numberStack.empty()) {
            result = numberStack.pop() + " " + result;
        }
        return result.trim();
    }
}
