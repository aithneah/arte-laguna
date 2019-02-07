package pl.artelaguna.backend.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordForm implements Serializable {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
