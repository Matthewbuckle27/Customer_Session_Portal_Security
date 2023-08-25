package com.maveric.projectcharter.controller;

import com.maveric.projectcharter.config.Constants;
import com.maveric.projectcharter.dto.*;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.exception.ServiceException;
import com.maveric.projectcharter.service.SessionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CreateUpdateResponse createUpdateResponse;

    @Autowired
    private DeleteArchiveResponse deleteArchiveResponse;

    @Autowired
    private GetResponse<SessionResponseDTO> getResponse;

    /**
     * Create a new session.
     *
     * @param sessionRequestDTO The session details to create.
     * @return ResponseEntity containing API message.
     * @throws ApiRequestException if there's an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @PostMapping
    public ResponseEntity<CreateUpdateResponse> createSession(@RequestBody @Valid SessionRequestDTO sessionRequestDTO)
            throws ApiRequestException, ServiceException {
        SessionResponseDTO sessionResponseDTO = sessionService.saveSession(sessionRequestDTO);
        createUpdateResponse.setMessage(Constants.CREATED);
        createUpdateResponse.setHttpStatus(HttpStatus.CREATED);
        createUpdateResponse.setSessionResponseDTO(sessionResponseDTO);
        return new ResponseEntity<>(createUpdateResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves sessions by pagination based on the provided status, offset, and page size.
     *
     * @param status   The status of sessions to retrieve. (e.g., "active", "inactive")
     * @param offset   The starting index of the page.
     * @param pageSize The number of items to retrieve per page.
     * @return A ResponseEntity containing a Page of SessionResponseDTO objects.
     * @throws ApiRequestException if there's an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @GetMapping("/{status}")
    public ResponseEntity<GetResponse<SessionResponseDTO>> getSessions
    (@PathVariable String status, @RequestParam(name = "pageNo", defaultValue = "0") int offset,
     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize)
            throws ApiRequestException, ServiceException {
        Page<SessionResponseDTO> sessionResponseDTOPage = sessionService.getSessions(status, offset, pageSize);
        getResponse.setTotalElements(sessionResponseDTOPage.getTotalElements());
        getResponse.setTotalPages(sessionResponseDTOPage.getTotalPages());
        getResponse.setSession(sessionResponseDTOPage.getContent());
        return new ResponseEntity<>(getResponse, HttpStatus.OK);
    }

    /**
     * Controller method to update a session identified by the provided session ID.
     *
     * @param sessionId         The ID of the session to be updated.
     * @param sessionRequestDTO The data to update the session with, in the form of a SessionRequestDTO.
     * @return A ResponseEntity containing a CustomResponse with details about the operation's outcome.
     * @throws ApiRequestException If there is an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @PutMapping("/{sessionId}")
    public ResponseEntity<CreateUpdateResponse> updateSession(@PathVariable @NotNull String sessionId, @RequestBody @Valid SessionRequestDTO sessionRequestDTO)
            throws ApiRequestException, ServiceException {
        SessionResponseDTO sessionResponseDTO = sessionService.updateSession(sessionId, sessionRequestDTO);
        createUpdateResponse.setMessage(Constants.UPDATED);
        createUpdateResponse.setHttpStatus(HttpStatus.OK);
        createUpdateResponse.setSessionResponseDTO(sessionResponseDTO);
        return new ResponseEntity<>(createUpdateResponse, HttpStatus.OK);
    }

    /**
     * Controller method to delete a session identified by the provided session ID.
     *
     * @param sessionId The ID of the session to be deleted.
     * @return A ResponseEntity containing a DeleteResponse with details about the operation's outcome.
     * @throws ApiRequestException If there is an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<DeleteArchiveResponse> deleteSession(@PathVariable @NotNull String sessionId)
            throws ApiRequestException, ServiceException {
        deleteArchiveResponse = sessionService.deleteSession(sessionId);
        return new ResponseEntity<>(deleteArchiveResponse, HttpStatus.OK);
    }

    /**
     * Archives a session identified by the provided session ID.
     *
     * @param sessionId The unique identifier of the session to be archived.
     * @return A ResponseEntity containing a Response indicating the result of the operation.
     * @throws ApiRequestException If there is an issue with the API request.
     * @throws ServiceException    If there is an issue with the service while archiving the session.
     */
    @PutMapping("/archive/{sessionId}")
    public ResponseEntity<DeleteArchiveResponse> archiveSession(@PathVariable @NotNull String sessionId)
            throws ApiRequestException, ServiceException {
        deleteArchiveResponse = sessionService.archiveSession(sessionId);
        return new ResponseEntity<>(deleteArchiveResponse, HttpStatus.OK);
    }
}
