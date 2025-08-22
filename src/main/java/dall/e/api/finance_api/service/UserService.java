package dall.e.api.finance_api.service;

import dall.e.api.finance_api.dto.UserDto;
import dall.e.api.finance_api.dto.UserRequest;
import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserRequest request);
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByEmail(String email);
    List<UserDto> users = userService.getAllUsers();

    UserDto updateUser(Long id, UserDto userDTO);
    void deleteUser(Long id);
}