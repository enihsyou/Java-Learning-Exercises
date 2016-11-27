package com.enihsyou.shane.stockfile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class StockJsonParser {
    private URL url;
    private ObjectMapper mapper;
    private JsonNode root;

    public StockJsonParser(URL url) throws IOException {
        this.url = url;
        mapper = new ObjectMapper();
        root = mapper.readTree(url);
    }

    StockList parse() {
        StockList fileList = new StockList();
        for (Iterator<Map.Entry<String, JsonNode>> it = root.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> record = it.next();
            String newKey = StockJsonParser.removePrefix(record.getKey(), 2);
            String newValue = record.getValue().textValue();
            fileList.addStock(new StockItem(newKey, newValue));
        }
        return fileList;
    }

    /**
     * API的数据有前缀，比如sh000001，这里手动移除掉。
     *
     * @param raw    原始字符串
     * @param number 要切除的前几个字符数量
     *
     * @return 移除了前缀的字符串
     */
    private static String removePrefix(String raw, int number) {
        return raw.substring(number, raw.length());
    }

    JsonNode getRoot() {
        return root;
    }

    private String removePrefix(String raw, String prefix) {
        return raw.replaceFirst("^" + prefix, "");
    }
}

