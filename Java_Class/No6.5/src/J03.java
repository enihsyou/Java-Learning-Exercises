/**
 * 3. 继承练习：
 * 创建一个名称为MainPackage的包，使它包含MainClass和MainSubClass类。
 * MainClass类应当包含变量声明，其值从构造函数中输出。
 * MainSubClass类从MainClass派生而来。
 * 试执行下列操作：创建一个名称为SamePackage的类，
 * 使它导入上述包，并创建一个MainSubClass类的对象
 */


import MainPackage.MainSubClass;

public class J03 {
    public static void main(String args[]) {
        MainSubClass a = new MainSubClass();
    }
}
