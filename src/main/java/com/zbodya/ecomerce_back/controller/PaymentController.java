package com.zbodya.ecomerce_back.controller;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.zbodya.ecomerce_back.exception.OrderException;
import com.zbodya.ecomerce_back.exception.UserException;
import com.zbodya.ecomerce_back.model.Order;
import com.zbodya.ecomerce_back.model.OrderStatus;
import com.zbodya.ecomerce_back.model.PaymentStatus;
import com.zbodya.ecomerce_back.repository.OrderRepository;
import com.zbodya.ecomerce_back.response.ApiResponse;
import com.zbodya.ecomerce_back.response.PaymentLinkResponse;
import com.zbodya.ecomerce_back.service.OrderService;
import com.zbodya.ecomerce_back.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

  @PostMapping("/payments/{orderId}")
  public ResponseEntity<PaymentLinkResponse> createPaymentLink(
      @PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
      throws RazorpayException, UserException, OrderException {
        Order order = orderService.findOrderById(orderId);
        try{
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_kTsRSaDC8hwztX", "LieoD1s9mxMlv569PcgRDMcU");
            JSONObject paymentLinkRequest =  new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice());
            paymentLinkRequest.put("currency", "USD");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            customer.put("contact", order.getUser().getMobile());

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);

            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("callback_url", "http://localhost:4200/payment-success/order_id=" + order.getId());
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse(paymentLinkUrl, paymentLinkId);
            return new ResponseEntity<>(paymentLinkResponse, HttpStatus.CREATED);
        }catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }
    }

    public ResponseEntity<ApiResponse> updatePayment(
            @RequestParam(name = "payment_id") String paymentId,
            @RequestParam(name = "order_id") Long orderId) throws RazorpayException, OrderException {
        RazorpayClient razorpayClient = new RazorpayClient("rzp_test_kTsRSaDC8hwztX", "LeqKpXR90ieK6v");
        Order order = orderService.findOrderById(orderId);
        try{
            Payment payment = razorpayClient.payments.fetch(paymentId);
            if(payment.get("status").equals("captured")){
                order.setOrderStatus(OrderStatus.PLACED);
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setPaymentStatus(PaymentStatus.COMPLETED);
                orderRepository.save(order);
            }
            ApiResponse apiResponse = new ApiResponse("Your order placed", true);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

}
