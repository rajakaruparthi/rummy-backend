package com.local.rummy.serviccImpl;

import com.local.rummy.entity.*;
import com.local.rummy.repository.FinalCardsRepository;
import com.local.rummy.repository.RoomRepository;
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
        DiscardCardsResponse discardResponse = distributeCards(deck, room);
        logger.info("discard response -- {}", discardResponse);
        return discardResponse;
    }

    @Override
    public void saveFinalCards(List<FinalCards> finalCards, String roomId) {
        logger.info("inside saveFinalCards with request object {}", finalCards.get(0).cards);
        FinalShowCards finalShowCards = new FinalShowCards();
        finalShowCards.setRoomId(roomId);
        List<PlayersAttrs> playersAttrsList = finalCards.stream()
                .map(each -> new PlayersAttrs(each.getCards(), each.getPlayerName(),  each.isFoldedFlag()))
                .collect(Collectors.toList());
        finalShowCards.setPlayersAttrsList(playersAttrsList);
        finalCardsRepository.save(finalShowCards);
    }


    @Override
    public FinalShowCards getFinalShowCards(RoomId roomId) {
        logger.info("room id request {}", roomId.getId());
        FinalShowCards finalShowCards = null;
        try {
            finalShowCards = finalCardsRepository.findById(roomId.getId()).get();
        } catch (Exception e) {
            logger.error("error occurred at {}", e);
        }
        return finalShowCards;
    }

    private DiscardCardsResponse distributeCards(Stack<String> cardValues, Room room) {

        DiscardCardsResponse discardResponse = new DiscardCardsResponse();

        int totalPlayers = 0;

        if (room != null && room.getPlayersList() != null) {
            List<Players> players = room.getPlayersList();
            totalPlayers = players.size();
            String[][] cardsAry = new String[totalPlayers][13];
            List<String[]> cardsByPerson = null;
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < totalPlayers; j++) {
                    cardsAry[j][i] = cardValues.pop();
                }
                cardsByPerson = Arrays.asList(cardsAry);
            }

            List<PlayerCards> listOfPlayercards = new ArrayList<>();

            for (int i = 0; i < players.size(); i++) {
                listOfPlayercards.add(new PlayerCards(players.get(i).getName(), cardsByPerson.get(i)));
            }
            discardResponse.setPlayersCards(listOfPlayercards);
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
