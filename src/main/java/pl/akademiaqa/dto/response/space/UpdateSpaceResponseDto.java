package pl.akademiaqa.dto.response.space;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateSpaceResponseDto {

    private String name;

}
