package com.eb.mp.typehandler.enlist;

import com.eb.constant.enums.HourOfDayEnums;

/**
 * @author suyh
 * @since 2024-09-03
 */
public class HourOfDayListTypeHandler extends AbstractEnumListTypeHandler<HourOfDayEnums> {
    public HourOfDayListTypeHandler() {
        super(HourOfDayEnums.class);
    }
}
