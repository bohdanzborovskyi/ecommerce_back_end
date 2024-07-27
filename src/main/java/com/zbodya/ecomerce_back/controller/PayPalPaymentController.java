package com.zbodya.ecomerce_back.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.model.OrderStatus;
import com.zbodya.ecomerce_back.model.PaymentStatus;
import com.zbodya.ecomerce_back.repository.OrderRepository;
import com.zbodya.ecomerce_back.request.PaymentRequest;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.response.PaymentLinkResponse;
import com.zbodya.ecomerce_back.service.OrderService;
import com.zbodya.ecomerce_back.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PayPalPaymentController {

    @Autowired
    private PayPalService payPalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentLinkResponse> payment(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, PayPalRESTException {
        Order order = orderService.findOrderById(orderId);
        double totalPrice = order.getTotalPrice();
        String successUrl = "http://localhost:4200/payment-success?order_id=" + order.getId();
        String canceledUrl = "http://localhost:4200/checkout";
        Payment payment = payPalService.createPayment(totalPrice, "USD", "paypal", "sale", "testing paypal", canceledUrl, successUrl);
        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
        paymentLinkResponse.setPaymentLinkId(payment.getId());
        for(Links link : payment.getLinks()){
            if(link.getRel().equals("approval_url")){
                order.setOrderStatus(OrderStatus.PENDING);
                order.getPaymentDetails().setPaymentStatus(PaymentStatus.PENDING);
                order.getPaymentDetails().setPaymentId(payment.getId());
                orderRepository.save(order);
                paymentLinkResponse.setPaymentLinkUrl(link.getHref());
                return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
    }

    @PostMapping("payment-success/{orderId}")
    public ResponseEntity<ApiResponse> successPayment(@PathVariable Long orderId, @RequestBody PaymentRequest paymentRequest) throws OrderException {
        String paymentId = paymentRequest.getPaymentId();
        String payerId = paymentRequest.getPayerId();
        Order order = orderService.findOrderById(orderId);
        try{
            Payment payment = payPalService.executePayment(paymentId, String.valueOf(payerId));
            if (payment.getState().equals("approved")) {
                order.setOrderStatus(OrderStatus.PLACED);
                order.getPaymentDetails().setPaymentStatus(PaymentStatus.COMPLETED);
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setPayerId(payerId);
                orderRepository.save(order);
                return new ResponseEntity<>(new ApiResponse("Payment success", true), HttpStatus.OK);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error payment: " + e.getMessage());
        }
        return new ResponseEntity<>(new ApiResponse("Payment failed", true), HttpStatus.BAD_REQUEST);
    }
}
