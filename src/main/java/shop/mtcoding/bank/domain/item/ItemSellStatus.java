package shop.mtcoding.bank.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemSellStatus {

    SELL("판매중"), SOLD_OUT("품절");

    private String value;
}
