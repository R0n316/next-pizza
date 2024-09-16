package ru.alex.nextpizzaapi.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "items", callSuper = false)
@ToString(exclude = "items")
public class Order extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String token;

    @Column(name = "total_amount")
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "payment_id")
    private Integer paymentId;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<CartItem> items;

    @Column(name = "full_name")
    private String fullName;

    private String email;
    private String phone;
    private String comment;
}
