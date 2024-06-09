package UI;

import entities.Loan;
import entities.Member;
import entities.Vehicle;
import exception.InvalidLoanException;
import exception.InvalidMemberException;
import exception.InvalidVehicleException;
import repository.file.MemberRepositoryFile;
import repository.file.VehicleRepositoryFile;
import repository.jdbc.LoanRepositoryDB;
import repository.jdbc.MemberRepositoryDB;
import repository.jdbc.VehicleRepositoryDB;
import repository.jdbc.databaseConnection;
import repository.memory.LoanRepositoryMem;
import repository.memory.MemberRepositoryMem;
import repository.memory.VehicleRepositoryMem;
import services.memberServices;

import java.io.Console;
import java.util.Scanner;
import java.util.stream.Stream;

public class vmsUI {
    private static memberServices memberServices;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean continueRunning = true;
    static String username;
    static String password;
    static databaseConnection dbConnect;

    public static void start() {
        System.out.println("=======================================================");
        System.out.println("Please select storage type[File, jdbc, Memory]");

        while (true) {
            String typeStorage = scanner.nextLine();
            switch (typeStorage.toLowerCase()) {
                case "file":
                    memberServices = new memberServices(new MemberRepositoryFile(), new LoanRepositoryMem(), new VehicleRepositoryFile());
                    break;
                case "jdbc":
                    System.out.println("Please login to Database Server");
                    Console cons = System.console();
                    if (cons != null) {
                        System.out.println("username: ");
                        username = cons.readLine();
                        System.out.println("password: ");
                        password = new String(cons.readPassword());
                    } else {
                        System.out.println("[Public]");
                        System.out.println("username: ");
                        username = scanner.nextLine();
                        System.out.println("password: ");
                        password = scanner.nextLine();
                    }

                    dbConnect = new databaseConnection(username, password);
                    menuDB();
                    break;
                case "memory":
                    memberServices = new memberServices(new MemberRepositoryMem(), new LoanRepositoryMem(), new VehicleRepositoryMem());
                    break;
                default:
                    System.out.println("Invalid option, Please select [File, jdbc, Memory]!");
                    continue;
            }
            break;
        }


    }
    private static void menuDB() {
        boolean logout = false;
        String menu = """
                    -- VehicleManage-DB --
                    1. Connect to Database
                    2. Exit""";
        while (!logout) {
            System.out.println(menu);
            System.out.println("Please select options");
            int option;
            try {
                String input = scanner.nextLine().trim();
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Menu. Please try again!");
                continue;
            }
            switch (option) {
                case 1:
                    connectDB();
                    memberServices = new memberServices(new MemberRepositoryDB(), new LoanRepositoryDB(), new VehicleRepositoryDB());
                    selectPermission();
                    break;
                case 2:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again!");

            }
        }
    }

    private static void connectDB() {
        try{
            dbConnect.getConnection();
            System.out.println("Connected!");
        }catch(Exception e){
            System.out.println("Connect failed!");
            System.out.println("Please tyr again!");
        }
    }




    private static void run() {
        while (continueRunning) {
            start();
            selectPermission();
        }
    }

    private static void selectPermission() {

        while (continueRunning == true) {
            System.out.println("Vehicle Management System");
            System.out.println("1.Admin menu");
            System.out.println("2.Customer Menu");
            System.out.println("3.Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void adminMenu() {
        while (true) {

            System.out.println("-------------------");

            System.out.println("Member Management");
            System.out.println("1. Add Member");
            System.out.println("2. Delete Member");
            System.out.println("3. Update Member");
            System.out.println("4. View Member");
            System.out.println("5. View All Members");

            System.out.println("Vehicle Management");
            System.out.println("6. Add Vehicle");
            System.out.println("8. Delete Vehicle");
            System.out.println("7. Update Vehicle");
            System.out.println("9. View Vehicle");
            System.out.println("10. View All Vehicle");

            System.out.println("Loan Management");
            System.out.println("11. Add Loan");
            System.out.println("12. Delete Loan");
            System.out.println("13. Update Loan");
            System.out.println("14. View Loan");
            System.out.println("15. View All Loans");

            System.out.println("16. Back to Main Menu");
            System.out.println("-------------------");

            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    deleteMember();
                    break;
                case 3:
                    updateMember();
                    break;
                case 4:
                    viewMember();
                    break;
                case 5:
                    viewAllMembers();
                    break;
                case 6:
                    addVehicle();
                    break;
                case 7:
                    updateVehicle();
                    break;
                case 8:
                    deleteVehicle();
                    break;
                case 9:
                    viewVehicle();
                    break;
                case 10:
                    viewAllVehicle();
                    break;
                case 11:
                    addLoan();
                    break;
                case 12:
                    deleteLoan();
                    break;
                case 13:
                    updateLoan();
                    break;
                case 14:
                    viewLoan();
                    break;
                case 15:
                    viewAllLoans();
                    break;
                case 16:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void customerMenu() {
        while (true) {


            System.out.println("-------------------");

            System.out.println("Member");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. View Member");

            System.out.println("Loaning");
            System.out.println("4. View All Vehicle");
            System.out.println("5. Add Loan");
            System.out.println("6. Update Loan");
            System.out.println("7. View Loan");

            System.out.println("8. Back to Main Menu");
            System.out.println("-------------------");

            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    updateMember();
                    break;
                case 3:
                    viewMember();
                    break;
                case 4:
                    viewAllVehicle();
                    break;
                case 5:
                    addLoan();
                    break;
                case 6:
                    updateLoan();
                    break;
                case 7:
                    viewLoan();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addMember() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Member ID: ");
                String memberID = scanner.nextLine();
                System.out.print("Enter Member Name: ");
                String memberName = scanner.nextLine();
                System.out.print("Enter Member Tel: ");
                String memberTel = scanner.nextLine();
                if (memberID.trim().isEmpty() || memberName.trim().isEmpty() || memberTel.trim().isEmpty()) {
                    throw new InvalidMemberException("Member information cannot be Empty");
                }
                valid = true;

                Member member = memberServices.addMember(memberID, memberName, memberTel);
                System.out.println("Member added: " + memberName + " successfully!");


            } catch (InvalidMemberException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }


    private static void deleteMember() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Member ID to delete: ");
                String memberID = scanner.nextLine();
                if (memberID.trim().isEmpty()) {
                    throw new InvalidMemberException("MemberID cannot be empty");
                }
                Member member = memberServices.deleteMember(memberID);
                if (member != null) {
                    System.out.println("Member deleted!!: " + "ID:" + member.getMemberID() + ", " + "Name:" + member.getMemberName() + ", " + "Tel:" + member.getMemberTel());
                } else {
                    System.out.println("Member not found.");
                }
                valid = true;
            } catch (InvalidMemberException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }


    private static void updateMember() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Member ID to update: ");
                String memberID = scanner.nextLine();
                System.out.print("Enter new Member Name: ");
                String memberName = scanner.nextLine();
                System.out.print("Enter new Member Tel: ");
                String memberTel = scanner.nextLine();
                if (memberID.trim().isEmpty() || memberName.trim().isEmpty() || memberTel.trim().isEmpty()) {
                    throw new InvalidMemberException("Member information cannot be empty");
                }
                Member member = memberServices.updateMember(memberID, memberName, memberTel);
                if (member != null) {
                    System.out.println("Member ID: " + member.getMemberID());
                    System.out.println("Member Name: " + member.getMemberName());
                    System.out.println("Member Tel: " + member.getMemberTel());
                    System.out.println("Update Successfully!");
                } else {
                    System.out.println("Member not found.");
                }
                valid = true;
            } catch (InvalidMemberException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void viewMember() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Member ID to view: ");
                String memberID = scanner.nextLine();
                if (memberID.trim().isEmpty()) {
                    throw new InvalidMemberException("Member information cannot be empty");
                }
                Member member = memberServices.findMember(memberID);
                if (member != null) {
                    System.out.println("Member ID: " + member.getMemberID());
                    System.out.println("Member Name: " + member.getMemberName());
                    System.out.println("Member Tel: " + member.getMemberTel());
                } else {
                    System.out.println("Member not found.");
                }
                valid = true;
            } catch (InvalidMemberException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }

    }


    private static void viewAllMembers() {
        Stream<Member> members = memberServices.getAllMember();
        members.map(member -> member.toString().replaceAll("^Member\\{memberID=(.*?), memberName=(.*?), memberTel=(.*?)}$", "ID:$1, Name:$2, Tel:$3"))
                .forEach(System.out::println);
    }


    private static void addVehicle() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Vehicle ID: ");
                String vehicleID = scanner.nextLine();
                System.out.print("Enter Vehicle Name: ");
                String vehicleName = scanner.nextLine();
                System.out.print("Enter Vehicle Type: ");
                String vehicleType = scanner.nextLine();
                if (vehicleID.trim().isEmpty()) {
                    throw new InvalidVehicleException("Vehicle information cannot be empty");
                }
                Vehicle vehicle = memberServices.addVehicle(vehicleID, vehicleName, vehicleType);
                System.out.println("Vehicle added: " + vehicleName + " successfully!");
                valid = true;
            } catch (InvalidVehicleException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void deleteVehicle() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Vehicle ID to delete: ");
                String vehicleID = scanner.nextLine();
                if (vehicleID.trim().isEmpty()) {
                    throw new InvalidVehicleException("VehicleID cannot be empty");
                }
                Vehicle vehicle = memberServices.deleteVehicle(vehicleID);
                if (vehicle != null) {
                    System.out.println("Vehicle deleted: " + "ID:" + vehicle.getVehicleId() + ", " + "Name:" + vehicle.getVehicleName() + ", " + "Tel:" + vehicle.getVehicleType());
                } else {
                    System.out.println("Vehicle not found.");
                }
                valid = true;
            } catch (InvalidVehicleException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void updateVehicle() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Vehicle ID to update: ");
                String vehicleID = scanner.nextLine();
                System.out.print("Enter new Vehicle Name: ");
                String vehicleName = scanner.nextLine();
                System.out.print("Enter new Vehicle Type: ");
                String vehicleType = scanner.nextLine();
                if (vehicleID.trim().isEmpty() || vehicleName.trim().isEmpty() || vehicleType.trim().isEmpty()) {
                    throw new InvalidVehicleException("VehicleID cannot be empty");
                }
                Vehicle vehicle = memberServices.updateVehicle(vehicleID, vehicleName, vehicleType);
                if (vehicle != null) {
                    System.out.println("Vehicle ID: " + vehicle.getVehicleId());
                    System.out.println("Vehicle Name: " + vehicle.getVehicleName());
                    System.out.println("Vehicle Tel: " + vehicle.getVehicleType());
                    System.out.println("Update Successfully!");
                } else {
                    System.out.println("Vehicle not found.");
                }
                valid = true;
            } catch (InvalidVehicleException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void viewVehicle() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Vehicle ID to view: ");
                String vehicleID = scanner.nextLine();
                if (vehicleID.trim().isEmpty()) {
                    throw new InvalidVehicleException("VehicleID cannot be empty");
                }
                Vehicle vehicle = memberServices.findByVehicleID(vehicleID);
                if (vehicle != null) {
                    System.out.println("Vehicle ID: " + vehicle.getVehicleId());
                    System.out.println("Vehicle Name: " + vehicle.getVehicleName());
                    System.out.println("Vehicle Type: " + vehicle.getVehicleType());
                } else {
                    System.out.println("Vehicle not found.");
                }
                valid = true;
            } catch (InvalidVehicleException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void viewAllVehicle() {
        Stream<Vehicle> vehicles = memberServices.getAllVehicle();
        vehicles.map(vehicle -> "ID:" + vehicle.getVehicleId() + ", Name:" + vehicle.getVehicleName() + ", Type:" + vehicle.getVehicleType())
                .forEach(System.out::println);
    }


    private static void addLoan() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Loan ID: ");
                String loanID = scanner.nextLine();
                System.out.print("Enter Member ID: ");
                String memberID = scanner.nextLine();
                System.out.print("Enter Vehicle ID: ");
                String vehicleID = scanner.nextLine();
                if (vehicleID.trim().isEmpty() || memberID.trim().isEmpty() || loanID.trim().isEmpty()) {
                    throw new InvalidLoanException("Information cannot be empty");
                }
                Loan loan = memberServices.addLoan(loanID, memberID, vehicleID);
                System.out.println("Loan added: " + loanID + " successfully!");
                valid = true;
            } catch (InvalidLoanException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void deleteLoan() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Loan ID to delete: ");
                String loanID = scanner.nextLine();
                if (loanID.trim().isEmpty()) {
                    throw new InvalidLoanException("LoanID cannot be empty");
                }
                Loan loan = memberServices.deleteLoan(loanID);
                if (loan != null) {
                    System.out.println("Loan deleted: " + "loan_ID:" + loan.getLoanID() + ", " + "member_ID:" + loan.getMemberID() + ", " + "vehicle_ID:" + loan.getVehicleID());
                } else {
                    System.out.println("Loan not found.");
                }
                valid = true;
            } catch (InvalidLoanException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void updateLoan() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Loan ID to update: ");
                String loanID = scanner.nextLine();
                System.out.print("Enter new Member ID: ");
                String memberID = scanner.nextLine();
                System.out.print("Enter new Vehicle ID: ");
                String vehicleID = scanner.nextLine();
                if (vehicleID.trim().isEmpty() || memberID.trim().isEmpty() || loanID.trim().isEmpty()) {
                    throw new InvalidLoanException("Information cannot be empty");
                }
                Loan loan = memberServices.updateLoan(loanID, memberID, vehicleID);
                if (loan != null) {
                    System.out.println("Loan_ID: " + loan.getLoanID());
                    System.out.println("Loan_MemberID: " + loan.getMemberID());
                    System.out.println("Loan_VehicleID: " + loan.getVehicleID());
                    System.out.println("Update Successfully!");
                } else {
                    System.out.println("Loan not found.");
                }
                valid = true;
            } catch (InvalidLoanException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }

    private static void viewLoan() {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter Loan ID to view: ");
                String loanID = scanner.nextLine();
                if (loanID.trim().isEmpty()) {
                    throw new InvalidLoanException("Information cannot be empty");
                }
                Loan loan = memberServices.findLoan(loanID);
                Vehicle vehicle = memberServices.findByVehicleID(loan.getVehicleID());
                if (loan != null) {
                    System.out.println("Loan_ID: " + loan.getLoanID());
                    System.out.println("Loan_MemberID: " + loan.getMemberID());
                    System.out.println("Loan_Vehicle: " + "Name: " + vehicle.getVehicleName() + ", Type: " + vehicle.getVehicleType());
                } else {
                    System.out.println("Loan not found.");
                }
                valid = true;
            } catch (InvalidLoanException e) {
                System.out.println("Invalid information. Try again!.");
            }
        }
    }


    private static void viewAllLoans() {
        Stream<Loan> loans = memberServices.getAllLoan();
        loans.map(loan -> "Loan_ID:" + loan.getLoanID() + ", Loan_MemberID:" + loan.getMemberID() + ", Loan_VehicleID:" + loan.getVehicleID())
                .forEach(System.out::println);
    }
}
