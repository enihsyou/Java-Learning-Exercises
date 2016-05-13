class Number {
    private int n1, n2;

    Number(int a, int b) {
        this.n1 = a;
        this.n2 = b;
    }

    Number() {}

    int addition() {
        return this.n1 + this.n2;
    }

    int subtration() {
        return this.n1 - this.n2;
    }

    double multiplication() {
        return this.n1 * this.n2;
    }

    double division() {
        return (double) this.n1 / this.n2;
    }
}

public class b04 {
    public static void main(String args[]) {
        Number a = new Number(10, 0);
        System.out.println(a.addition());
        System.out.println(a.subtration());
        System.out.println(a.multiplication());
        System.out.println(a.division());
    }
}
