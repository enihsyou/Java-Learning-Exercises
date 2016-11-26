import java.util.Comparator;
import java.util.TreeSet;

public class StockList {
    private TreeSet<StockItem> stocks;

    public TreeSet<StockItem> getStocks() {
        return stocks;
    }

    public StockList() {
        stocks = new TreeSet<>(Comparator.comparing(StockItem::getId));
    }

    public boolean addStock(StockItem item) {
        return stocks.add(item);
    }
}
