package com.blockchain.demo.domain.entity;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.base.BaseEntity;
import com.blockchain.demo.domain.entity.listener.TransactionEventListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(TransactionEventListener.class)
@Entity
public class TransactionEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigInteger blockNumber;

    @Column(nullable = false)
    private String fromAddress;

    @Column(nullable = false)
    private String toAddress;

    @Column(nullable = false)
    private String hash;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column
    private BigDecimal gasPrice;

    @Column
    private BigDecimal translationFee;

    @Column
    private Integer blockConfirmation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionEventStatus status;

    public void changeBlockConfirmation(int blockConfirmation) {
        this.blockConfirmation = blockConfirmation;
    }
}
