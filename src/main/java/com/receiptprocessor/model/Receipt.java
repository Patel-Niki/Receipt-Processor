package com.receiptprocessor.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class Receipt {
    @NotBlank(message = "Retailer cannot be blank")
    private String retailer;

    @NotBlank(message = "Purchase date cannot be blank")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Purchase date must be in the format YYYY-MM-DD")
    private String purchaseDate;

    @NotBlank(message = "Purchase time cannot be blank")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Purchase time must be in the format HH:MM")
    private String purchaseTime;

    @NotBlank(message = "Total cannot be blank")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be in the format of 'xx.xx'")
    private String total;

    @NotEmpty(message = "Item List can not be empty")
    @Valid
    private List<Item> items;
}
