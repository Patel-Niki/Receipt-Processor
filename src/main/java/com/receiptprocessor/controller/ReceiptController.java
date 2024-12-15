package com.receiptprocessor.controller;

import com.receiptprocessor.model.Receipt;
import com.receiptprocessor.model.response.ErrorResponse;
import com.receiptprocessor.model.response.PointsResponse;
import com.receiptprocessor.model.response.ReceiptResponse;
import com.receiptprocessor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService service;

    public ReceiptController(ReceiptService service) {
        this.service = service;
    }

    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder receiptError = new StringBuilder("The receipt is invalid: ");
            result.getAllErrors().forEach(error -> receiptError.append(error.getDefaultMessage()).append(""));
            return ResponseEntity.badRequest().body(new ErrorResponse(receiptError.toString()));
        }

        String id = service.processReceipt(receipt);
        return ResponseEntity.ok(new ReceiptResponse(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        Integer points = service.getPoints(id);
        if (points == null) {
            StringBuilder pointsError = new StringBuilder("No receipt found for that ID");
            return ResponseEntity.badRequest().body(new ErrorResponse(pointsError.toString()));
        }
        return ResponseEntity.ok(new PointsResponse(points));
    }
}
