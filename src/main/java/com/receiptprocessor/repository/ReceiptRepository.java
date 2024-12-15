package com.receiptprocessor.repository;

import com.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {
    private final Map<String, Receipt> receiptList = new HashMap<>();
    private final Map<String, Integer> pointsList = new HashMap<>();

    public void saveReceipt(String id, Receipt receipt, int points) {
        receiptList.put(id, receipt);
        pointsList.put(id, points);
    }

    public Integer getPoints(String id) {
        return pointsList.get(id);
    }
}