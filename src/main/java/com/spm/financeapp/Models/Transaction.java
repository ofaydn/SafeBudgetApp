package com.spm.financeapp.Models;

import com.spm.financeapp.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="type")
    private TransactionType type;
    @Column(name="price")
    private Double price;
    @Column(name="date")
    private Date date;
    @Column(name="is_periodic")
    private Boolean isPeriodic;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToOne
    @JoinColumn(name="period_id")
    private Period period;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}
