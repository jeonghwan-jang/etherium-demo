package com.blockchain.demo.domain.entity;

import com.blockchain.demo.domain.entity.base.BaseEntity;
import com.blockchain.demo.util.CryptoUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column
    private BigDecimal amount;

    public void changeAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Wallet(Long id,
                  Long memberId,
                  String path,
                  String password,
                  String address,
                  BigDecimal amount) {
        this.id = id;
        this.memberId = memberId;
        this.path = path;
        this.password = CryptoUtil.encrypt(password);
        this.address = address;
        this.amount = amount;
    }

    public String getPassword() {
        return CryptoUtil.decrypt(password);
    }
}
