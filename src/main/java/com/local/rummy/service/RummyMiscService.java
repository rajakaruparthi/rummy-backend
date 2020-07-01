package com.local.rummy.service;

import com.local.rummy.entity.FinalShowCards;
import com.local.rummy.entity.Room;
import com.local.rummy.request.DeletePlayerRequest;
import com.local.rummy.request.FinalCards;
import com.local.rummy.request.RoomId;
import com.local.rummy.request.ShuffleCardsRequest;
import com.local.rummy.response.DiscardCardsResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public interface RummyMiscService {

    @RequestMapping(path = "/shuffle-cards", method = RequestMethod.POST)
    DiscardCardsResponse shuffleCards(@RequestBody ShuffleCardsRequest request);

    @RequestMapping(path = "/save-final-cards/{id}", method = RequestMethod.POST)
    void saveFinalCards(@RequestBody List<FinalCards> finalCards, @PathVariable("id") String id);

    @RequestMapping(path = "/pull-final-cards", method = RequestMethod.POST)
    FinalShowCards getFinalShowCards(@RequestBody RoomId roomId);

    @RequestMapping(path = "/delete-player", method = RequestMethod.POST)
    Room deletePlayersByRoom(@RequestBody DeletePlayerRequest deletePlayerRequest);
}
