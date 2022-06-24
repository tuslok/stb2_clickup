package pl.akademiaqa.tests.space;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.space.UpdateSpaceRequest;

public class CreateSpaceTest {

    private static final String SPACE_NAME = "MY NEW SPACE";
    private static final String UPDATE_SPACE_NAME = "UPDATED SPACE";


    @Test
    void createSpaceTest() {

        JSONObject space = new JSONObject();
        space.put("name", SPACE_NAME);

        var response = CreateSpaceRequest.createSpace(space);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("name")).isEqualTo(SPACE_NAME);

        final var spaceId = response.jsonPath().getString("id");

        JSONObject updateSpace = new JSONObject();
        updateSpace.put("name", UPDATE_SPACE_NAME);

        final var updateResponse = UpdateSpaceRequest.updateSpace(updateSpace, spaceId);
        Assertions.assertThat(updateResponse.statusCode()).isEqualTo(200);
        Assertions.assertThat(updateResponse.jsonPath().getString("name")).isEqualTo(UPDATE_SPACE_NAME);

        final var deleteResponse = DeleteSpaceRequest.deleteSpace(spaceId);
        Assertions.assertThat(deleteResponse.statusCode()).isEqualTo(200);
    }
}
