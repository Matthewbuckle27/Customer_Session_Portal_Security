package com.maveric.projectcharter.service;

import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.UserDTO;

public interface UserService {
    DeleteArchiveResponse createUser(UserDTO userDTO);
    boolean checkUserName(String username);
}
