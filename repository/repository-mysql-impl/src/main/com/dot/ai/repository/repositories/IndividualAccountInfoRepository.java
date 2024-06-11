package com.dot.ai.repository.repositories;

import com.dot.ai.repository.entities.IndividualAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualAccountInfoRepository extends JpaRepository<IndividualAccountInfo, Long> {

   IndividualAccountInfo findByAccountNumberAndBankCodeAndAccountNameAndBankVerificationNumberAndKycLevel(
           String accountNumber,
           String bankCode,
           String accountName,
           String bankVerificationNumber,
           String kycLevel
           );

}
