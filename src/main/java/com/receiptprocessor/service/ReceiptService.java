package com.receiptprocessor.service;

import com.receiptprocessor.model.Receipt;
import com.receiptprocessor.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptService {
    private final ReceiptRepository repository;

    public ReceiptService(ReceiptRepository repository) {
        this.repository = repository;
    }

    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        repository.saveReceipt(id, receipt, points);
        return id;
    }

    public Integer getPoints(String id) {
        return repository.getPoints(id);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;
        String total = receipt.getTotal();

        // Rule 1: Alphanumeric characters in retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: Round dollar total
        if (total != null && total.matches("\\d+\\.00")) {
            points += 50;
        }

        // Rule 3: Total multiple of 0.25
        if (total != null && Double.parseDouble(total) % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: Description length multiple of 3
        for (var item : receipt.getItems()) {
            int descLength = item.getShortDescription().trim().length();
            if (descLength % 3 == 0) {
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // Rule 6: Odd day
        if (Integer.parseInt(receipt.getPurchaseDate().split("-")[2]) % 2 != 0) {
            points += 6;
        }

        // Rule 7: Time between 2:00pm and 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        if (hour == 14 || (hour == 15 && minute < 60)) {
            points += 10;
        }

        return points;
    }
}