package com.eb.mp.typehandler.enlist;

import com.eb.constant.enums.DaysOfWeekEnums;

/**
 * @author suyh
 * @since 2024-09-03
 */
public class DaysOfWeekListTypeHandler extends AbstractEnumListTypeHandler<DaysOfWeekEnums> {
    public DaysOfWeekListTypeHandler() {
        super(DaysOfWeekEnums.class);
    }
}
