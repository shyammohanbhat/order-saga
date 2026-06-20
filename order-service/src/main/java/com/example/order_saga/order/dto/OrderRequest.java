package com.example.order_saga.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
  @JsonProperty("customer_id")
  private String customerId;

  private String status;
  private Double amount;
}
