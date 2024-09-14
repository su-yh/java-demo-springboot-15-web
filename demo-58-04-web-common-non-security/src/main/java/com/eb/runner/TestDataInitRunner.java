package com.eb.runner;

import com.eb.constant.enums.AuditRecordTypeEnums;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.OrderMatchStatusEnums;
import com.eb.constant.enums.SvipAuditStatusEnums;
import com.eb.constant.enums.DaysOfWeekEnums;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.HourOfDayEnums;
import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.PayTypeEnums;
import com.eb.constant.enums.PresentStatusEnums;
import com.eb.constant.enums.ShowStatusEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.constant.enums.VipTypeEnums;
import com.eb.constant.enums.YesOrNoEnums;
import com.eb.mp.mysql.entity.business.SchedulingTransferRecordEntity;
import com.eb.mp.mysql.entity.business.SmartSmsRecordEntity;
import com.eb.mp.mysql.entity.business.TransferRecordEntity;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.mp.mysql.entity.business.VipBankAccountEntity;
import com.eb.mp.mysql.entity.business.VipInfoEntity;
import com.eb.mp.mysql.entity.business.VipTppEntity;
import com.eb.mp.mysql.mapper.business.SchedulingTransferRecordMapper;
import com.eb.mp.mysql.mapper.business.SmartSmsRecordMapper;
import com.eb.mp.mysql.mapper.business.TransferRecordMapper;
import com.eb.mp.mysql.mapper.business.UtrUploadRecordMapper;
import com.eb.mp.mysql.mapper.business.VipBankAccountMapper;
import com.eb.mp.mysql.mapper.business.VipInfoMapper;
import com.eb.mp.mysql.mapper.business.VipTppMapper;
import com.eb.util.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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

    private final VipInfoMapper vipInfoMapper;
    private final VipBankAccountMapper vipBankAccountMapper;
    private final VipTppMapper vipTppMapper;
    private final SchedulingTransferRecordMapper schedulingTransferRecordMapper;
    private final TransferRecordMapper transferRecordMapper;
    private final SmartSmsRecordMapper smartSmsRecordMapper;
    private final UtrUploadRecordMapper utrUploadRecordMapper;

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
        if (true) {
            return;
        }

        log.error("生产环境不应该出现");

        List<VipInfoEntity> vipInfoEntities = mockVipInfoEntities();
        List<VipTppEntity> vipTppEntities = mockVipTppEntities(vipInfoEntities);
        List<VipBankAccountEntity> vipBankAccountEntities = mockVipBankAccountEntities(vipInfoEntities);
        List<SchedulingTransferRecordEntity> schedulingTransferRecordEntities
                = mockSchedulingTransferRecordEntities(vipInfoEntities, vipTppEntities, vipBankAccountEntities);
        List<TransferRecordEntity> transferRecordEntities = mockTransferRecordEntities(
                vipInfoEntities, schedulingTransferRecordEntities);

        List<SmartSmsRecordEntity> smartSmsRecordEntities = mockSmartSmsRecordEntities();
        List<UtrUploadRecordEntity> utrUploadRecordEntities = mockUtrUploadRecordEntities(schedulingTransferRecordEntities);

        vipInfoMapper.insertBatch(vipInfoEntities);
        vipTppMapper.insertBatch(vipTppEntities);
        schedulingTransferRecordMapper.insertBatch(schedulingTransferRecordEntities);
        vipBankAccountMapper.insertBatch(vipBankAccountEntities);
        transferRecordMapper.insertBatch(transferRecordEntities);
        smartSmsRecordMapper.insertBatch(smartSmsRecordEntities);
        utrUploadRecordMapper.insertBatch(utrUploadRecordEntities);
    }

    private List<UtrUploadRecordEntity> mockUtrUploadRecordEntities(
            List<SchedulingTransferRecordEntity> schedulingTransferRecordEntities) {
        Date now = new Date();
        Integer dates = DateUtils.convertToInteger(LocalDate.now());
        List<UtrUploadRecordEntity> entities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int enableNumber = RANDOM.nextInt(10);

            UtrUploadRecordEntity entity = new UtrUploadRecordEntity();


            if (enableNumber != 0) {
                int index = RANDOM.nextInt(schedulingTransferRecordEntities.size());
                SchedulingTransferRecordEntity orderEntity = schedulingTransferRecordEntities.get(index);
                entity.setCpOrder(orderEntity.getCpOrder())
                        .setPn(orderEntity.getPn())
                        .setBuyerUid(orderEntity.getReceiverUid())
                        .setPnum(orderEntity.getPnum())
                        .setUtr(orderEntity.getUtr())
                        .setHit(OrderMatchStatusEnums.MATCH);
            } else {
                entity.setCpOrder("cpOrder_" + randomUniqueNumber())
                        .setPn("hy")
                        .setBuyerUid("buUid_" + randomUniqueNumber())
                        .setPnum("phone_" + randomUniqueNumber())
                        .setUtr("utr_" + randomUniqueNumber())
                        .setHit(OrderMatchStatusEnums.MISMATCH);
            }

            entity.setType(AUDIT_RECORD_TYPE_ENUMS_RANDOM.obtainEnum())
                    .setBuyerUid("uid_" + randomUniqueNumber())
                    .setAmount(randomBigDecimal())
                    .setDates(dates)
                    .setCreated(now)
                    .setUpdated(now);

            entities.add(entity);
        }

        return entities;
    }

    private List<SmartSmsRecordEntity> mockSmartSmsRecordEntities() {
        Date nowDate = new Date();
        long nowTimeMilli = nowDate.getTime();

        Integer dates = DateUtils.convertToInteger(LocalDate.now());

        List<SmartSmsRecordEntity> entities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Date created = new Date(nowTimeMilli + 1000 * i);
            int durationSeconds = RANDOM.nextInt(1000);
            Date mtime = new Date(nowTimeMilli + 1000 * durationSeconds);

            SmartSmsRecordEntity entity = new SmartSmsRecordEntity();
            entity.setPnum("pnum_" + randomUniqueNumber())
                    .setAmount(randomBigDecimal())
                    .setCreated(created)
                    .setDates(dates)
                    .setUtr("utr_" + uuid())
                    .setCpOrder("cpOrder_" + uuid());

            entities.add(entity);
        }

        return entities;
    }

    private List<TransferRecordEntity> mockTransferRecordEntities(
            List<VipInfoEntity> vipInfoEntities,
            List<SchedulingTransferRecordEntity> schedulingTransferRecordEntities) {
        Date nowDate = new Date();
        long nowTimeMilli = nowDate.getTime();
        List<TransferRecordEntity> entities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int vipInfoIndex = RANDOM.nextInt(vipInfoEntities.size());
            VipInfoEntity vipInfoEntity = vipInfoEntities.get(vipInfoIndex);

            int schedulingTransferRecordIndex = RANDOM.nextInt(schedulingTransferRecordEntities.size());
            SchedulingTransferRecordEntity schedulingTransferRecordEntity
                    = schedulingTransferRecordEntities.get(schedulingTransferRecordIndex);

            Date created = new Date(nowTimeMilli + 1000 * i);
            int durationSeconds = RANDOM.nextInt(1000);
            Date mtime = new Date(nowTimeMilli + 1000 * durationSeconds);
            TransferRecordEntity entity = new TransferRecordEntity();
            entity.setOrder("order_" + uuid())
                    .setCpOrder(schedulingTransferRecordEntity.getCpOrder())
                    .setCode(vipInfoEntity.getCode())
                    .setReceiverUid("receiver_" + randomUniqueNumber())
                    .setSenderUid("sender_" + randomUniqueNumber())
                    //.setPresent(randomBigDecimal())
                    .setStatus(PRESENT_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setCreated(created)
                    .setMtime(mtime)
                    .setPnum(vipInfoEntity.getPnum())
                    .setPn(vipInfoEntity.getPn());

            entities.add(entity);
        }

        return entities;
    }

    public static List<SchedulingTransferRecordEntity> mockSchedulingTransferRecordEntities(
            List<VipInfoEntity> vipInfoEntities, List<VipTppEntity> vipTppEntities,
            List<VipBankAccountEntity> vipBankAccountEntities) {
        List<SchedulingTransferRecordEntity> entities = new ArrayList<>();

        for (VipInfoEntity vipInfoEntity : vipInfoEntities) {
            int val = RANDOM.nextInt(10);
            // 10% 的没有
            if (val == 0) {
                continue;
            }

            String code = vipInfoEntity.getCode();
            String pnum = vipInfoEntity.getPnum();
            String uid = vipInfoEntity.getUid();

            List<SchedulingTransferRecordEntity> curEntities = new ArrayList<>();
            int entityCount = RANDOM.nextInt(5) + 1;
            for (int i = 0; i < entityCount; i++) {

                LocalDate localDate = LocalDate.now();
                Integer dates = DateUtils.convertToInteger(localDate);

                Date nowDate = new Date();
                int mtimePlus = RANDOM.nextInt(10000) + 10;
                Date mtimeDate = new Date(nowDate.getTime() + mtimePlus * 1000);
                PayTypeEnums payTypeEnums = PAY_TYPE_ENUMS_RANDOM.obtainEnum();
                String orderInfo = "[{\"sku\":\"SKU123\",\"quantity\":10,\"price\":99.99},{\"sku\":\"SKU456\",\"quantity\":5,\"price\":59.99}]";
                String logisticsInfo = "[{\"carrier\":\"usps\",\"trackingNumber\":\"123456\"},{\"carrier\":\"ups\",\"trackingNumber\":\"789012\"}]";

                SchedulingTransferRecordEntity entity = new SchedulingTransferRecordEntity();
                entity.setCode(code)
                        .setPn("hy")
                        .setPnum(pnum)
                        .setPayType(payTypeEnums)
                        .setAmount(randomBigDecimal())
                        .setReceiverUid("rcv_uid_" + randomUniqueNumber())
                        .setUid(uid)
                        .setRealAmount(randomBigDecimal())
                        .setCpOrder("cpOrder_" + uuid())
                        .setStatus(TRANSFER_STATUS_ENUMS_RANDOM.obtainEnum())
                        .setMtime(mtimeDate)
                        .setOrderInfo("[{\"sku\":\"SKU123\",\"quantity\":10,\"price\":99.99},{\"sku\":\"SKU456\",\"quantity\":5,\"price\":59.99}]")
                        .setOrderInfo(orderInfo)
                        .setLogisticsInfo(logisticsInfo)
                        .setOrderAuditStatus(ORDER_AUDIT_STATUS_ENUMS_RANDOM.obtainEnum())
                        .setDates(dates)
                        .setUtr("utr_" + uuid())
                        .setCreated(nowDate)
                        .setUpdated(nowDate);

                switch (payTypeEnums) {
                    case BANK:
                        int indexBank = RANDOM.nextInt(vipBankAccountEntities.size());
                        VipBankAccountEntity bankAccountEntity = vipBankAccountEntities.get(indexBank);
                        entity.setCardNo(bankAccountEntity.getCardNo()).setAccount(bankAccountEntity.getAccount());
                        break;
                    case TPP:
                        int indexTpp = RANDOM.nextInt(vipTppEntities.size());
                        VipTppEntity tppEntity = vipTppEntities.get(indexTpp);
                        entity.setTpp(tppEntity.getTpp());
                        break;
                    default:
                        break;
                }

                curEntities.add(entity);
            }

            entities.addAll(curEntities);
        }

        return entities;
    }

    public static List<VipInfoEntity> mockVipInfoEntities() {

        long currentTimeSeconds = System.currentTimeMillis() / 1000;

        List<VipInfoEntity> entities = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int num = i + 1;
            long codeValue = currentTimeSeconds + i;

            VipInfoEntity entity = new VipInfoEntity();
            entity.setCode(String.format("code_%d", codeValue))
                    .setTgUsername("tg_" + uuid())
                    .setUid("uid_" + randomUniqueNumber())
                    .setRanking(num)
                    .setSequence(num)
                    .setEnable(ENABLE_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setShow(SHOW_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setEvaluate(RANDOM.nextInt(100))
                    .setPnum(String.format("phone_%08d", RANDOM.nextInt(900_000_000) + 100_000_000))
                    .setPn("hy")
                    .setBalance(randomBigDecimal())
                    .setName("name_" + uuid())
                    .setTransferBalance(randomBigDecimal())
                    .setTransferCount(RANDOM.nextInt(5000))
                    .setContactDetails(uuid())
                    .setCreated(new Date())
                    .setCreatedBy("createBy_" + uuid())
                    .setInitTransferBalance(randomBigDecimal())
                    .setInitTransferCount(RANDOM.nextInt(100))
                    .setOnline(ONLINE_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setSvipAppOnline(ONLINE_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setOnlineDayOfWeek(DAYS_OF_WEEK_ENUMS_RANDOM.obtainList())
                    .setOnlineHourOfDay(HOUR_OF_DAY_ENUMS_RANDOM.obtainList())
                    .setSvipAuditStatus(AUDIT_STATUS_ENUMS_RANDOM.obtainEnum())
                    .setSvipVipType(VIP_TYPE_ENUMS_RANDOM.obtainEnum())
            ;

            entities.add(entity);
        }

        return entities;
    }

    public static List<VipTppEntity> mockVipTppEntities(List<VipInfoEntity> vipInfoEntities) {
        List<VipTppEntity> entities = new ArrayList<>();

        for (VipInfoEntity vipInfoEntity : vipInfoEntities) {
            int val = RANDOM.nextInt(10);
            // 10% 的没有
            if (val == 0) {
                continue;
            }

            String code = vipInfoEntity.getCode();
            String pnum = vipInfoEntity.getPnum();

            List<VipTppEntity> curEntities = new ArrayList<>();
            int entityCount = RANDOM.nextInt(5) + 1;
            for (int i = 0; i < entityCount; i++) {
                VipTppEntity entity = new VipTppEntity();
                entity.setCode(code)
                        .setPnum(pnum)
                        .setTpp("tpp_" + uuid())
                        .setEnable(ENABLE_STATUS_ENUMS_RANDOM.obtainEnum())
                        .setCreated(new Date())
                        .setUpdated(new Date());

                curEntities.add(entity);
            }

            entities.addAll(curEntities);
        }

        return entities;
    }

    public static List<VipBankAccountEntity> mockVipBankAccountEntities(List<VipInfoEntity> vipInfoEntities) {
        List<VipBankAccountEntity> entities = new ArrayList<>();

        for (VipInfoEntity vipInfoEntity : vipInfoEntities) {
            int val = RANDOM.nextInt(10);
            // 10% 的没有
            if (val == 0) {
                continue;
            }

            String code = vipInfoEntity.getCode();
            String pnum = vipInfoEntity.getPnum();

            List<VipBankAccountEntity> curEntities = new ArrayList<>();
            int entityCount = RANDOM.nextInt(5) + 1;
            for (int i = 0; i < entityCount; i++) {
                VipBankAccountEntity entity = new VipBankAccountEntity();
                entity.setCode(code)
                        .setPnum(pnum)
                        .setCardNo("cardNo_" + uuid())
                        .setAccount("account_" + uuid())
                        .setSwift("swift_" + randomUniqueNumber())
                        .setEnable(ENABLE_STATUS_ENUMS_RANDOM.obtainEnum())
                        .setCreated(new Date())
                        .setUpdated(new Date());

                curEntities.add(entity);
            }

            entities.addAll(curEntities);
        }

        return entities;
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
