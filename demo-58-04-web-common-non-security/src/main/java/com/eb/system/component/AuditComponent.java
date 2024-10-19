package com.eb.system.component;

import com.eb.constant.CommunityConstants;
import com.eb.constant.enums.AuditEnums;
import com.eb.mp.handler.SqlHandler;
import com.eb.mp.mysql.entity.audit.OperationRecordEntity;
import com.eb.mp.mysql.mapper.audit.OperationRecordMapper;
import com.eb.mvc.authentication.LoginUser;
import com.eb.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suyh
 * @since 2024-10-18
 */
@Component("audit")
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class AuditComponent {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final OperationRecordMapper operationRecordMapper;

    // 审计日志记录
    public void auditRecord(
            AuditEnums auditOperation,
            Object spelReturnValue,
            HttpServletRequest request,
            LoginUser loginUser,
            Object... reqArgs) {

        try {
            doAuditRecord(auditOperation, spelReturnValue, request, loginUser, reqArgs);
        } catch (Exception e) {
            log.warn("audit record exception.", e);
        }
    }

    private void doAuditRecord(
            AuditEnums auditOperation,
            Object spelReturnValue,
            HttpServletRequest request,
            LoginUser loginUser,
            Object... reqArgs) {
        OperationRecordEntity recordEntity = new OperationRecordEntity();
        recordEntity.setUserId(loginUser.getId()).setUserNickname(loginUser.getNickname())
                .setPage(auditOperation.getPage()).setOperation(auditOperation.getOperation())
                .setReqArgument(JsonUtils.serializable(reqArgs))
                .setResult(JsonUtils.serializable(spelReturnValue))
                .setReqPath(request.getServletPath()).setReqMethod(request.getMethod())
                .setCreated(new Date());

        Object traceId = request.getAttribute(CommunityConstants.TRACE_ID);
        if (traceId instanceof Long) {
            recordEntity.setTraceId(Long.parseLong(traceId + ""));
        } else {
            log.warn("LOST TRACE ID");
        }

        recordEntity.setSqlList(SqlHandler.AUDIT_SQL_LIST.get());

        executorService.submit(() -> {
            operationRecordMapper.insert(recordEntity);
        });
    }

}
