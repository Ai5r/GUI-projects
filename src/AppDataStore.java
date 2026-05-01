import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AppDataStore {
    private static final Path USERS_FILE = Paths.get("users.txt");
    private static final Path ADDED_USERS_FILE = Paths.get("addnewuser.txt");
    private static final Path ADDED_MEMBERS_FILE = Paths.get("addedmember.txt");
    private static final Path MEMBER_LIST_FILE = Paths.get("memberlist.txt");
    private static final Path PARTICIPANTS_FILE = Paths.get("participants.txt");

    private static final String USER_PREFIX = "USER|";
    private static final String LEGACY_NAME = "Name:";
    private static final String LEGACY_PHONE = "Phone:";
    private static final String LEGACY_EMAIL = "Email:";

    public void saveRegisteredUser(String name, String phone, String email, String password) throws IOException {
        ensureFileExists(USERS_FILE);
        String line = USER_PREFIX + sanitize(name) + "|" + sanitize(phone) + "|" + sanitize(email) + "|" + sanitize(password);
        appendLine(USERS_FILE, line);
    }

    public boolean authenticate(String name, String password) throws IOException {
        List<UserRecord> users = getAllUsers();
        for (UserRecord user : users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<UserRecord> getAllUsers() throws IOException {
        ensureFileExists(USERS_FILE);
        List<String> lines = Files.readAllLines(USERS_FILE, StandardCharsets.UTF_8);
        List<UserRecord> users = parseStructuredUsers(lines);
        if (!users.isEmpty()) {
            return users;
        }
        return parseLegacyUsers(lines);
    }

    public String findMemberByName(String query) throws IOException {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();
        if (normalizedQuery.isEmpty()) {
            return "";
        }

        for (UserRecord user : getAllUsers()) {
            if (user.getName().toLowerCase().contains(normalizedQuery)) {
                return user.getName() + "\nPhone: " + user.getPhone() + "\nEmail: " + user.getEmail();
            }
        }
        return "";
    }

    public void saveAddedUser(String name, String uniqueId) throws IOException {
        ensureFileExists(ADDED_USERS_FILE);
        appendLine(ADDED_USERS_FILE, "USER|" + sanitize(name) + "|" + sanitize(uniqueId));
    }

    public void saveAddedMember(String type, String name) throws IOException {
        ensureFileExists(ADDED_MEMBERS_FILE);
        appendLine(ADDED_MEMBERS_FILE, "MEMBER|" + sanitize(name) + "|" + sanitize(type));
    }

    public String readMemberList() throws IOException {
        ensureFileExists(MEMBER_LIST_FILE);
        return String.join("\n", Files.readAllLines(MEMBER_LIST_FILE, StandardCharsets.UTF_8));
    }

    public String readDashboardAllMembers() throws IOException {
        ensureFileExists(USERS_FILE);
        ensureFileExists(ADDED_USERS_FILE);
        ensureFileExists(ADDED_MEMBERS_FILE);
        ensureFileExists(PARTICIPANTS_FILE);

        StringBuilder out = new StringBuilder();
        out.append("=== users.txt ===\n");
        appendUsersSection(out, Files.readAllLines(USERS_FILE, StandardCharsets.UTF_8));
        out.append("\n=== addnewuser.txt ===\n");
        appendAddNewUserSection(out, Files.readAllLines(ADDED_USERS_FILE, StandardCharsets.UTF_8));
        out.append("\n=== addedmember.txt ===\n");
        appendAddedMemberSection(out, Files.readAllLines(ADDED_MEMBERS_FILE, StandardCharsets.UTF_8));
        out.append("\n=== participants.txt ===\n");
        appendParticipantsSection(out, Files.readAllLines(PARTICIPANTS_FILE, StandardCharsets.UTF_8));
        return out.toString().trim();
    }

    public void saveParticipant(String name, String phone) throws IOException {
        ensureFileExists(PARTICIPANTS_FILE);
        appendLine(PARTICIPANTS_FILE, "PARTICIPANT|" + sanitize(name) + "|" + sanitize(phone));
    }

    private List<UserRecord> parseStructuredUsers(List<String> lines) {
        List<UserRecord> users = new ArrayList<>();
        for (String line : lines) {
            if (!line.startsWith(USER_PREFIX)) {
                continue;
            }
            String[] parts = line.split("\\|", -1);
            if (parts.length < 5) {
                continue;
            }
            users.add(new UserRecord(parts[1], parts[2], parts[3], parts[4]));
        }
        return users;
    }

    private List<UserRecord> parseLegacyUsers(List<String> lines) {
        List<UserRecord> users = new ArrayList<>();
        String name = "";
        String phone = "";
        String email = "";
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith(LEGACY_NAME)) {
                if (!name.isEmpty() || !phone.isEmpty() || !email.isEmpty()) {
                    users.add(new UserRecord(name, phone, email, ""));
                }
                name = trimmed.substring(LEGACY_NAME.length()).trim();
                phone = "";
                email = "";
            } else if (trimmed.startsWith(LEGACY_PHONE)) {
                phone = trimmed.substring(LEGACY_PHONE.length()).trim();
            } else if (trimmed.startsWith(LEGACY_EMAIL)) {
                email = trimmed.substring(LEGACY_EMAIL.length()).trim();
            }
        }
        if (!name.isEmpty() || !phone.isEmpty() || !email.isEmpty()) {
            users.add(new UserRecord(name, phone, email, ""));
        }
        return users;
    }

    private void appendLine(Path path, String line) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            if (Files.size(path) > 0) {
                writer.newLine();
            }
            writer.write(line);
        }
    }

    private void ensureFileExists(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    private String sanitize(String value) {
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ").trim();
    }

    private void appendUsersSection(StringBuilder out, List<String> lines) {
        List<UserRecord> users = parseStructuredUsers(lines);
        if (users.isEmpty()) {
            users = parseLegacyUsers(lines);
        }
        if (users.isEmpty()) {
            out.append("(no data)\n");
            return;
        }
        int i = 1;
        for (UserRecord user : users) {
            out.append(i++)
                .append(". ")
                .append(user.getName())
                .append(" | ")
                .append(user.getPhone())
                .append(" | ")
                .append(user.getEmail())
                .append("\n");
        }
    }

    private void appendAddNewUserSection(StringBuilder out, List<String> lines) {
        int i = 1;
        boolean hasData = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("USER|")) {
                String[] parts = trimmed.split("\\|", -1);
                if (parts.length >= 3) {
                    hasData = true;
                    out.append(i++)
                        .append(". ")
                        .append(parts[1])
                        .append(" | ID: ")
                        .append(parts[2])
                        .append("\n");
                }
            } else if (!trimmed.isEmpty()) {
                hasData = true;
                out.append("- ").append(trimmed).append("\n");
            }
        }
        if (!hasData) {
            out.append("(no data)\n");
        }
    }

    private void appendAddedMemberSection(StringBuilder out, List<String> lines) {
        int i = 1;
        boolean hasData = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("MEMBER|")) {
                String[] parts = trimmed.split("\\|", -1);
                if (parts.length >= 3) {
                    hasData = true;
                    out.append(i++)
                        .append(". ")
                        .append(parts[1])
                        .append(" | Type: ")
                        .append(parts[2])
                        .append("\n");
                }
            } else if (!trimmed.isEmpty()) {
                hasData = true;
                out.append("- ").append(trimmed).append("\n");
            }
        }
        if (!hasData) {
            out.append("(no data)\n");
        }
    }

    private void appendParticipantsSection(StringBuilder out, List<String> lines) {
        int i = 1;
        boolean hasData = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("PARTICIPANT|")) {
                String[] parts = trimmed.split("\\|", -1);
                if (parts.length >= 3) {
                    hasData = true;
                    out.append(i++)
                        .append(". ")
                        .append(parts[1])
                        .append(" | Phone: ")
                        .append(parts[2])
                        .append("\n");
                }
            } else if (!trimmed.isEmpty()) {
                hasData = true;
                out.append("- ").append(trimmed).append("\n");
            }
        }
        if (!hasData) {
            out.append("(no data)\n");
        }
    }
}
