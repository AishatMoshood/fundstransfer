package com.dot.ai.repository.repositories;

import com.dot.ai.repository.entities.IndividualAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualAccountInfoRepository extends JpaRepository<IndividualAccountInfo, Long> {

    Optional<IndividualAccountInfo> findById(Long id);

}
