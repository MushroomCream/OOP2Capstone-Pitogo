package com.example.capstonepitogo.GymMembership;

public class StudentDiscountPricing implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * 0.90; // 10% off
    }
}