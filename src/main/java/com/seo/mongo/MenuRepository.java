package com.seo.mongo;

import com.seo.entity.Menus;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

@CacheConfig(cacheNames = "menu")
public interface MenuRepository extends MongoRepository<Menus, String> {

    Menus findByUrl(String url);

    @Cacheable
    Page<Menus> findAll(Pageable pageable);
}
