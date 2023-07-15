package shop.mtcoding.bank.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트 세터 사용")
    void createdItem_test() {
        // given
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setStockNumber(100);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        // when
        Item saved = itemRepository.save(item);

        // then
        assertThat(saved.getItemName()).isEqualTo("테스트 상품");
    }

    @Test
    @DisplayName("상품 저장 테스트 빌더 사용")
    void builder_test() {
        // given
        Item item = Item.builder().itemName("테스트 상품").price(10000).stockNumber(100)
                .itemDetail("테스트 상품 상세 설명").itemSellStatus(ItemSellStatus.SELL)
                .updatedAt(LocalDateTime.now()).createdAt(LocalDateTime.now())
                .build();
        // when
        Item saved = itemRepository.save(item);

        // then
        assertThat(saved.getItemDetail()).isEqualTo("테스트 상품 상세 설명");
    }
}