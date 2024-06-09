package com.dot.ai.repository.entities;

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
@ApiModel(value = "ChannelInfo", description = "channel information registry")
@Table(name = "funds_transfer_order")
public class ChannelInfo extends BaseEntity {

    /**
     * channel name
     */
    private String channel;

    /**
     * channel id
     */
    @Column(name = "channel_id")
    private String channelId;

    /**
     * channel code
     */
    @Column(name = "channel_code")
    private String channelCode;

    /**
     * channel key
     */
    @Column(name = "channel_key")
    private String channelKey;

    /**
     * Additional field information，json
     */
    @Column(name = "extend_fields")
    private String extendFields;

}
