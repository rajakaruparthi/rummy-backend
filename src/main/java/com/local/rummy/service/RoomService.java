package com.local.rummy.service;

import com.local.rummy.entity.Players;
import com.local.rummy.entity.Room;
import com.local.rummy.request.RoomId;
import com.local.rummy.request.UpdateRoomRequest;
import com.local.rummy.response.RoomIdResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public interface RoomService {

    @RequestMapping(method = RequestMethod.POST, path = "/get-room-by-id")
    Room getRoomById(@RequestBody RoomId quantity);

    @RequestMapping(method = RequestMethod.GET, path = "/get-rooms")
    List<Room> getRooms();

    @RequestMapping(method = RequestMethod.POST, path = "/create-room")
    RoomIdResponse createRoom(@RequestBody Room room);

    @RequestMapping(method = RequestMethod.POST, path = "/update-room")
    Room updateRooms(@RequestBody UpdateRoomRequest updateRoomRequest);

    @RequestMapping(method = RequestMethod.GET, path = "/get-users-by-room/{id}")
    List<Players> getListOfUsersByRoomId(@PathVariable("id") String id);

    @RequestMapping(method = RequestMethod.DELETE, path = "/delete-room/{id}")
    RoomIdResponse deleteRoom(@PathVariable("id") String id);

    class AdminService {
    }
}
