import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileWriter {
    private Path filePath;

    public FileWriter(Path filePath) {
        this.filePath = filePath;
    }

    void writeList(List<StockItem> list) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (StockItem stockItem : list) {
                writer.write(stockItem.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "I/O错误");
        }
    }

    void writeRecord(StockItem item) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(item.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "I/O错误");
        }
    }
}
