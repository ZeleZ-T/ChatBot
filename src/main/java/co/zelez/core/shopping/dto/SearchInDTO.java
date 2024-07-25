package co.zelez.core.shopping.dto;

public record SearchInDTO(int ID, int quantity, String name) {
    public SearchInDTO(String name, int quantity) {this(-1, quantity, name);}
    public SearchInDTO(int ID, int quantity) {this(ID, quantity, null);}
    public SearchInDTO(String name) {this(-1, -1, name);}
    public SearchInDTO(int ID) {this(ID, -1, null);}
}
