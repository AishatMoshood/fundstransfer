package com.dot.ai.repository.entities;

import com.dot.ai.commonservice.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Data
@Entity
@ApiModel(value = "IndividualAccountInfo", description = "sender and beneficiary information")
@Table(name = "individual_account_info")
public class IndividualAccountInfo extends BaseEntity {

    /**
     * individual's first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * individual's last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * individual's middle_name
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * individual's email
     */
    @Column(name = "email_address")
    private String emailAddress;

    /**
     * individual's gender
     */
    @Column(name = "gender")
    private GenderEnum gender;

    /**
     * individual's email
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * individual's email
     */
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * individual's bank code
     */
    @Column(name = "bank_code")
    private String bankCode;


    /**
     * individual's bankVerificationNumber
     */
    @Column(name = "bank_verification_number")
    private String bankVerificationNumber;

    /**
     * individual's kycLevel
     */
    //todo: find out if this is dependent on bank
    @Column(name = "kyc_level")
    private String kycLevel;

    /**
     * Additional field information，json
     */
    @Column(name = "extend_fields")
    private String extendFields;

}
