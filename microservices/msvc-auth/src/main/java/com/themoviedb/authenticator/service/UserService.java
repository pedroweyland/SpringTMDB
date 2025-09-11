package com.themoviedb.authenticator.service;

import com.themoviedb.authenticator.model.exception.InvalidTokenException;
import com.themoviedb.authenticator.model.exception.InvalidUserDataException;
import com.themoviedb.authenticator.model.exception.UserNotFoundException;
import com.themoviedb.authenticator.jwt.JwtService;
import com.themoviedb.authenticator.model.entity.User;
import com.themoviedb.authenticator.repository.UserRepository;
import com.themoviedb.authenticator.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserDto getCurrentUser(String authHeader) throws InvalidTokenException, UserNotFoundException {

        User user = jwtService.getUserFromToken(authHeader);

        return usertoUserDto(user);
    }

    public UserDto updateUser(String authHeader, UserDto userDto) throws UserNotFoundException, InvalidTokenException, InvalidUserDataException {

        // Obtengo el usuario con el token
        User user = jwtService.getUserFromToken(authHeader);

        // Validar y actualizar campos
        user = validateUserDto(userDto, user);

        // Guardo los cambios
        userRepository.save(user);

        return usertoUserDto(user);
    }

    private User validateUserDto(UserDto dto, User user) throws InvalidUserDataException {

        // Valido si no estan vacios y seteo al usuario real
        if (StringUtils.hasText(dto.getFirstName())) user.setFirstName(dto.getFirstName());
        if (StringUtils.hasText(dto.getLastName())) user.setLastName(dto.getLastName());
        if (StringUtils.hasText(dto.getCountry())) user.setCountry(dto.getCountry());
        if (StringUtils.hasText(dto.getPhone())) user.setPhone(dto.getPhone());
        if (StringUtils.hasText(dto.getAddress())) user.setAddress(dto.getAddress());

        return user;
    }

    private UserDto usertoUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .country(user.getCountry())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
    }
}
