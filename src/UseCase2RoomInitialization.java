public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        System.out.println("=== SINGLE ROOM ===");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleRoomAvailability);

        System.out.println("\n=== DOUBLE ROOM ===");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleRoomAvailability);

        System.out.println("\n=== SUITE ROOM ===");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteRoomAvailability);
    }
}