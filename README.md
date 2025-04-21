✨ Features
🔐 Secure login via email and password (using IMAP over SSL)

📥 Fetch a user-specified number of latest emails

🧠 Group emails based on Subject similarity

📊 Display groups in a clean table (Subject + Count)

🔄 Retry automatically if folder closes unexpectedly

💬 User-friendly messages and error handling

🖥️ JavaFX-based modern UI

🚀 Demo Screenshot

Login Page	Grouped Emails
(Replace with real screenshots later!)

📂 Project Structure
bash
Copy
Edit
src/main/java/
 ├── org.example/
 │    ├── EmailUI.java         # JavaFX Application (UI)
 │    ├── EmailReader.java     # Gmail/Outlook Email Reader (IMAP)
 │
 ├── com.example.emailgrouper/
 │    ├── EmailGrouper.java    # Logic to group emails based on subject
🛠️ How to Run
1. Clone the repo
bash
Copy
Edit
git clone git@github.com:Abhiroop97204/OutlookEmailProject.git
cd email-grouper
2. Import project into IntelliJ / Eclipse / VSCode
Open as Maven project (pom.xml is already configured).

3. Install dependencies
Java 17+

JavaFX SDK

Jakarta Mail (jakarta.mail:jakarta.mail-api)

Maven Compiler Plugin

You can update your pom.xml to include:

xml
Copy
Edit
<dependencies>
    <dependency>
        <groupId>jakarta.mail</groupId>
        <artifactId>jakarta.mail-api</artifactId>
        <version>2.1.2</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>21.0.1</version>
    </dependency>
</dependencies>
📋 How to Use
Enter your Gmail or Outlook email ID.

Enter your password or App Password (recommended for Gmail).

Enter the number of emails you want to fetch.

Click Fetch and Group Emails.

View results in a clean, sortable table.

✅ That's it!

⚠️ Important Notes
Gmail Users: You must enable IMAP in Gmail settings.

App Passwords: If you have 2FA enabled, use App Passwords instead of your real password.

How to create Gmail App Password

Security: Passwords are only used in-memory, no saving to disk.

📦 Build a runnable JAR
To package as an executable JAR:

bash
Copy
Edit
mvn clean package
Then run:

bash
Copy
Edit
java -jar target/email-grouper-1.0-SNAPSHOT.jar
👨‍💻 Contributing
Pull requests are welcome! Feel free to suggest improvements, report issues, or add new features.
Let's build it even better! 🚀

📄 License
This project is licensed under the MIT License.

❤️ Acknowledgements
Jakarta Mail

JavaFX

Special thanks to all testers and contributors!

✨ Star this repo if you found it useful! ⭐
🔥 Happy Email Grouping! 📬🚀
