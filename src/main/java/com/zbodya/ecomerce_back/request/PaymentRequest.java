package com.zbodya.ecomerce_back.request;

public class PaymentRequest {

    private String paymentId;
    private String payerId;

    public PaymentRequest(String paymentId, String payerId) {
        this.paymentId = paymentId;
        this.payerId = payerId;
    }

    public PaymentRequest() {
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
