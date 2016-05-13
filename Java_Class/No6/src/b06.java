class b06_C {
    private int a;

    b06_C(int a) {
        this.a = a;
    }

    b06_C() {
        this.a = 0;
    }

    void setNumber(int a) {
        this.a = a;
    }

    int getNumber() {
        return this.a;
    }
}

public class b06 {
    public static void main(String args[]) {
        b06_C a = new b06_C(4);
        a.setNumber(3);
        System.out.println(a.getNumber());
    }
}
