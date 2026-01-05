package com.example.FinanceApp.dto.category;

public class CategoryResponse {
    public String name;
    public String type;
    public boolean isCustom;

    public CategoryResponse(String name,String type, boolean isCustom)
    {
        this.name=name;
        this.type=type;
        this.isCustom=isCustom;
    }
}
