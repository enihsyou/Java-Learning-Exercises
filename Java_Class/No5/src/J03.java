public class J03 {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i < 100; i++) {
            sum += (i * (i + 1) * (i + 2));
        }
        System.out.println("1×2×3+3×4×5+…+99×100×101的值:" + sum);
    }
}