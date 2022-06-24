package pl.akademiaqa.dto.request.space;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UpdateSpaceRequestDto {

    private String name;
}
