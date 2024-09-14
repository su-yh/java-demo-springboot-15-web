package com.eb.business.dto.vip.req;

import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.constant.enums.ReviewStatusEnums;
import com.eb.group.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.group.GroupSequenceProvider;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suyh
 * @since 2024-09-05
 */
@Data
@GroupSequenceProvider(VipAccountReqDto.VipAccountReqDtoGroupSequenceProvider.class)
public class VipAccountReqDto {
    @Schema(description = "tpp 表主键/银行卡表主键")
    @NotNull(groups = ValidationGroups.Req.Update.class)
    @Null(groups = ValidationGroups.Req.Create.class)
    private Long id;

    @Schema(description = "金融账号类别：TPP账号/银行卡号")
    @NotNull
    private FinancialCategoryEnums financialCategory;

    @Schema(description = "金融账号")
    @NotBlank(groups = {BankCreateGroup.class, TppCreateGroup.class})
    @Size(max = 100)
    private String financialAccount;

    @Schema(description = "银行卡时，需要提供")
    @NotBlank(groups = BankCreateGroup.class)
    @Size(max = 100)
    private String bankAccountName;

    @Schema(description = "银行卡时，需要提供")
    @NotBlank(groups = BankCreateGroup.class)
    @Size(max = 100)
    private String swiftCode;

    @Schema(description = "手机号")
    @Size(max = 20)
    private String pnum;

    @Schema(description = "code")
    @Size(max = 20)
    private String code;

    @Schema(description = "人工审核状态")
    private ReviewStatusEnums review;

    public interface BankCreateGroup {
    }

    public interface TppCreateGroup {}

    public static class VipAccountReqDtoGroupSequenceProvider
            implements DefaultGroupSequenceProvider<VipAccountReqDto> {

        @Override
        public List<Class<?>> getValidationGroups(VipAccountReqDto bean) {
            List<Class<?>> defaultGroupSequence = new ArrayList<>();
            defaultGroupSequence.add(VipAccountReqDto.class); // 这一步不能省,否则Default分组都不会执行了，会抛错的

            if (bean != null) {
                if (bean.getFinancialCategory() != null) {
                    switch (bean.getFinancialCategory()) {
                        case BANK:
                            defaultGroupSequence.add(BankCreateGroup.class);
                            break;
                        case TPP:
                            defaultGroupSequence.add(TppCreateGroup.class);
                            break;
                        default:
                            break;
                    }
                }
            }

            return defaultGroupSequence;
        }
    }
}
