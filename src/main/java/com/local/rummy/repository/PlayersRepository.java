package com.local.rummy.repository;

import com.local.rummy.entity.Players;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersRepository extends MongoRepository<Players, String> {

}
