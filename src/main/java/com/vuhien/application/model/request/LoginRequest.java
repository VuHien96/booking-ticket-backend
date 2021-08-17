package com.vuhien.application.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Created by VuHien96 on 17/08/2021 15:06
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginRequest {
    @NotBlank(message = "Email trống")
    @Email(message = "Email không đúng")
    private String email;
    @NotBlank(message = "Mật khẩu trống")
    @Size(min = 6,max = 20,message = "Mật khẩu có độ dài từ 6 - 20 ký tự")
    private String password;
}
