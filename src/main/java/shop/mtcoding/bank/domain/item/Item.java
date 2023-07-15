package shop.mtcoding.bank.domain.item;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shop.mtcoding.bank.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name="item_tb")
@Getter
@Setter // 세터 말고 빌더 사용하자!
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @Column(name="itme_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemName; // 상품명

    @Column(name="price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Item(Long id, String itemName, int price, int stockNumber, String itemDetail, ItemSellStatus itemSellStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
