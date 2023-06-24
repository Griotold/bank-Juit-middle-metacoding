# Junit Bank App

### JPA LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListerners(AuditingEntityListeners.class) (Entity 클래스)
```java
@CreatedDate // Insert
@Column(nullable = false)
private LocalDateTime createdAt;

@LastModifiedDate // Insert, Update
@Column(nullable = false)
private LocalDateTime updatedAt;
```