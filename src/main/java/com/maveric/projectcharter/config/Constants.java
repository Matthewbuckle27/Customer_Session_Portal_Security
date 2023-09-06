package com.maveric.projectcharter.config;

public class Constants {

    //API Response Messages
    public static final String CREATED = "Session created successfully";
    public static final String SESSION_NOT_FOUND = "NO SESSION FOUND";
    public static final String CUSTOMER_NOT_FOUND = "Customer NOT found";
    public static final String UPDATED = "Session updated successfully";
    public static final String DELETED = "Session Deleted";
    public static final String ARCHIVED = "Session Archived";
    public static final String CANNOT_DELETE = "Cannot Delete an Archive session";
    public static final String CANNOT_UPDATE = "Cannot Update an Archive session";
    public static final String ALREADY_ARCHIVED = "Session is already Archived";
    public static final String WRONG_STATUS = "Session status passed is not A or X";
    public static final String NOT_ELIGIBLE_TO_ARCHIVE = "Session is not eligible to archive";
    public static final String CANNOT_ARCHIVE = "Cannot Archive Session";
    public static final String CUSTOMER_CREATED = "Customer Created";
    public static final String UNABLE_TO_CREATE = "Unable to Create Session";
    public static final String UNABLE_TO_UPDATE = "Unable to Update Session";
    public static final String UNABLE_TO_GET = "Unable to Load Sessions";
    public static final String UNABLE_TO_DELETE = "Unable to Delete Session";
    public static final String UNABLE_TO_ARCHIVE = "Unable to Archive Session";

    //ID Generator
    public static final String PREFIX_SESSION = "Session";
    public static final String QUERY_SESSION = "SELECT MAX(CAST(SUBSTRING(session_id, 8) AS UNSIGNED)) FROM session";
    public static final String PREFIX_CUSTOMER = "CB";
    public static final String QUERY_CUSTOMER = "SELECT MAX(CAST(SUBSTRING(customer_id, 3) AS UNSIGNED)) FROM customer";
    public static final String PREFIX_FORMAT = "000001";
    public static final String FORMAT = "%06d";

    //Service
    public static final String SESSION_STATUS_A = "A";
    public static final String SESSION_STATUS_X = "X";
    public static final String UPDATED_ON = "updatedOn";
    public static final String MAXIMUM_DORMANT_DAYS = "${maximumDormantDays}";
    public static final String SORT_BY = "${sortSessionsBy}";

    //Logging
    public static final String CONTROLLER = "execution(* com.maveric.projectcharter.controller.*.*(..))";
    public static final String SERVICE = "execution(* com.maveric.projectcharter.service.*.*(..))";
    public static final String RESULT = "result";
    public static final String EX = "ex";
    public static final String EXECUTING = "Executing: ";
    public static final String RESPONSE = "Response: ";
    public static final String EXCEPTION_IN = "Exception in: ";
    public static final String EXCEPTION = ". Exception: ";
    public static final String ARGUMENT = "Request Argument: ";

    //Swagger Config
    public static final String SWAGGER_TITLE = "customer_session_portal";
    public static final String SWAGGER_DESCRIPTION = "session_management_api";
    public static final String SWAGGER_VERSION = "1.0";

    //Controller Mapping
    public static final String CUSTOMER_MAPPING = "/customer";
    public static final String SESSION_MAPPING = "/sessions";
    public static final String CROSS_ORIGIN_URL = "http://localhost:4200";
    public static final String PATH_VARIABLE_STATUS = "/{status}";
    public static final String PATH_VARIABLE_SESSION_ID = "/{sessionId}";
    public static final String PATH_VARIABLE_ARCHIVE = "/archive/{sessionId}";
    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_NO_VALUE = "0";
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_SIZE_VALUE = "5";

    //Entity
    public static final String TABLE_SESSION = "session";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_SESSION_HISTORY = "session_history";
    public static final String SESSION_ID = "session_id";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String SESSION_NAME = "Session_Name";
    public static final String CREATED_BY = "created_By";
    public static final String UPDATE = "Updated_On";
    public static final String CREATED_ON = "Created_On";
    public static final String DELETED_ON = "Deleted_On";
    public static final String REMARKS = "Remarks";
    public static final String STATUS = "Status";
    public static final String SESSION_STRATEGY = "com.maveric.projectcharter.generator.SessionIdGenerator";
    public static final String CUSTOMER_STRATEGY = "com.maveric.projectcharter.generator.CustomerIdGenerator";

    //DTO Validations
    public static final String CUSTOMER_NAME_REQUIRED = "Customer name is required";
    public static final String CUSTOMER_NAME_LENGTH = "Customer name must have at least 3 characters";
    public static final String CUSTOMER_EMAIL_REQUIRED = "Email is required";
    public static final String PROPERTY_ELEMENTS = "totalElements";
    public static final String PROPERTY_PAGES = "totalPages";
    public static final String PROPERTY_SESSION = "session";
    public static final String SESSION_NAME_REQUIRED = "Session name is required";
    public static final String CUSTOMER_ID_REQUIRED = "Customer Id is required";
    public static final String CREATED_BY_REQUIRED = "Created By RM NOT found";
    public static final String REMARKS_REQUIRED = "Notes are required";
    public static final String SESSION_NAME_LENGTH = "Session name must have at least 4 characters";
    public static final String REMARKS_LENGTH = "Notes must be at least 4 characters";

    //Exception
    public static final String API_EXCEPTION = "API Exception";
    public static final String SERVICE_EXCEPTION = "Service Exception";
    public static final String NULL_POINTER_EXCEPTION = "Null Pointer Exception";

}