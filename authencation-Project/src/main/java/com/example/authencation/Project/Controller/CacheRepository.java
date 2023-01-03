package com.example.authencation.Project.Controller;

import java.util.Optional;

public interface CacheRepository {

    void put(String key, Integer value);

    Optional<String> get(String key);

    void remove(String key);

}