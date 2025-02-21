import java.util.ArrayList;

public class LibraryCollection {
    public static ArrayList<Book> books = new ArrayList<>();
    public static ArrayList<Member> members = new ArrayList<>();
    public static Book getBook(String index) {
        return books.get(Integer.parseInt(index) - 1);
    }
    public static Member getMember(String index) {
        return members.get(Integer.parseInt(index) - 1);
    }
}
