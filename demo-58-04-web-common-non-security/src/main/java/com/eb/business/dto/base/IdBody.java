package com.eb.business.dto.base;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author suyh
 * @since 2024-09-05
 */
@Data
public class IdBody {
    @NotNull
    private Long id;
}
