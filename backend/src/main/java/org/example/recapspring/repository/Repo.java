package org.example.recapspring.repository;

import org.example.recapspring.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface Repo extends MongoRepository<Todo,String> {
}
