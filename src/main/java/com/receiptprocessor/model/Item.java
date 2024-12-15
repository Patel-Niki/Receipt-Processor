package com.receiptprocessor.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Item {
    @NotBlank(message = "Item Description cannot be blank")
    private String shortDescription;

    @NotBlank(message = "Item Price cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be in the format 'xx.xx'")
    private String price;
}