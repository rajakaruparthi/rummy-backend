package com.local.rummy.serviccImpl;

import com.google.gson.Gson;
import com.local.rummy.entity.*;
import com.local.rummy.repository.FinalCardsRepository;
import com.local.rummy.repository.RoomRepository;
import com.local.rummy.request.DeletePlayerRequest;
import com.local.rummy.request.FinalCards;
import com.local.rummy.request.RoomId;
import com.local.rummy.request.ShuffleCardsRequest;
import com.local.rummy.response.DiscardCardsResponse;
import com.local.rummy.service.RummyMiscService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RummyMiscServiceImpl implements RummyMiscService {

    Logger logger = LoggerFactory.getLogger(RummyMiscServiceImpl.class);

    private int decks;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FinalCardsRepository finalCardsRepository;

    @Override
    public DiscardCardsResponse shuffleCards(ShuffleCardsRequest request) {
        String roomId = request.getRoomId();
        int distributeIndex = request.getDistributeIndex();

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Room room = roomOptional.get();
        logger.info("room id -- {}", room.getId());
        int totalPlayers = 0;
        if (room.getPlayersList() != null) {
            totalPlayers = room.getPlayersList().size();
        }

        if (totalPlayers < 3) {
            decks = 1;
        } else if (totalPlayers > 2 && totalPlayers < 7) {
            decks = 2;
        } else if (totalPlayers >= 7 && totalPlayers < 10) {
            decks = 3;
        }
        List<Integer> cards = new ArrayList<>();
        List<String> cardValues = null;

        Random r = new Random();
        for (int i = 1; i <= 52 * decks + 1; i++) {
            cards.add(i);
        }

        Collections.shuffle(cards);
        Stack<String> deck = new Stack<>();
        cards.stream().map(this::getCard).forEach(each -> deck.push(each));
        DiscardCardsResponse discardResponse = distributeCards(deck, room, distributeIndex);
        logger.info("discard response -- {}", discardResponse);
        return discardResponse;
    }

    @Override
    public void saveFinalCards(List<FinalCards> finalCards, String roomId) {
        Gson gson = new Gson();
        logger.info("inside saveFinalCards with request object {}", gson.toJson(finalCards));
        FinalShowCards finalShowCards = new FinalShowCards();
        finalShowCards.setRoomId(roomId);
        List<PlayersAttrs> playersAttrsList = finalCards.stream()
                .map(each -> new PlayersAttrs(each.getCards(), each.getPlayerName(), each.isFoldedFlag()))
                .collect(Collectors.toList());
        finalShowCards.setPlayersAttrsList(playersAttrsList);

        String s = gson.toJson(finalShowCards);
        logger.info("show cards inside save show cards {}", s);
        logger.info("deleting previous show cards");

        try {
            deleteAllShowCards();
        } catch (Exception e) {
            logger.error("");
        }

        try {
            finalCardsRepository.save(finalShowCards);
        } catch (Exception e) {
            logger.error("exception occurred while saving the final cards");
        }
    }

    @Override
    public FinalShowCards getFinalShowCards(RoomId roomId) {
        logger.info("room id request {}", roomId.getId());
        List<String> roomIds = new ArrayList<>();
        roomIds.add(roomId.getId());
        Optional<List<FinalShowCards>> finalcardsOpt = null;
        try {
            finalcardsOpt = finalCardsRepository.findByRoomId(roomId.getId());
        } catch (Exception e) {
            logger.error("error occurred at {}", e);
        }
        Gson gson = new Gson();
        logger.info("list of final show cards {}", gson.toJson(finalcardsOpt.get()));

        return finalcardsOpt.isPresent() ? finalcardsOpt.get().get(0) : null;
    }

    @Override
    public Room deletePlayersByRoom(DeletePlayerRequest deletePlayerRequest) {
        Optional<Room> roomOptional = roomRepository.findById(deletePlayerRequest.getRoomId());
        Room room = roomOptional.get();
        Gson gson = new Gson();
        List<Players> playersList = room.getPlayersList();
        logger.info("inside delete players by room method before deleting ..." + gson.toJson(playersList));

        playersList.remove(deletePlayerRequest.getPlayerIndex());
        room.setPlayersList(playersList);

        logger.info("inside delete players by room method after deleting ... index {}, {} ",
                deletePlayerRequest.getPlayerIndex(), gson.toJson(playersList));
        roomRepository.save(room);
        return room;
    }

    @Override
    public void deleteAllShowCards() {
        try {
            logger.info("came inside delete all show cards .. ");
            finalCardsRepository.deleteAll();
        } catch (Exception e) {
            logger.error("Exception occurred while deleting the cards {}", e.toString());
        }
    }

    private DiscardCardsResponse distributeCards(Stack<String> cardValues, Room room, int distributeIndex) {

        DiscardCardsResponse discardResponse = new DiscardCardsResponse();

        int totalPlayers = 0;

        if (room != null && room.getPlayersList() != null) {
            List<Players> players = room.getPlayersList();
            totalPlayers = players.size();
            String[][] cardsAry = new String[totalPlayers][13];
            List<String[]> cardsByPerson = null;
            Gson gson = new Gson();
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < totalPlayers; j++) {
                    cardsAry[j][i] = cardValues.pop();
                }
                cardsByPerson = Arrays.asList(cardsAry);
            }

            logger.info("cards before distributions .. {}", gson.toJson(cardsByPerson));

            PlayerCards[] listOfPlayercards = new PlayerCards[totalPlayers];

            for (int i = 0; i < players.size(); i++) {
                listOfPlayercards[(i + 1 + distributeIndex) % totalPlayers] =
                        new PlayerCards(players.get((i + 1 + distributeIndex) % totalPlayers).getName(), cardsByPerson.get(i));
            }

            logger.info("cards after distributions .. {}", gson.toJson(listOfPlayercards));

            discardResponse.setPlayersCards(Arrays.asList(listOfPlayercards));
            discardResponse.setOpenCard(cardValues.pop());
            int jokerIndex = getJokerIndex(cardValues);
            discardResponse.setOpenJoker(cardValues.get(jokerIndex));
            cardValues.remove(jokerIndex);
            discardResponse.setDeck(cardValues);
        }
        return discardResponse;
    }

    private int getJokerIndex(Stack<String> cardValues) {
        Random random = new Random();
        int jokerIndex = random.nextInt(cardValues.size());
        return jokerIndex;
    }

    public String getCard(Integer element) {
        int division = (int) Math.ceil((double) element / 13);
        String whichCard = null;
        String card = this.mapCards(element % 13);
        if (division == 1 || division == 5 || division == 9) {
            whichCard = card + 'C';
        }
        if (division == 2 || division == 6 || division == 10) {
            whichCard = card + 'D';
        }
        if (division == 3 || division == 7 || division == 11) {
            whichCard = card + 'H';
        }
        if (division == 4 || division == 8 || division == 12) {
            whichCard = card + 'S';
        } else if (element == 52 * decks + 1) {
            whichCard = "joker";
        }
        return whichCard;
    }


    public String mapCards(Integer cardNum) {
        HashMap<Integer, String> map1 = new HashMap<>();
        map1.put(1, "A");
        map1.put(2, "2");
        map1.put(3, "3");
        map1.put(4, "4");
        map1.put(5, "5");
        map1.put(6, "6");
        map1.put(7, "7");
        map1.put(8, "8");
        map1.put(9, "9");
        map1.put(10, "10");
        map1.put(11, "J");
        map1.put(12, "Q");
        map1.put(0, "K");
        return map1.get(cardNum);
    }
}
