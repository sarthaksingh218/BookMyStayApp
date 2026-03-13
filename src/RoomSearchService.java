public class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms, String[] roomTypes) {

        System.out.println("Available Rooms\n");

        for (int i = 0; i < rooms.length; i++) {

            int available = inventory.getAvailability(roomTypes[i]);

            if (available > 0) {
                System.out.println(roomTypes[i] + ":");
                rooms[i].displayRoomDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }
}