package com.zbodya.ecomerce_back.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

// @Embeddable
public class PaymentDetails {

  private String paymentId;
  private String paymentMethod;
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;
  private String payerId;

  public PaymentDetails() {}

  public PaymentDetails(
      String paymentMethod,
      PaymentStatus paymentStatus,
      String paymentId,
      String payerId) {
    this.paymentMethod = paymentMethod;
    this.paymentStatus = paymentStatus;
    this.paymentId = paymentId;
    this.payerId = payerId;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public String getPayerId() {
    return payerId;
  }

  public void setPayerId(String payerId) {
    this.payerId = payerId;
  }


}
