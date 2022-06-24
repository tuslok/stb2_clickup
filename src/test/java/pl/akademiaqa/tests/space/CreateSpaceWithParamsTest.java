package pl.akademiaqa.tests.space;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.akademiaqa.dto.request.space.UpdateSpaceRequestDto;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.space.UpdateSpaceRequest;

import java.util.stream.Stream;

class CreateSpaceWithParamsTest {

    private static final String UPDATE_SPACE_NAME = "UPDATED SPACE AGAIN";

    @ParameterizedTest(name = "Create space with space name: {0}")
    @DisplayName("Create space with valid space name")
    @MethodSource("createSpaceData")
    void createSpaceTest(String spaceName) {

        JSONObject space = new JSONObject();
        space.put("name", spaceName);

        var response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(spaceName);

        final var spaceId = response.jsonPath().getString("id");

        UpdateSpaceRequestDto spaceDto = new UpdateSpaceRequestDto();
        spaceDto.setName(UPDATE_SPACE_NAME);

        final var updateSpace = UpdateSpaceRequest.updateSpace(spaceDto, spaceId);
        Assertions.assertThat(updateSpace.getName()).isEqualTo(UPDATE_SPACE_NAME);

        final var deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }

    private static Stream<Arguments> createSpaceData(){
        return Stream.of(
                Arguments.of("TEST SPACE"),
                Arguments.of("123"),
                Arguments.of("$")
        );
    }
}
