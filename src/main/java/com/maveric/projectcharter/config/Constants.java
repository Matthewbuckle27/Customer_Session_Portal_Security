package com.maveric.projectcharter.config;

public class Constants {

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
    public static final String PREFIX_SESSION = "Session";
    public static final String QUERY_SESSION = "SELECT MAX(CAST(SUBSTRING(session_id, 8) AS UNSIGNED)) FROM session";
    public static final String PREFIX_CUSTOMER = "CB";
    public static final String QUERY_CUSTOMER = "SELECT MAX(CAST(SUBSTRING(customer_id, 3) AS UNSIGNED)) FROM customer";
    public static final String SESSION_STATUS_A = "A";
    public static final String SESSION_STATUS_X = "X";
    public static final String EXECUTING = "Executing: ";
    public static final String RESPONSE = "Response: ";
    public static final String EXCEPTION_IN = "Exception in: ";
    public static final String EXCEPTION = ". Exception: ";
    public static final String ARGUMENT = "Request Argument: ";

}
