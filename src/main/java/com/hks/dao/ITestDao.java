package com.hks.dao;

import com.hks.entity.User;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ITestDao {
    /**
     * @return
     */
    @Cacheable("sqlCache")
    int test();

    @Cacheable(value = "sqlCache", key = "#a0.id")
    List<User> findAllUser(User user);
}
