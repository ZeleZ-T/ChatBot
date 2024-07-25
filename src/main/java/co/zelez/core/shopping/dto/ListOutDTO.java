package co.zelez.core.shopping.dto;

import java.util.List;

public record ListOutDTO(List<ItemOutDTO> items, int totalCost) {
}
