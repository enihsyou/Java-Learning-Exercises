public class J01 {

    public static void main(String[] args) {
        int beer = 23;
        int nomi = 19;
        int total = 823;
        for (int i = 0; i < total / beer + 1; i++) {
            for (int j = 0; j < total / nomi + 1; j++) {
                if (beer * i + nomi * j == total && i < j) {
                    System.out.println("啤酒:" + i + " 饮料:" + j);
                }
            }
        }

    }
}
