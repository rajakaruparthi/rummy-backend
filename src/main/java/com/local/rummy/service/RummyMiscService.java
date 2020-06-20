package com.local.rummy.service;

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
}
