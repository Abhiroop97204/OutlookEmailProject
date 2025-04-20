package org.example;

import jakarta.mail.Message;
import java.util.*;

public class EmailGrouper {
    public Map<String, List<Message>> groupEmails(List<Message> emails) {
        Map<String, List<Message>> groups = new HashMap<>();

        for (Message message : emails) {
            try {
                String subject = message.getSubject();
                String key = simplifySubject(subject);

                groups.computeIfAbsent(key, k -> new ArrayList<>()).add(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return groups;
    }

    private String simplifySubject(String subject) {
        if (subject == null) return "No Subject";
        subject = subject.toLowerCase();
        subject = subject.replaceAll("\\[.*?\\]", ""); // remove brackets
        subject = subject.replaceAll("re:|fw:", "");    // remove reply/forward
        subject = subject.replaceAll("[^a-zA-Z0-9 ]", ""); // remove special characters
        return subject.trim();
    }
}
