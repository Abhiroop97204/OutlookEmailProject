package org.example;

import jakarta.mail.*;
import java.util.*;

import jakarta.mail.*;
import java.util.*;

public class EmailReader {
    private Store store;
    private Session session;
    private int fetchCount = 100; // default 100 emails

    public EmailReader(String username, String password, int fetchCount) {
        this.fetchCount = fetchCount;
        connect("imap.gmail.com", username, password);
    }

    public boolean connect(String host, String username, String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", host);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.ssl.enable", "true");

            session = Session.getInstance(properties);
            store = session.getStore("imaps");
            store.connect(host, username, password);
            System.out.println("Connected to Gmail successfully!");
            return true;
        } catch (AuthenticationFailedException e) {
            System.out.println("Authentication failed: " + e.getMessage());
        } catch (MessagingException e) {
            System.out.println("Messaging error: " + e.getMessage());
        }
        return false;
    }

    public List<Message> fetchEmails() {
        return fetchEmailsInternal(false);
    }

    private List<Message> fetchEmailsInternal(boolean isRetry) {
        List<Message> emails = new ArrayList<>();
        if (store == null || !store.isConnected()) {
            System.out.println("Store is not connected. Please connect first.");
            return emails;
        }

        Folder inbox = null;
        try {
            inbox = store.getFolder("INBOX");

            if (!inbox.isOpen()) {
                inbox.open(Folder.READ_ONLY);
            }

            int messageCount = inbox.getMessageCount();
            System.out.println("Total Messages in Inbox: " + messageCount);

            int end = messageCount;
            int start = Math.max(1, messageCount - (fetchCount - 1)); // Dynamic based on fetchCount

            System.out.println("Fetching messages from " + start + " to " + end);

            Message[] messages = inbox.getMessages(start, end);

            // preload subject, sender inside memory
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            inbox.fetch(messages, fp);

            emails.addAll(Arrays.asList(messages));

        } catch (FolderClosedException e) {
            System.out.println("⚠FolderClosedException occurred: " + e.getMessage());
            if (!isRetry) {
                try {
                    System.out.println("⏳ Waiting 6 seconds before retrying...");
                    Thread.sleep(6000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Retrying now...");
                return fetchEmailsInternal(true);
            } else {
                System.out.println("Retry also failed due to FolderClosedException.");
            }
        } catch (MessagingException e) {
            System.out.println("Error reading emails: " + e.getMessage());
        } finally {
            try {
                if (inbox != null && inbox.isOpen()) {
                    inbox.close(false);
                }
            } catch (MessagingException e) {
                System.out.println("Error closing inbox: " + e.getMessage());
            }
        }
        return emails;
    }

    public void disconnect() {
        if (store != null && store.isConnected()) {
            try {
                store.close();
                System.out.println("Disconnected from Gmail successfully!");
            } catch (MessagingException e) {
                System.out.println("Error closing store: " + e.getMessage());
            }
        }
    }
}