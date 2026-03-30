import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void decrease(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increase(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        reservations.remove(reservationId);
    }

    public boolean exists(String reservationId) {
        return reservations.containsKey(reservationId);
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = history.getReservation(reservationId);

        rollbackStack.push(r.getRoomId());

        inventory.increase(r.getRoomType());

        history.removeReservation(reservationId);

        System.out.println("Booking " + reservationId + " cancelled successfully.");
        System.out.println("Released Room ID pushed to rollback stack: " + rollbackStack.peek());
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R101", "Amit", "Single", "S1");
        Reservation r2 = new Reservation("R102", "Neha", "Double", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        inventory.decrease("Single");
        inventory.decrease("Double");

        CancellationService cancellationService = new CancellationService(inventory, history);

        inventory.displayInventory();
        System.out.println();

        cancellationService.cancelBooking("R101");

        System.out.println();
        inventory.displayInventory();

        System.out.println();
        cancellationService.cancelBooking("R999");
    }
}