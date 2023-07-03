package com.blockchain.demo.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWallet is a Querydsl query type for Wallet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWallet extends EntityPathBase<Wallet> {

    private static final long serialVersionUID = -543855304L;

    public static final QWallet wallet = new QWallet("wallet");

    public final com.blockchain.demo.domain.entity.base.QBaseEntity _super = new com.blockchain.demo.domain.entity.base.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath path = createString("path");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QWallet(String variable) {
        super(Wallet.class, forVariable(variable));
    }

    public QWallet(Path<? extends Wallet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWallet(PathMetadata metadata) {
        super(Wallet.class, metadata);
    }

}

