package com.blockchain.demo.repository;

import static com.blockchain.demo.domain.entity.QTransactionEvent.transactionEvent;
import static com.querydsl.core.types.ExpressionUtils.or;

import com.blockchain.demo.common.constant.TransactionEventStatus;
import com.blockchain.demo.domain.entity.QTransactionEvent;
import com.blockchain.demo.domain.entity.TransactionEvent;
import com.blockchain.demo.dto.request.PagingRequestDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionEventRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TransactionEvent> getOnlyMinedStatus() {
        QTransactionEvent from = new QTransactionEvent("from");
        QTransactionEvent joinSelf = new QTransactionEvent("joinSelf");

        return this.jpaQueryFactory.selectFrom(from)
            .leftJoin(joinSelf).on(joinSelf.hash.eq(from.hash),
                                   joinSelf.status.eq(TransactionEventStatus.CONFIRMED))
            .where(from.status.eq(TransactionEventStatus.MINED),
                   joinSelf.id.isNull())
            .fetch();
    }

    public List<TransactionEvent> getTransactionEvents(String address,
                                                       PagingRequestDto requestDto) {
        JPQLQuery<TransactionEvent> query = this.jpaQueryFactory.selectFrom(transactionEvent)
            .where(or(transactionEvent.fromAddress.eq(address),
                      transactionEvent.toAddress.eq(address)),
                   startingAfter(requestDto.startingAfter()),
                   endingBefore(requestDto.endingBefore())
            )
            .orderBy(new OrderSpecifier<>(Order.DESC, transactionEvent.createdAt))
            .limit(requestDto.size());

        return query.fetch();
    }

    private BooleanExpression startingAfter(Long startingAfter) {
        return Objects.isNull(startingAfter) ? null : transactionEvent.id.gt(startingAfter);
    }

    private BooleanExpression endingBefore(Long endingBefore) {
        return Objects.isNull(endingBefore) ? null : transactionEvent.id.lt(endingBefore);
    }
}
