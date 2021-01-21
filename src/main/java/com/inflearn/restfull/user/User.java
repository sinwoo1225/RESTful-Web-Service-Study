package com.inflearn.restfull.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value={"password"})
@JsonFilter("UserInfo")
public class User {
    private Integer id;
    @Size(min = 2, message = "Name은 2글자 이상 입력해 주세요.")
    @NotNull
    private String name;
    @Past
    private Date joinDate;
    @NotEmpty
    private String password;
    @NotEmpty
    private String ssn;
}
