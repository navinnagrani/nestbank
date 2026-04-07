package com.nestbank.dto;

public class CardPaymentRequest {

    private Long accountId;
    private String cardNumber;
    private String expiry;
    private String cvv;
    private Double amount;

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}