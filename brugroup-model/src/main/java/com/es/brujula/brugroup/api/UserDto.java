package com.es.brujula.brugroup.api;

import com.es.brujula.brugroup.api.common.ModelItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@ApiModel("Model User")
@Data
public class UserDto extends ModelItem {

    @ApiModelProperty(value = "the user's id", required = true)
    private Long id;

    @ApiModelProperty(value = "the user's fullName", required = true)
    @NotNull(message = "fullName is null")
    @NotEmpty(message = "fullName is empty")
    @Size(max = 200, message = "fullName has more than 200 characters")
    @Pattern(regexp = "^[A-Za-z\\s]*$", message = "can only have blank letters and spaces")
    private String fullName;

    @ApiModelProperty(value = "the user's username", required = true)
    @NotNull(message = "username is null")
    @NotEmpty(message = "username is empty")
    @Size(max = 20, message = "username has more than 20 characters")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "can only have alphanumeric characters")
    private String username;

    @ApiModelProperty(value = "the user's password", required = true)
    @NotNull(message = "password is null")
    @NotEmpty(message = "username is empty")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(regexp = "^[a-zA-Z]*(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]*[a-zA-Z]*$", message = "must contain some capital letter and some digit")
    private String password;

    @ApiModelProperty(value = "the user's lastUpdate", required = true)
    @Null(message = "password isn't null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;
}
