package com.mycompany.quickchat;

import Message.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuickChat {

    private static final Scanner input = new Scanner(System.in);

    private static final List<Message> messages = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Welcome to QuickChat.");

        System.out.print("Enter your username: ");
        String username = input.nextLine();

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        if (username.trim().isEmpty()
                || password.trim().isEmpty()) {

            System.out.println(
                    "Please enter a valid username and password."
            );

            return;
        }

        System.out.println("Login successful.");

        System.out.print("How many messages would you like to send? ");

        int numberOfMessages;

        try {
            numberOfMessages = Integer.parseInt(input.nextLine());

            if (numberOfMessages < 0) {
                System.out.println(
                        "Please enter zero or a positive number."
                );
                return;
            }

        } catch (NumberFormatException e) {

            System.out.println("Please enter a valid number.");
            return;
        }

        int messageNumber = 0;

        while (true) {

            System.out.println("\nWelcome to QuickChat.");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");

            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {

                case "1":

                    if (messageNumber >= numberOfMessages) {

                        System.out.println(
                                "You have reached your message limit."
                        );

                        break;
                    }

                    sendNewMessage(messageNumber + 1);

                    messageNumber++;

                    break;

                case "2":

                    System.out.println("Coming Soon.");

                    break;

                case "3":

                    System.out.println("Thank you for using QuickChat.");

                    System.out.println(
                            "Total messages sent: "
                            + Message.returnTotalMessages()
                    );

                    input.close();

                    return;

                default:

                    System.out.println(
                            "Invalid option. Please select 1, 2, or 3."
                    );
            }
        }
    }

    private static void sendNewMessage(int messageNumber) {

        System.out.print("Enter recipient cellphone number: ");
        String recipient = input.nextLine();

        System.out.print("Enter your message: ");
        String messageText = input.nextLine();

        if (messageText.trim().isEmpty()) {

            System.out.println("Please enter a message.");
            return;
        }

        if (messageText.length() > 250) {

            System.out.println(
                    "Please enter a message of less than 250 characters."
            );

            return;
        }

        Message message = new Message(
                messageNumber,
                recipient,
                messageText
        );

        if (!message.checkMessageID()) {

            System.out.println("The message ID is invalid.");
            return;
        }

        String recipientResult = message.checkRecipientCell();

        if (!recipientResult.equals(
                "Cell phone number successfully captured.")) {

            System.out.println(recipientResult);
            return;
        }

        System.out.println("Message ready to send.");

        System.out.println("\nChoose what to do with this message:");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message");

        System.out.print("Choose an option: ");

        String choice = input.nextLine();

        if (choice.equals("2")) {

            System.out.println("Press 0 to delete the message.");

            String deleteChoice = input.nextLine();

            if (deleteChoice.equals("0")) {

                System.out.println("Message deleted.");
            } else {

                System.out.println("Message was not deleted.");
            }

            return;
        }

        String result = message.sendMessage(choice);

        System.out.println(result);

        if (choice.equals("1") || choice.equals("3")) {

            messages.add(message);

            System.out.println("\nMessage Details:");

            System.out.println(message.printMessages());
        }
    }
}