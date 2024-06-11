package com.dot.ai;

import com.google.common.base.Optional;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Slf4j
@Component
@Order
public class ControllerNameReader implements OperationBuilderPlugin {

  public ControllerNameReader() {
  }

  @Override
  public void apply(OperationContext context) {
    try {
      String name = context.getGroupName();
      Optional<ApiOperation> optional = context.findAnnotation(ApiOperation.class);
      if (!optional.isPresent()) {
        return;
      }
      String notes = optional.get().notes();
      context.operationBuilder().notes(String.format("%s[%s]", notes, name));
    } catch (Exception e) {
      log.error("initialize swagger failed",e);
    }

  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}
