package com.dev.coder.multiDbConn.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T> {
    /**
     * Get list of items of type T
     * @return List<T>
     */
    List<T> list();

    /**
     * Get an item with specified id
     * @param id id of item to be retrieved
     * @return Optional<T>
     */
    Optional<T> get(int id);

    /**
     * Add item of type T
     * @param data item to be added
     */
    void create(T data);


    /**
     * Update an item of given id with specified data
     * @param id item to be updated
     * @param data parameters to be modified
     */
    void update(int id, T data);


    /**
     * Delete an item
     * @param id id of item to be deleted
     */
    void delete(int id);
}
