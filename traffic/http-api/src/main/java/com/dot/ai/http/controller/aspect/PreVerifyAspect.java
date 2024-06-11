package com.dot.ai.http.controller.aspect;

import com.alibaba.fastjson.JSON;
import com.dot.ai.commonservice.enums.ResponseCodeEnum;
import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.commonservice.service.EncryptionService;
import com.dot.ai.http.controller.response.FundsTransferResponse;
import com.dot.ai.repository.entities.ChannelInfo;
import com.dot.ai.repository.repositories.ChannelInfoRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Slf4j
@Component
@Aspect
public class PreVerifyAspect {

    private final ChannelInfoRepository channelInfoRepository;
    private final EncryptionService encryptionService;

    public PreVerifyAspect(ChannelInfoRepository channelInfoRepository,
                           EncryptionService encryptionService) {
        this.channelInfoRepository = channelInfoRepository;
        this.encryptionService = encryptionService;
    }

    @Pointcut("@annotation(com.dot.ai.http.controller.annotation.PreVerify)")
    public void advice() {
    }


    /**
     * validate header key
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("advice()")
    public Object invokeAround(ProceedingJoinPoint point) throws Throwable {

        FundsTransferBaseModel response = null;

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String key = request.getHeader("Authorization");
            log.debug("authorization = {}",key);

            if (StringUtils.isBlank(key)){
                response = new FundsTransferBaseModel();
                response.setResponseCodeAndMessage(ResponseCodeEnum.FA01);
            }

            ChannelInfo channelInfo = channelInfoRepository.findByChannelKey(key);
            if (channelInfo == null || channelInfo.getChannelValidity().equals("false")){
                response = new FundsTransferBaseModel();
                response.setResponseCodeAndMessage(ResponseCodeEnum.FA01);
            }
            if (response != null){
                return new FundsTransferResponse(encryptionService.aesEncrypt(
                        JSON.toJSONString(response), key));
            }
            return point.proceed();
        }catch (Exception e){
            response = new FundsTransferBaseModel();
            response.setResponseCodeAndMessage(ResponseCodeEnum.PEN03);
            return new FundsTransferResponse(encryptionService.aesEncrypt(
                    JSON.toJSONString(response), ""));
        }

    }

    @AfterReturning(value = "advice()",returning = "data")
    public void afterReturning(JoinPoint point,Object data) throws Throwable {

    }

    @AfterThrowing("advice()")
    public void afterThrowing(JoinPoint point){

    }


}
