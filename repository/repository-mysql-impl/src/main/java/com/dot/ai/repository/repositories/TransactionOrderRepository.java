package com.dot.ai.repository.repositories;

import com.dot.ai.repository.entities.TransactionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Long> {

    Optional<TransactionOrder> findById(Long id);

}
