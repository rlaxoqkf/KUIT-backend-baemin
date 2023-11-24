package kuit.server.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PatchPhoneNumberRequest {
    @NotBlank(message = "phoneNumber: {NotBlank}")
    @Length(max = 30, message = "phoneNumber: 최대 {max}자리까지 가능합니다")
    private String phoneNumber;
}
