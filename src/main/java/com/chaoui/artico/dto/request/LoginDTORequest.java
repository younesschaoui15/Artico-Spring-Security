package com.chaoui.artico.dto.request;

import com.chaoui.artico.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTORequest extends LoginDTO {

    private String email;

}
