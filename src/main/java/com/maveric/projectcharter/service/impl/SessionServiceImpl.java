package com.maveric.projectcharter.service.impl;

import com.maveric.projectcharter.config.Constants;
import com.maveric.projectcharter.dto.ArchiveFlag;
import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.SessionRequestDTO;
import com.maveric.projectcharter.dto.SessionResponseDTO;
import com.maveric.projectcharter.entity.Customer;
import com.maveric.projectcharter.entity.Session;
import com.maveric.projectcharter.entity.SessionHistory;
import com.maveric.projectcharter.entity.SessionStatus;
import com.maveric.projectcharter.exception.ApiRequestException;
import com.maveric.projectcharter.exception.ServiceException;
import com.maveric.projectcharter.repository.CustomerRepository;
import com.maveric.projectcharter.repository.SessionHistoryRepository;
import com.maveric.projectcharter.repository.SessionRepository;
import com.maveric.projectcharter.service.SessionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SessionHistoryRepository sessionHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeleteArchiveResponse deleteArchiveResponse;

    private final int maximumDormantDays;
    private final String sortSessionsBy;

    public SessionServiceImpl() {
        this.maximumDormantDays = 10;
        this.sortSessionsBy = "updatedOn";
    }

    @Autowired
    public SessionServiceImpl(@Value("${maximumDormantDays}") int maximumDormantDays,
                              @Value("${sortSessionsBy}") String sortSessionsBy) {
        this.maximumDormantDays = maximumDormantDays;
        this.sortSessionsBy = sortSessionsBy;
    }

    /**
     * Saves a new session with the provided details.
     *
     * @param sessionRequestDTO The data transfer object containing session details.
     * @throws ApiRequestException If the customer is not found.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @Override
    public SessionResponseDTO saveSession(SessionRequestDTO sessionRequestDTO) {
        try {
            Session session = modelMapper.map(sessionRequestDTO, Session.class);
            Customer customer = customerRepository.findById(sessionRequestDTO.getCustomerId())
                    .orElseThrow(() -> new ApiRequestException(Constants.CUSTOMER_NOT_FOUND));
            session.setCustomer(customer);
            Session savedSession = sessionRepository.save(session);
            SessionResponseDTO sessionResponseDTO = modelMapper.map(savedSession, SessionResponseDTO.class);
            sessionResponseDTO.setArchiveFlag(ArchiveFlag.N);
            return sessionResponseDTO;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves sessions by pagination based on the provided session status, offset, and page size.
     *
     * @param status The status of sessions to retrieve.
     * @param offset        The starting index of the page.
     * @param pagesize      The number of items to retrieve per page.
     * @return A Page of SessionResponseDTO objects containing paginated session information.
     * @throws ApiRequestException if there's an issue with the API request or if session information is not found.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @Override
    public Page<SessionResponseDTO> getSessions(String status, int offset, int pagesize) {

        try {
            if(!(status.equalsIgnoreCase(Constants.SESSION_STATUS_A)||status.equalsIgnoreCase(Constants.SESSION_STATUS_X))){
                throw new ApiRequestException(Constants.WRONG_STATUS);
            }
            SessionStatus sessionStatus = SessionStatus.valueOf(status.toUpperCase());
            Pageable pageable = PageRequest.of(offset, pagesize)
                    .withSort(Sort.by(sortSessionsBy).descending());
            Page<Session> sessions = sessionRepository.findByStatus(sessionStatus, pageable);
            if (sessions.isEmpty()) {
                throw new ApiRequestException(Constants.SESSION_NOT_FOUND);
            }
            Page<SessionResponseDTO> sessionResponseDTOS = sessions.map(session -> modelMapper.map(session, SessionResponseDTO.class));
            sessionResponseDTOS.forEach(session -> {
                if(session.getStatus().equals(SessionStatus.A)){
                    LocalDateTime updatedOn = session.getUpdatedOn();
                    LocalDateTime archiveDate = updatedOn.plusDays(maximumDormantDays);
                    if (archiveDate.isBefore(LocalDateTime.now())) {
                        session.setArchiveFlag(ArchiveFlag.Y);
                    } else {
                        session.setArchiveFlag(ArchiveFlag.N);
                    }
                }
                else {
                    session.setArchiveFlag(ArchiveFlag.NA);
                }
            });
            return sessionResponseDTOS;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    /**
     * Update a session identified by the provided session ID using the information from the given SessionRequestDTO.
     *
     * @param sessionId         The ID of the session to be updated.
     * @param sessionRequestDTO The data to update the session with, encapsulated in a SessionRequestDTO.
     * @return The SessionResponseDTO containing the updated session's details.
     * @throws ApiRequestException If the session or customer is not found, or if there's an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @Override
    public SessionResponseDTO updateSession(String sessionId, SessionRequestDTO sessionRequestDTO) {
        try {
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new ApiRequestException(Constants.SESSION_NOT_FOUND));
            Customer customer = customerRepository.findById(sessionRequestDTO.getCustomerId())
                    .orElseThrow(() -> new ApiRequestException(Constants.CUSTOMER_NOT_FOUND));
            if(session.getStatus().equals(SessionStatus.X)){
                throw new ApiRequestException(Constants.CANNOT_UPDATE);
            }
            session.setSessionName(sessionRequestDTO.getSessionName());
            session.setRemarks(sessionRequestDTO.getRemarks());
            session.setCreatedBy(sessionRequestDTO.getCreatedBy());
            session.setCustomer(customer);
            session.setUpdatedOn(LocalDateTime.now());
            Session updatedSession = sessionRepository.save(session);
            SessionResponseDTO sessionResponseDTO = modelMapper.map(updatedSession, SessionResponseDTO.class);
            sessionResponseDTO.setArchiveFlag(ArchiveFlag.N);
            return sessionResponseDTO;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Delete a session identified by the provided session ID. The method also creates a SessionHistory record
     * before deleting the session.
     *
     * @param sessionId The ID of the session to be deleted.
     * @throws ApiRequestException If the session is not found, or if there's an issue with the API request.
     * @throws ServiceException    If there is an issue in the service layer during processing.
     */
    @Override
    public DeleteArchiveResponse deleteSession(String sessionId) {
        try {
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new ApiRequestException(Constants.SESSION_NOT_FOUND));
            if(session.getStatus().equals(SessionStatus.X)){
                throw new ApiRequestException(Constants.CANNOT_DELETE);
            }
            session.setStatus(SessionStatus.D);
            SessionHistory sessionHistory = modelMapper.map(session, SessionHistory.class);
            sessionHistory.setDeletedOn(LocalDateTime.now());
            sessionHistoryRepository.save(sessionHistory);
            deleteArchiveResponse.setMessage(Constants.DELETED);
            deleteArchiveResponse.setHttpStatus(HttpStatus.OK);
            return deleteArchiveResponse;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Archives a session identified by the provided session ID.
     *
     * @param sessionId The unique identifier of the session to be archived.
     * @return A Response indicating the result of the archive operation.
     * @throws ServiceException If there is an issue with the service while archiving the session.
     */
    @Override
    public DeleteArchiveResponse archiveSession(String sessionId) {
        try {
            Session session = sessionRepository.findById(sessionId)
                    .orElseThrow(() -> new ApiRequestException(Constants.SESSION_NOT_FOUND));
            if(session.getStatus().equals(SessionStatus.X)){
                throw new ApiRequestException(Constants.ALREADY_ARCHIVED);
            }
            session.setStatus(SessionStatus.X);
            sessionRepository.save(session);
            deleteArchiveResponse.setMessage(Constants.ARCHIVED);
            deleteArchiveResponse.setHttpStatus(HttpStatus.OK);
            return deleteArchiveResponse;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
