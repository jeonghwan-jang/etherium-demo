package com.blockchain.demo.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransactionEvent is a Querydsl query type for TransactionEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionEvent extends EntityPathBase<TransactionEvent> {

    private static final long serialVersionUID = -404643621L;

    public static final QTransactionEvent transactionEvent = new QTransactionEvent("transactionEvent");

    public final com.blockchain.demo.domain.entity.base.QBaseEntity _super = new com.blockchain.demo.domain.entity.base.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final NumberPath<Integer> blockConfirmation = createNumber("blockConfirmation", Integer.class);

    public final NumberPath<java.math.BigInteger> blockNumber = createNumber("blockNumber", java.math.BigInteger.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fromAddress = createString("fromAddress");

    public final NumberPath<java.math.BigDecimal> gasPrice = createNumber("gasPrice", java.math.BigDecimal.class);

    public final StringPath hash = createString("hash");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.blockchain.demo.common.constant.TransactionEventStatus> status = createEnum("status", com.blockchain.demo.common.constant.TransactionEventStatus.class);

    public final StringPath toAddress = createString("toAddress");

    public final NumberPath<java.math.BigDecimal> translationFee = createNumber("translationFee", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTransactionEvent(String variable) {
        super(TransactionEvent.class, forVariable(variable));
    }

    public QTransactionEvent(Path<? extends TransactionEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransactionEvent(PathMetadata metadata) {
        super(TransactionEvent.class, metadata);
    }

}

