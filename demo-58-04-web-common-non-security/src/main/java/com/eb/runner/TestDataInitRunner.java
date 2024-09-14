package com.eb.runner;

import com.eb.constant.enums.AuditRecordTypeEnums;
import com.eb.constant.enums.DaysOfWeekEnums;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.HourOfDayEnums;
import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.PayTypeEnums;
import com.eb.constant.enums.PresentStatusEnums;
import com.eb.constant.enums.ShowStatusEnums;
import com.eb.constant.enums.SvipAuditStatusEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.constant.enums.VipTypeEnums;
import com.eb.constant.enums.YesOrNoEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 用来生成一些测试数据的
 *
 * @author suyh
 * @since 2024-09-03
 */
@Component
@Profile({"suyh", "dev"})
@RequiredArgsConstructor
@Slf4j
public class TestDataInitRunner implements ApplicationRunner {
    public final static Random RANDOM = new Random();
    public final static EnumsRandom<EnableStatusEnums> ENABLE_STATUS_ENUMS_RANDOM = new EnumsRandom<>(EnableStatusEnums.values());
    public final static EnumsRandom<ShowStatusEnums> SHOW_STATUS_ENUMS_RANDOM = new EnumsRandom<>(ShowStatusEnums.values());
    public final static EnumsRandom<OnlineStateEnums> ONLINE_STATUS_ENUMS_RANDOM = new EnumsRandom<>(OnlineStateEnums.values());
    public final static EnumsRandom<DaysOfWeekEnums> DAYS_OF_WEEK_ENUMS_RANDOM = new EnumsRandom<>(DaysOfWeekEnums.values());
    public final static EnumsRandom<HourOfDayEnums> HOUR_OF_DAY_ENUMS_RANDOM = new EnumsRandom<>(HourOfDayEnums.values());
    public final static EnumsRandom<SvipAuditStatusEnums> AUDIT_STATUS_ENUMS_RANDOM = new EnumsRandom<>(SvipAuditStatusEnums.values());
    public final static EnumsRandom<VipTypeEnums> VIP_TYPE_ENUMS_RANDOM = new EnumsRandom<>(VipTypeEnums.values());
    public final static EnumsRandom<YesOrNoEnums> YES_OR_NO_ENUMS_RANDOM = new EnumsRandom<>(YesOrNoEnums.values());
    public final static EnumsRandom<PayTypeEnums> PAY_TYPE_ENUMS_RANDOM = new EnumsRandom<>(PayTypeEnums.values());
    public final static EnumsRandom<TransferStatusEnums> TRANSFER_STATUS_ENUMS_RANDOM = new EnumsRandom<>(TransferStatusEnums.values());
    public final static EnumsRandom<PresentStatusEnums> PRESENT_STATUS_ENUMS_RANDOM = new EnumsRandom<>(PresentStatusEnums.values());
    public final static EnumsRandom<OrderAuditStatusEnums> ORDER_AUDIT_STATUS_ENUMS_RANDOM = new EnumsRandom<>(OrderAuditStatusEnums.values());
    public final static EnumsRandom<AuditRecordTypeEnums> AUDIT_RECORD_TYPE_ENUMS_RANDOM = new EnumsRandom<>(AuditRecordTypeEnums.values());

    private static final AtomicLong baseIdNumber = new AtomicLong(0);

    static {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime baseTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime nowTime = LocalDateTime.now();
        long epochMilliBase = baseTime.atZone(zoneId).toInstant().toEpochMilli();
        long epochMilliNow = nowTime.atZone(zoneId).toInstant().toEpochMilli();
        long baseNumberMilli = epochMilliNow - epochMilliBase;
        baseIdNumber.set(baseNumberMilli / 100);
    }

    /**
     * 生成一定宽度的十六进制字符，同0 填充不足宽度
     */
    public static String randomUniqueNumber() {
        long curIdNumber = baseIdNumber.getAndIncrement();
        return String.format("%012x", curIdNumber);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static BigDecimal randomBigDecimal() {
        double doubleValue = RANDOM.nextDouble() * 10000.0;
        return BigDecimal.valueOf(doubleValue);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
    }

    public static class EnumsRandom<E extends Enum<E>> {
        private final Random random = new Random();
        private final E[] values;

        public EnumsRandom(E[] values) {
            this.values = values;
        }

        public List<E> obtainList() {
            int enable = random.nextInt(2);
            if (enable == 0) {
                return null;
            }

            List<E> enList = new ArrayList<>();
            int num = random.nextInt(7);
            for (int i = 0; i < num; i++) {
                E e = obtainEnum();
                enList.add(e);
            }

            return enList;
        }

        public E obtainEnum() {
            int index = random.nextInt(values.length);
            return values[index];
        }
    }
}
