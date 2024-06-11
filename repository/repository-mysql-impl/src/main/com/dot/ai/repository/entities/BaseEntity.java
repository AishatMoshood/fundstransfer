package com.dot.ai.repository.entities;


import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ApiModelProperty(value = "created time")
    @Column(name = "gmt_created")
    private Date gmtCreated;

    @ApiModelProperty(value = "modified time")
    @Column(name = "gmt_modified")
    private Date gmtModified;

    @ApiModelProperty(value = "remark")
    private String remark;

}
