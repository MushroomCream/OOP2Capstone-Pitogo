package com.example.capstonepitogo.GymMembership;

public class RegularPricing implements PricingStrategy {
    @Override
    public double calculatePrice(double basePrice) {
        return basePrice;
    }
}