package com.receiptprocessor.repository;

import com.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {
    private static final int MAX_CACHE_SIZE = 1000;

    private final Map<String, Receipt> receiptList = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, Receipt> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    private final Map<String, Integer> pointsList = new LinkedHashMap<>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
            return size() > MAX_CACHE_SIZE;
        }
    };

    public void saveReceipt(String id, Receipt receipt, int points) {
        receiptList.put(id, receipt);
        pointsList.put(id, points);
    }

    public Integer getPoints(String id) {
        return pointsList.get(id);
    }
}
