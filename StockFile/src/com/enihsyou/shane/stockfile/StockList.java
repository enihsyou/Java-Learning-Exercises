package com.enihsyou.shane.stockfile;

import java.util.Comparator;
import java.util.TreeSet;

class StockList {
    private TreeSet<StockItem> stocks;

    StockList() {
        stocks = new TreeSet<>(Comparator.comparing(StockItem::getId));
    }

    TreeSet<StockItem> getStocks() {
        return stocks;
    }

    boolean addStock(StockItem item) {
        return stocks.add(item);
    }
}
