package hexlet.code.controller;

import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
@AllArgsConstructor
public class TaskStatusController {

    private final TaskStatusService statusService;

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get list of all taskStatuses")
    @ApiResponse(responseCode = "200", description = "List of all statuses")
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> index() {
        List<TaskStatusDTO> statusesDto = statusService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(statusesDto.size()))
                .body(statusesDto);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get a taskStatus by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status found"),
            @ApiResponse(responseCode = "404", description = "Status with that id not found")
    })
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO show(
            @Parameter(description = "Id of status to be found")
            @PathVariable Long id) {
        return statusService.getById(id);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Create new taskStatus")
    @ApiResponse(responseCode = "201", description = "Status created")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(
            @Parameter(description = "Status data to save")
            @Valid @RequestBody TaskStatusDTO data) {
        return statusService.create(data);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Update taskStatus by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated"),
            @ApiResponse(responseCode = "404", description = "Status with that id not found")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO update(
            @Parameter(description = "Id of status to be updated")
            @PathVariable Long id,
            @Parameter(description = "Status data to update")
            @Valid @RequestBody TaskStatusDTO data) {
        return statusService.update(id, data);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete taskStatus by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Status deleted"),
            @ApiResponse(responseCode = "404", description = "Status with that id hot found")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Id of status to be deleted")
            @PathVariable Long id) {
        statusService.delete(id);
    }
}
