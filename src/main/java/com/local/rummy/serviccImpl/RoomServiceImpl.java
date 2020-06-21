package com.local.rummy.serviccImpl;

import com.local.rummy.entity.Players;
import com.local.rummy.entity.Room;
import com.local.rummy.repository.RoomRepository;
import com.local.rummy.request.RoomId;
import com.local.rummy.request.UpdateRoomRequest;
import com.local.rummy.response.RoomIdResponse;
import com.local.rummy.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room getRoomById(RoomId quantity) {
        Optional<Room> id = roomRepository.findById(quantity.getId());
        if (id.isPresent()) {
            return id.get();
        }
        return null;
    }

    @Override
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        Iterable<Room> roomsIterable = null;
        try {
            roomsIterable = roomRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        roomsIterable.iterator().forEachRemaining(room -> rooms.add(room));
        return rooms;
    }

    public Room updateRooms(UpdateRoomRequest updateRoom) {
        Room room = roomRepository.findById(updateRoom.getRoomId()).get();
        Players player = new Players();
        player.setName(updateRoom.getUsername());
        List<Players> playersList = room.getPlayersList();
        if(playersList == null) {
            playersList = new ArrayList<>();
        }

        playersList.add(player);
        room.setPlayersList(playersList);
        logger.info("room with the players added -- {}", player.getName());
        try{
            roomRepository.save(room);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return room;
    }

    @Override
    public List<Players> getListOfUsersByRoomId(String id) {
        Optional<Room> roomById = roomRepository.findById(id);
        return roomById.get().getPlayersList();
    }

    @Override
    public RoomIdResponse deleteRoom(String id) {
        roomRepository.deleteById(id);
        RoomIdResponse response = new RoomIdResponse();
        response.setRoomId(id);
        return response;
    }

    @Override
    public RoomIdResponse createRoom(Room room) {
        roomRepository.save(room);
        RoomIdResponse response = new RoomIdResponse();
        response.setRoomId(room.getId());
        return response;
    }

    private List<Room> listOfRooms() {
        List<Room> rooms = null;
        try {
            rooms = roomRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

}
