package com.example;
@Data
@Builder
public class BaseSheet {
    String internalCode;
    String barCode; 
    String name; 
    String category; 
    String description; 
    Double cost; 
    Double price; 
    String current_stock; 
    String type; 
    String type2;
    public String getInternalCode() {
        return internalCode;}
    public String getBarCode() {
        return barCode;}
    public String getName() {
        return name;}
    public String getCategory() {
        return category;}
    public String getDescription() {
        return description;}
    public Double getCost() {
        return cost;}
    public Double getPrice() {
        return price;}
    public String getCurrent_stock() {
        return current_stock;}
    public String getType() {
        return type;}
    public String getType2() {
        return type2;}
    public static BaseSheet builder() {
        return null;
    } 
}
