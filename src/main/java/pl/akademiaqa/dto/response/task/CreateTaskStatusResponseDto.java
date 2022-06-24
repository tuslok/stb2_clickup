package pl.akademiaqa.dto.response.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskStatusResponseDto {

    private String status;
    private String type;

}
