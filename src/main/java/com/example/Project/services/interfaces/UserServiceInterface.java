package com.example.Project.services.interfaces;

import com.example.Project.model.entity.Users;

/**
 * Интерфейс для работы с пользователями
 */
public interface UserServiceInterface {

    /**
     * Найти пользователя по ID
     * @param id идентификатор пользователя
     * @return пользователь или null, если не найден
     */
    Users getUserById(int id);

    /**
     * Найти пользователя по email
     * @param email email пользователя
     * @return пользователь или null, если не найден
     */
    Users getUserByEmail(String email);

    /**
     * Обновить данные пользователя
     * @param user объект пользователя с обновленными данными
     * @return обновленный пользователь
     */
    Users updateUserById(Users user);

    /**
     * Удалить пользователя по ID
     * @param id идентификатор пользователя
     * @return удаленный пользователь
     */
    Users deleteUserById(int id);

    /**
     * Создать нового пользователя
     * @param user данные нового пользователя
     * @return созданный пользователь
     */
    Users createUser(Users user);
}
