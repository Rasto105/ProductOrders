package sk.streetofcode.productordermanagement.domain;

public enum OrderStatus {
    NEW,PAID;
    public static OrderStatus fromString(String status){
        return switch (status){
            case "NEW" -> NEW;
            case "PAID" -> PAID;
            default ->throw new IllegalArgumentException("Unknow status of order "+status);
        };
    }
}
