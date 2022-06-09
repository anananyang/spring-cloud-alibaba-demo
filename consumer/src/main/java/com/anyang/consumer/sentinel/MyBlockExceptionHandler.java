package com.anyang.consumer.sentinel;

import ch.qos.logback.core.net.ObjectWriter;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.anyang.common.base.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring mvc 拦截器中如果抛出 BlockException，则会执行 MyBlockExceptionHandler.handle
 */
@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       BlockException e) throws Exception {
        Result<?> result = getResultByBlockException(e);
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(httpServletResponse.getWriter(), result);

    }

    private Result<?> getResultByBlockException(BlockException e) {
        if (e instanceof ParamFlowException) {
            return Result.wrapError("101", "热点参数限流");
        } else if (e instanceof DegradeException) {
            return Result.wrapError("102", "服务降级");
        } else if (e instanceof AuthorityException) {
            return Result.wrapError("103", "授权规则不通过");
        }
        // SystemBlockException
        else if (e instanceof SystemBlockException) {
            return Result.wrapError("104", "触发系统保护规则");
        }
        // FlowException
        else {
            return Result.wrapError("100", "接口限流");
        }
    }

}
