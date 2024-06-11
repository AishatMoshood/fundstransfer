package com.dot.ai.repository.repositories;

import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.repository.entities.TransactionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Long>,
        JpaSpecificationExecutor<TransactionOrder> {

      List<TransactionOrder> findByDateCreatedBetween(Date startOfDay, Date endOfDay);

      List<TransactionOrder> findByStatus(TransactionStatusEnum status);

      TransactionOrder findBySessionId(String sessionId);


}
