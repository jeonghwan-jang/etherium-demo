package com.blockchain.demo.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBlockNumberHistory is a Querydsl query type for BlockNumberHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBlockNumberHistory extends EntityPathBase<BlockNumberHistory> {

    private static final long serialVersionUID = -1627178947L;

    public static final QBlockNumberHistory blockNumberHistory = new QBlockNumberHistory("blockNumberHistory");

    public final com.blockchain.demo.domain.entity.base.QOnlyCreateEntity _super = new com.blockchain.demo.domain.entity.base.QOnlyCreateEntity(this);

    public final NumberPath<java.math.BigInteger> blockNumber = createNumber("blockNumber", java.math.BigInteger.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QBlockNumberHistory(String variable) {
        super(BlockNumberHistory.class, forVariable(variable));
    }

    public QBlockNumberHistory(Path<? extends BlockNumberHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBlockNumberHistory(PathMetadata metadata) {
        super(BlockNumberHistory.class, metadata);
    }

}

