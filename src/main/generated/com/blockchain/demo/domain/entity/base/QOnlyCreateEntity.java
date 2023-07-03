package com.blockchain.demo.domain.entity.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOnlyCreateEntity is a Querydsl query type for OnlyCreateEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QOnlyCreateEntity extends EntityPathBase<OnlyCreateEntity> {

    private static final long serialVersionUID = -1581603735L;

    public static final QOnlyCreateEntity onlyCreateEntity = new QOnlyCreateEntity("onlyCreateEntity");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public QOnlyCreateEntity(String variable) {
        super(OnlyCreateEntity.class, forVariable(variable));
    }

    public QOnlyCreateEntity(Path<? extends OnlyCreateEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOnlyCreateEntity(PathMetadata metadata) {
        super(OnlyCreateEntity.class, metadata);
    }

}

