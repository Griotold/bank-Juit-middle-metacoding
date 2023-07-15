package shop.mtcoding.bank.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
//@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    // 아이템 열 개 리스트 : 반은 판매중, 반은 품절
    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

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
    @Test
    @DisplayName("상품명으로 조회하기")
    void findByItemName_test() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByItemName("테스트 상품1");
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }

        // then
        assertThat(itemList.get(0).getItemDetail()).isEqualTo("테스트 상품 상세 설명1");
    }

    @Test
    @DisplayName("상품명 또는 상품 상세 설명으로 조회하기")
    void findByItemNameOrItemDetail_test() {
        // given
        createItemList();

        // when
        List<Item> itemList
                = itemRepository.findByItemNameOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }

        // then
        assertThat(itemList.get(0).getItemDetail()).isEqualTo("테스트 상품 상세 설명1");
        assertThat(itemList.get(1).getItemName()).isEqualTo("테스트 상품5");
    }
    @Test
    @DisplayName("가격 LessThan 테스트")
    void findByLessThan_test() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }

        // then
        assertThat(itemList.get(0).getPrice()).isEqualTo(10001);
    }
    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    void findByPriceLessThanOrderByPriceDesc_test() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }

        // then
        assertThat(itemList.get(0).getPrice()).isEqualTo(10004);
    }
    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    void findByItemDetail_test() {
        // given
        createItemList();

        // when
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println("item = " + item);
        }
        // then
        assertThat(itemList.size()).isEqualTo(10);
    }
}