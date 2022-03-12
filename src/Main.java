import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean quit = false;
        Scanner sc = new Scanner(System.in);
        while (!quit){
            System.out.println("-------------------------------");
            System.out.println("1. Encrypt message" +
                    "\n2. Decrypt message" +
                    "\n3. Quit");
            System.out.print("Input: ");
            int choice = sc.nextInt();
            System.out.println("-------------------------------");
            if (choice == 1){
                System.out.print("Enter the number of rows: ");
                int numRows = sc.nextInt();
                System.out.print("Enter the number of columns: ");
                int numColumns = sc.nextInt();
                System.out.print("Enter the row offset: ");
                int rowOffset = sc.nextInt();
                System.out.print("Enter the column offset: ");
                int columnOffset = sc.nextInt();
                Encryptor e = new Encryptor(numRows, numColumns, rowOffset, columnOffset);
                System.out.print("Enter the message that you want to encrypt: ");
                sc.nextLine();
                String message = sc.nextLine();
                System.out.println("Encrypted message: " + e.encryptMessage(message));
            } else if (choice == 2){
                System.out.print("Enter the number of rows: ");
                int numRows = sc.nextInt();
                System.out.print("Enter the number of columns: ");
                int numColumns = sc.nextInt();
                System.out.print("Enter the row offset: ");
                int rowOffset = sc.nextInt();
                System.out.print("Enter the column offset: ");
                int columnOffset = sc.nextInt();
                Encryptor e = new Encryptor(numRows, numColumns, rowOffset, columnOffset);
                System.out.print("Enter the message that you want to decrypt: ");
                sc.nextLine();
                String message = sc.nextLine();
                System.out.println("Decrypted message: " + e.decryptMessage(message));
            } else if (choice == 3){
                quit = true;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }
}

