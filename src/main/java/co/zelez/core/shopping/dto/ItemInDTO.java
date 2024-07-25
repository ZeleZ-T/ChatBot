package co.zelez.core.shopping.dto;

public record ItemInDTO(String name, int price, int quantity) {
    public ItemInDTO(String name, int price) {this(name, price, 1);}
}