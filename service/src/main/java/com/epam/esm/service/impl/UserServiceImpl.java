package com.epam.esm.service.impl;

import com.epam.esm.config.MessageByLang;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.creteria.EntityPage;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.UserConvert;
import com.epam.esm.pagination.Page;
import com.epam.esm.pagination.PaginationResult;
import com.epam.esm.repository.UserDao;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public PaginationResult<UserDto> getAll(EntityPage entityPage) {
        PaginationResult<User> userList = userDao.findAll(entityPage);
        if (entityPage.getPage() == 1 && userList.getRecords().isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }
        List<UserDto> userDtos = userList.getRecords()
                .stream()
                .map(UserConvert::toDto)
                .collect(Collectors.toList());

        return new PaginationResult<>(
                new Page(
                        userList.getPage().getCurrentPageNumber(),
                        userList.getPage().getLastPageNumber(),
                        userList.getPage().getPageSize(),
                        userList.getPage().getTotalRecords()),
                userDtos
        );
    }

    @Override
    public UserDto getById(long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + id);
        }
        return UserConvert.toDto(optionalUser.get());
    }

    @Override
    public UserDto insert(UserDto userDto) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
