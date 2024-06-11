package com.dot.ai.repository.repositories;

import com.dot.ai.repository.entities.ChannelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelInfoRepository extends JpaRepository<ChannelInfo, Long> {

   ChannelInfo findByChannelKey(String key);

}
