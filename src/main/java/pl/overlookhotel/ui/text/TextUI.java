package pl.overlookhotel.ui.text;

import pl.overlookhotel.domain.ObjectPool;
import pl.overlookhotel.exceptions.OnlyNumberException;
import pl.overlookhotel.exceptions.PersistenceToFileException;
import pl.overlookhotel.exceptions.WrongOptionException;
import pl.overlookhotel.domain.guest.Guest;
import pl.overlookhotel.domain.guest.GuestService;
import pl.overlookhotel.domain.reservation.Reservation;
import pl.overlookhotel.domain.reservation.ReservationService;
import pl.overlookhotel.domain.room.Room;
import pl.overlookhotel.domain.room.RoomService;
import pl.overlookhotel.util.Properties;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {

    private final GuestService guestService = ObjectPool.getGuestService();
    private final RoomService roomService = ObjectPool.getRoomService();
    private final ReservationService reservationService = ObjectPool.getReservationService();

    private void readNewGuestData(Scanner scanner) {
        System.out.println("Wybrano opcję dodania nowego gościa");
        try {
            System.out.println("Podaj imię: ");
            String firstName = scanner.next();
            System.out.println("Podaj nazwisko: ");
            String lastName = scanner.next();
            System.out.println("Podaj wiek: ");
            int age = scanner.nextInt();
            System.out.println("Podaj płeć (1. Mężczyzna, 2. Kobieta");
            int genderOption = scanner.nextInt();

            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = false;

            if (genderOption == 1) {
                isMale = true;
            }

            Guest newGuest = guestService.createNewGuest(firstName, lastName, age, isMale);
            System.out.println("Dodano nowego gościa: " + newGuest.getInfo());

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("User only numbers when choosing age or gender");
        }
    }

    private void readNewRoomData(Scanner scanner) {
        System.out.println("Wybrano opcję dodania nowego pokoju.");
        try {
            System.out.println("Numer:");
            int number = scanner.nextInt();
            int[] bedTypes = chooseBedType(scanner);
            Room newRoom = roomService.createNewRoom(number, bedTypes);
            System.out.println("Dodano nowy pokój: " + newRoom.getInfo());

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when creating new room");
        }
    }

    private int[] chooseBedType(Scanner scanner) {
        System.out.println("Ile łóżek w pokoju?:  ");
        int bedNumber = scanner.nextInt();

        int[] bedTypes = new int[bedNumber];

        for (int i = 0; i < bedNumber; i++) {

            System.out.println("Typy łóżek: ");
            System.out.println("1. Pojedyńcze");
            System.out.println("2. Podwójne");
            System.out.println("3. Królewskie");

            int bedTypeOption = scanner.nextInt();

            bedTypes[i] = bedTypeOption;

        }

        return bedTypes;

    }

    public void showSystemInfo() {
        System.out.println("Witaj w systemie rezerwacji dla hotelu " + Properties.HOTEL_NAME);
        System.out.println("Aktualna wersja systemu: " + Properties.SYSTEM_VERSION);
        System.out.println("Wersja developerska " + Properties.IS_DEVELOPER_VERSION);

        System.out.println("\n ==============================\n");
    }

    public void showMainMenu() {

        System.out.println("Trwa ładowanie danych...");
        this.guestService.readAll();
        this.roomService.readAll();
        this.reservationService.readAll();
        System.out.println("Dane załadowane");

        Scanner scanner = new Scanner(System.in);

        try {
            performAction(scanner);
        } catch (WrongOptionException | OnlyNumberException | PersistenceToFileException e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Komunikat błędu: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Komunikat błędu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void performAction(Scanner scanner) {

        int option = -1;

        while (option != 0) {
            option = getActionFromUser(scanner);

            if (option == 1) {
                readNewGuestData(scanner);
                this.guestService.saveAll();
            } else if (option == 2) {
                readNewRoomData(scanner);
                this.roomService.saveAll();
            } else if (option == 3) {
                System.out.println("Aktualni goście:");
                showAllGuests();
            } else if (option == 4) {
                System.out.println("Wszystkie dodane pokoje:");
                showAllAddedRooms();
            } else if (option == 5) {
                System.out.println("usuwanie gościa");
                removeGuest(scanner);
            } else if (option == 6) {
                System.out.println("edycja danych gościa");
                editGuest(scanner);
            } else if (option == 7) {
                System.out.println("usuwanie pokoju");
                removeRoom(scanner);
            } else if (option == 8) {
                System.out.println("edycja pokoju");
                editRoom(scanner);
            } else if (option == 9) {
                System.out.println("rezerwacja");
                createReservation(scanner);
                this.reservationService.saveAll();
            } else if (option == 0) {
                System.out.println("Wychodzę z aplikacji");
            } else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }
    }

    private void createReservation(Scanner scanner) {
        System.out.println("Od kiedy (DD.MM.YYYY): ");
        String fromAsString = scanner.next();
        LocalDate from = LocalDate.parse(fromAsString, Properties.DATE_FORMATTER);
        System.out.println("Do kiedy (DD.MM.YYYY): ");
        String toAsString = scanner.next();
        LocalDate to = LocalDate.parse(toAsString, Properties.DATE_FORMATTER);
        System.out.println("Podaj ID pokoju");
        int roomId = scanner.nextInt();
        System.out.println("Podaj ID gościa");
        int guestId = scanner.nextInt();

        try {

            Reservation res = this.reservationService.createNewReservation(from, to, roomId, guestId);
            if (res != null) {
                System.out.println("Udało się stworzyć rezerwację");
            }
        } catch (IllegalArgumentException exception) {
            System.out.println("Data zakończenia rezerwacji nie może być wcześniejsza niż data rozpoczęcia rezerwacji");
        }
    }

    private void editRoom(Scanner scanner) {
        System.out.println("Podaj ID pokoju, który chcesz edytować");
        try {
            int id = scanner.nextInt();
            System.out.println("Numer:");
            int number = scanner.nextInt();
            int[] bedTypes = chooseBedType(scanner);
            roomService.editRoom(id, number, bedTypes);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void removeRoom(Scanner scanner) {
        System.out.println("Podaj ID pokoju do usunięcia");
        try {
            int id = scanner.nextInt();
            this.roomService.removeRoom(id);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void editGuest(Scanner scanner) {
        System.out.println("Podaj ID gościa, którego dane chcesz edytować");
        try {
            int id = scanner.nextInt();

            System.out.println("Podaj imię: ");
            String firstName = scanner.next();
            System.out.println("Podaj nazwisko: ");
            String lastName = scanner.next();
            System.out.println("Podaj wiek: ");
            int age = scanner.nextInt();
            System.out.println("Podaj płeć (1. Mężczyzna, 2. Kobieta");
            int genderOption = scanner.nextInt();

            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = false;

            if (genderOption == 1) {
                isMale = true;
            }

            guestService.editGuest(id, firstName, lastName, age, isMale);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void removeGuest(Scanner scanner) {
        System.out.println("Podaj ID gościa do usunięcia");
        try {
            int id = scanner.nextInt();
            this.guestService.removeGuest(id);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void showAllAddedRooms() {
        List<Room> rooms = this.roomService.getAllRooms();

        for (Room room : rooms) {
            System.out.println(room.getInfo());
        }
    }

    private void showAllGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        for (Guest guest : guests) {
            System.out.println(guest.getInfo());
        }
    }

    private int getActionFromUser(Scanner scanner) {
        System.out.println("1. Dodaj nowego gościa");
        System.out.println("2. Dodaj nowy pokój");
        System.out.println("3. Pokaż listę wszystkich gości");
        System.out.println("4. Pokaż listę wsystkich dodanych pokoi");
        System.out.println("5. Usuń gościa");
        System.out.println("6. Edytuj dane gościa");
        System.out.println("7. Usuń pokój");
        System.out.println("8. Edytuj pokój");
        System.out.println("9. Stwórz rezerwację");
        System.out.println("");
        System.out.println("0. Wyjście z aplikacji");
        System.out.println("");
        System.out.println("Wybierz opcję: ");

        int option;

        try {
            option = scanner.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers in main menu");
        }
        return option;
    }

}
