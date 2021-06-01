package pl.overlookhotel.ui.text;

import pl.overlookhotel.exceptions.OnlyNumberException;
import pl.overlookhotel.exceptions.WrongOptionException;
import pl.overlookhotel.guest.Guest;
import pl.overlookhotel.guest.GuestService;
import pl.overlookhotel.room.Room;
import pl.overlookhotel.room.RoomService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {

    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();

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
            System.out.println(newRoom.getInfo());

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

    public void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {
        System.out.println("Witaj w systemie rezerwacji dla hotelu " + hotelName);
        System.out.println("Aktualna wersja systemu " + systemVersion);
        System.out.println("Wersja developerska " + isDeveloperVersion);

        System.out.println("\n ==============================\n");
    }

    public void showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        try {
            performAction(scanner);
        } catch (WrongOptionException | OnlyNumberException e) {
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
            } else if (option == 2) {
                readNewRoomData(scanner);
            } else if (option == 3) {
                System.out.println("Aktualni goście:");
                showAllGuests();
            } else if (option == 0) {
                System.out.println("Wychodzę z aplikacji");
            } else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }
    }

    private void showAllGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        for ( Guest guest : guests){
            System.out.println(guest.getInfo());
        }
    }


    private int getActionFromUser(Scanner scanner) {
        System.out.println("1. Dodaj nowego gościa");
        System.out.println("2. Dodaj nowy pokój");
        System.out.println("3. Pokaż listę wszystkich gości");
        System.out.println("0. Wyjście z aplikacji");
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
