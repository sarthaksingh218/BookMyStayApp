import java.util.*;

class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public synchronized boolean allocateRoom(String roomType) {
        Integer available = inventory.get(roomType);
        if (available == null || available <= 0) {
            return false;
        }

        inventory.put(roomType, available - 1);
        return true;
    }

    public synchronized void displayInventory() {
        System.out.println("Final Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
    }

    public synchronized BookingRequest getNextRequest() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {

    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            synchronized (bookingQueue) {
                request = bookingQueue.getNextRequest();
            }

            if (request == null) {
                break;
            }

            boolean success = inventory.allocateRoom(request.getRoomType());

            if (success) {
                System.out.println("Booking confirmed for " + request.getGuestName() +
                        " (" + request.getRoomType() + " room)");
            } else {
                System.out.println("Booking failed for " + request.getGuestName() +
                        " - No " + request.getRoomType() + " rooms available");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new BookingRequest("Amit", "Single"));
        bookingQueue.addRequest(new BookingRequest("Neha", "Single"));
        bookingQueue.addRequest(new BookingRequest("Rahul", "Single"));
        bookingQueue.addRequest(new BookingRequest("Priya", "Double"));
        bookingQueue.addRequest(new BookingRequest("Karan", "Suite"));
        bookingQueue.addRequest(new BookingRequest("Riya", "Suite"));

        BookingProcessor t1 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor(bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println();
        inventory.displayInventory();
    }
}