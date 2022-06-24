package pl.akademiaqa.tests.e2e;

import io.restassured.path.json.JsonPath;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.akademiaqa.dto.request.task.CreateTaskRequestDto;
import pl.akademiaqa.requests.list.CreateListRequest;
import pl.akademiaqa.requests.space.CreateSpaceRequest;
import pl.akademiaqa.requests.space.DeleteSpaceRequest;
import pl.akademiaqa.requests.task.CreateTaskRequest;
import pl.akademiaqa.requests.task.UpdateTaskRequest;

class UpdateTaskE2ETest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTaskE2ETest.class);
    private static String spaceName = "space e2e";
    private static String listName = "lista e2e";
    private static String taskName = "zadanie e2e";
    private String spaceId;
    private String listId;
    private String taskId;

    @Test
    void UpdateTaskE2ETest(){
        spaceId = createSpaceStep();
        LOGGER.info("Space created with id: {}", spaceId);

        listId = createListStep();
        LOGGER.info("List created with id: {}", listId);

        taskId = createTaskStep();
        LOGGER.info("Task created with id: {}", taskId);

        updateTaskStep();
        closeTaskStep();
        deleteSpaceStep();
    }

    private String createSpaceStep(){
        JSONObject json = new JSONObject();
        json.put("name", spaceName);
        final var response = CreateSpaceRequest.createSpace(json);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(spaceName);

        return jsonData.getString("id");
    }

    private String createListStep(){
        JSONObject json = new JSONObject();
        json.put("name", listName);

        final var response = CreateListRequest.createList(json, spaceId);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo(listName);

        return jsonData.getString("id");
    }

    private String createTaskStep(){

        CreateTaskRequestDto taskDto = new CreateTaskRequestDto();
        taskDto.setName(taskName);
        taskDto.setDescription("How it's work?");
        taskDto.setStatus("to do");

        final var response = CreateTaskRequest.createTask(taskDto, listId);

        Assertions.assertThat(response.getName()).isEqualTo(taskName);
        Assertions.assertThat(response.getDescription()).isEqualTo("How it's work?");

        return response.getId();
    }

    private void updateTaskStep(){
        JSONObject updateTask = new JSONObject();
        updateTask.put("name","Zmiana nazwy zadania");
        updateTask.put("description","Zmiana opisu zadania");

        final var response = UpdateTaskRequest.updateTask(updateTask, taskId);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("Zmiana nazwy zadania");
        Assertions.assertThat(jsonData.getString("description")).isEqualTo("Zmiana opisu zadania");
    }

    private void closeTaskStep(){
        JSONObject closeTask = new JSONObject();
        closeTask.put("status", "complete");

        final var response = UpdateTaskRequest.updateTask(closeTask, taskId);

        JsonPath jsonData = response.jsonPath();
        Assertions.assertThat(jsonData.getString("name")).isEqualTo("Zmiana nazwy zadania");
        Assertions.assertThat(jsonData.getString("status.status")).isEqualTo("complete");
    }

    private void deleteSpaceStep(){
        final var response = DeleteSpaceRequest.deleteSpace(spaceId);

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }
}
