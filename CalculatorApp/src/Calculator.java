//// Calculator.java
//public class Calculator {
//
//    public double add(double a, double b) {
//        return a + b;
//    }
//
//    public double subtract(double a, double b) {
//        return a - b;
//    }
//
//    public double multiply(double a, double b) {
//        return a * b;
//    }
//
//    public double divide(double a, double b) {
//        if (b == 0) {
//            throw new IllegalArgumentException("Cannot divide by zero");
//        }
//        return a / b;
//    }
//}



class Calculator {
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        return a / b;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double percentage(double value) {
        return value / 100;
    }

    public double squareRoot(double value) {
        return Math.sqrt(value);
    }
}