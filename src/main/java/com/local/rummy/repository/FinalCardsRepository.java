package com.local.rummy.repository;

import com.local.rummy.entity.FinalShowCards;
import com.local.rummy.request.FinalCards;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinalCardsRepository extends MongoRepository<FinalShowCards, String> {

    Optional<List<FinalShowCards>> findByRoomId(String roomId);
}
