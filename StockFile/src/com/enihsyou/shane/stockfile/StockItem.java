package com.enihsyou.shane.stockfile;

class StockItem{
    private String name;
    private String id;

    StockItem(String id, String name) throws NumberFormatException {
        int input = Integer.parseInt(id);
        if (id.length() != 6||input >= 1000000 || input <= 0) {
            throw new NumberFormatException("输入代码不合法");
        }
        this.id = id;
        this.name = name;

    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "\t" + name;
    }
}
