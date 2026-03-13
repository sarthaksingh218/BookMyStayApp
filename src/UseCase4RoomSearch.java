public class UseCase4RoomSearch {

    public static void main(String[] args) {

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        Room[] rooms = {singleRoom, doubleRoom, suiteRoom};
        String[] roomTypes = {"Single Room", "Double Room", "Suite Room"};

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        System.out.println("Hotel Room Search\n");

        searchService.searchAvailableRooms(rooms, roomTypes);
    }
}