package com.zbodya.ecomerce_back.response;

public class PaymentLinkResponse {

    private String paymentLinkUrl;
    private String paymentLinkId;

    public PaymentLinkResponse(String paymentLinkUrl, String paymentLinkId) {
        this.paymentLinkUrl = paymentLinkUrl;
        this.paymentLinkId = paymentLinkId;
    }

    public PaymentLinkResponse() {

    }

    public String getPaymentLinkUrl() {
        return paymentLinkUrl;
    }

    public void setPaymentLinkUrl(String paymentLinkUrl) {
        this.paymentLinkUrl = paymentLinkUrl;
    }

    public String getPaymentLinkId() {
        return paymentLinkId;
    }

    public void setPaymentLinkId(String paymentLinkId) {
        this.paymentLinkId = paymentLinkId;
    }
}
