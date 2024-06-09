package com.dot.ai.repository.repositories;

import com.dot.ai.repository.entities.ChannelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelInfoRepository extends JpaRepository<ChannelInfo, Long> {

    Optional<ChannelInfo> findById(Long id);

}
