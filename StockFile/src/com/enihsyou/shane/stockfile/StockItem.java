package com.enihsyou.shane.stockfile;

public class StockItem{
    private String name;
    private String id;

    public StockItem(String id, String name) throws NumberFormatException {
        int input = Integer.parseInt(id);
        if (id.length() != 6||input >= 1000000 || input <= 0) {
            throw new NumberFormatException("输入代码不合法");
        }
        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "\t" + name;
    }
}
