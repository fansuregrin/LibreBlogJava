package org.fansuregrin.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.fansuregrin.annotation.Password;
import org.fansuregrin.annotation.Username;
import org.fansuregrin.validation.ValidateGroup;

import java.time.LocalDateTime;

@Data
public class User {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = {
        ValidateGroup.Crud.Create.class,
        ValidateGroup.Crud.Update.Password.Self.class,
        ValidateGroup.Crud.Update.GeneralInfo.Self.class}, message = "无需提供用户ID")
    @NotNull(groups = {ValidateGroup.Crud.Update.GeneralInfo.Other.class,
        ValidateGroup.Crud.Update.Password.Other.class}, message = "缺失id字段，请提供用户ID")
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(groups = {ValidateGroup.Crud.Create.class, ValidateGroup.Crud.Query.Login.class},
        message = "缺失username字段，请提供用户名")
    @Username(groups = {ValidateGroup.Crud.Create.class,
        ValidateGroup.Crud.Update.GeneralInfo.class, ValidateGroup.Crud.Query.Login.class},
        message = "用户名至少4个字符且不能包含空白字符")
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.GeneralInfo.class,
        message = "请访问单独的更新密码接口")
    @NotNull(groups = {ValidateGroup.Crud.Create.class, ValidateGroup.Crud.Update.Password.class,
        ValidateGroup.Crud.Query.Login.class}, message = "缺失password字段，请提供密码")
    @Password(groups = {ValidateGroup.Crud.Create.class, ValidateGroup.Crud.Update.Password.class,
        ValidateGroup.Crud.Query.Login.class},
        message = "密码长度仅限6~20且必须包括大小写字母、数字和特殊符号，不能包含空白字符")
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Email(groups = {ValidateGroup.Crud.Create.class, ValidateGroup.Crud.Update.class},
        message = "请填写正确的邮箱地址")
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private String realname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private Integer roleId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private LocalDateTime createTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private LocalDateTime modifyTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Null(groups = ValidateGroup.Crud.Update.Password.class, message = "该接口只能更新密码")
    private Role role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull(groups = {ValidateGroup.Crud.Update.Password.class},
        message = "缺失oldPassword字段，请提供密码")
    @Password(groups = ValidateGroup.Crud.Update.Password.class,
        message = "密码长度仅限6~20且必须包括大小写字母、数字和特殊符号，不能包含空白字符")
    private String oldPassword;
}
