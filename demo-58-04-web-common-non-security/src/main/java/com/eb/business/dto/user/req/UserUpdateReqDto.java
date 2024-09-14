package com.eb.business.dto.user.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
public class UserUpdateReqDto {
    @NotNull
    private Long id;

    private String password;

    private String nickname;
}
