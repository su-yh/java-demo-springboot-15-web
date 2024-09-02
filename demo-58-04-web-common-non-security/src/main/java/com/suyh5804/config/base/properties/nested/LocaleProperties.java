package com.suyh5804.config.base.properties.nested;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Data
public class LocaleProperties {
    @NotBlank
    private String language;
    private String country;
}
