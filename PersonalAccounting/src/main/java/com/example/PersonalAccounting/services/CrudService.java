package com.example.PersonalAccounting.services;

import java.util.List;

public interface CrudService<T> {

    void create(T toCreate);

    List<T> getAll();

    T getOne(int id);

    void update(int id, T updated);

    void delete(int id);
}
