package com.example.PersonalAccounting.entity.enums;

public enum TransactionCategory {
    CLOTHES_AND_SHOES("CLOTHES_AND_SHOES"), GIFTS("GIFTS"),
    ENTERTAINMENT_AND_LEISURE("ENTERTAINMENT_AND_LEISURE"), FINANCIAL_SERVICES("FINANCIAL_SERVICES"),
    EDUCATION("EDUCATION"), DWELLING("DWELLING"), EXPENSES_FOR_ANIMALS("EXPENSES_FOR_ANIMALS"),
    TRANSPORT("TRANSPORT"), FOOD_AND_DRINKS("FOOD_AND_DRINKS"), CHARITY("CHARITY"),
    EXPENSES_FOR_CHILDREN("EXPENSES_FOR_CHILDREN"), HEALTH_AND_MEDICINE("HEALTH_AND_MEDICINE"),
    OTHER("OTHER");

    private final String categoryName;

    TransactionCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static TransactionCategory getCategoryByName(String name) {
        for (TransactionCategory category : values()) {
            if (category.getCategoryName().equals(name)) {
                return category;
            }
        }

        throw new IllegalArgumentException("No enum found with url: [" + name + "]");
    }
}
