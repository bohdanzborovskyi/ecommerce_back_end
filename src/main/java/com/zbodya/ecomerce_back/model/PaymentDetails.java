package com.zbodya.ecomerce_back.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

// @Embeddable
public class PaymentDetails {

  private String paymentId;
  private String paymentMethod;
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;
  private String razorpayPaymentLinkId;
  private String razorpayPaymentLinkReferenceId;
  private String razorpayPaymentLinkStatus;
  private String razorpayPaymentId;

  public PaymentDetails() {}

  public PaymentDetails(
      String paymentMethod,
      PaymentStatus paymentStatus,
      String paymentId,
      String razorpayPaymentLinkId,
      String razorpayPaymentLinkReferenceId,
      String razorpayPaymentLinkStatus,
      String razorpayPaymentId) {
    this.paymentMethod = paymentMethod;
    this.paymentStatus = paymentStatus;
    this.paymentId = paymentId;
    this.razorpayPaymentLinkId = razorpayPaymentLinkId;
    this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
    this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
    this.razorpayPaymentId = razorpayPaymentId;
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

  public String getRazorpayPaymentLinkId() {
    return razorpayPaymentLinkId;
  }

  public void setRazorpayPaymentLinkId(String razorpayPaymentLinkId) {
    this.razorpayPaymentLinkId = razorpayPaymentLinkId;
  }

  public String getRazorpayPaymentLinkReferenceId() {
    return razorpayPaymentLinkReferenceId;
  }

  public void setRazorpayPaymentLinkReferenceId(String razorpayPaymentLinkReferenceId) {
    this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
  }

  public String getRazorpayPaymentLinkStatus() {
    return razorpayPaymentLinkStatus;
  }

  public void setRazorpayPaymentLinkStatus(String razorpayPaymentLinkStatus) {
    this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
  }

  public String getRazorpayPaymentId() {
    return razorpayPaymentId;
  }

  public void setRazorpayPaymentId(String razorpayPaymentId) {
    this.razorpayPaymentId = razorpayPaymentId;
  }
}
