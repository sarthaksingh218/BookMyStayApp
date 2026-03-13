import java.util.*;

public class RoomAllocationService {

    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocations;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    public void processBookingRequests(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation reservation = queue.getNextRequest();
            String roomType = reservation.getRoomType();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.updateAvailability(roomType, available - 1);

                System.out.println("Reservation confirmed for "
                        + reservation.getGuestName()
                        + " | Room Type: " + roomType
                        + " | Room ID: " + roomId);
            } else {
                System.out.println("No rooms available for "
                        + reservation.getGuestName()
                        + " (" + roomType + ")");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String id;
        do {
            id = roomType.substring(0, 2).toUpperCase()
                    + (100 + new Random().nextInt(900));
        } while (allocatedRoomIds.contains(id));

        return id;
    }
}