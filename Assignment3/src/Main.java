import java.io.*;
import java.rmi.AlreadyBoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * add book to library collection
     * @param command --> addBook typeOfBook
     */
    public static void addBook(String[] command,BufferedWriter writer) throws IOException {
        Book.index++;
        //Handwritten Books
        if (command[1].equals("H")) {
            HandwrittenBook book = new HandwrittenBook();
            LibraryCollection.books.add(book);
            writer.write(String.format("Created new book: Handwritten [id: " + book.getBookIndex() + "]"));
            writer.newLine();
        }
        // Printed Books
        else if (command[1].equals("P")) {
            Book book = new PrintedBook();
            LibraryCollection.books.add(book);
            writer.write(String.format("Created new book: Printed [id: " + book.getBookIndex() + "]"));
            writer.newLine();
        }
    }
    /**
     * add members to library collection
     * @param command --> addMember, typeOfMember
     */
    public static void addMember(String[] command,BufferedWriter writer) throws IOException {
        Member.index++;
        //Student member
        if (command[1].equals("S")) {
            Student member = new Student();
            LibraryCollection.members.add(member);
            writer.write(String.format("Created new member: Student [id: %d]",member.getMemberIndex()));
            writer.newLine();
        }
        //Academic member
        else if (command[1].equals("A")) {
            Academic member = new Academic();
            LibraryCollection.members.add(member);
            writer.write(String.format("Created new member: Academic [id: %d]",member.getMemberIndex()));
            writer.newLine();
        }

    }

    /**
     * borrowing book from the library
     * @param command --> borrowBook, Id_Of_Book, Id_Of_Library_Member, date
     */
    public static void borrowBook(String[] command,BufferedWriter writer) throws IOException {
        try {
            Book book = LibraryCollection.getBook(command[1]);
            Member member = LibraryCollection.getMember(command[2]);
            LocalDate date = stringToDate(command[3]);
            member.borrowBook(book,date);
            writer.write(String.format("The book [%d] was borrowed by member [%d] at %s",book.getBookIndex(),member.getMemberIndex(),date));
            writer.newLine();
        }
        //when borrowing limit is exceeded
        catch (IndexOutOfBoundsException e) {
            writer.write("You have exceeded the borrowing limit!");
            writer.newLine();
        }
        //other exceptions
        catch (Exception e) {
            writer.write("You cannot borrow this book!");
            writer.newLine();
        }

    }

    /**
     * returning book to library
     * @param command --> returnBook, Id_Of_Book, Id_Of_Library_Member, date
     */
    public static void returnBook(String[] command,BufferedWriter writer) throws IOException {
        Book book = LibraryCollection.getBook(command[1]);
        Member member = LibraryCollection.getMember(command[2]);
        LocalDate date = stringToDate(command[3]);
        //when borrow the book before the deadline
        try {
            member.returnBook(book,date);
            writer.write(String.format("The book [%d] was returned by member [%d] at %s Fee: 0", book.getBookIndex(), member.getMemberIndex(),date));
            writer.newLine();
        }
        //when borrow the book after the deadline
        catch (IllegalAccessException e) {
            writer.write("You must pay a penalty!");
            writer.newLine();
            writer.write(String.format("The book [%d] was returned by member [%d] at %s Fee: %d", book.getBookIndex(), member.getMemberIndex(),date,member.getFee()));
            writer.newLine();
        }
    }

    /**
     * extend the deadline (7 for students 14 for academics)
     * @param command --> extendBook, Id_Of_Book, Id_Of_Library_Member, currentDate
     */
    public static void extendBook(String[] command,BufferedWriter writer) throws IOException {
        Book book = LibraryCollection.getBook(command[1]);
        Member member = LibraryCollection.getMember(command[2]);
        LocalDate date = stringToDate(command[3]);
        if (book.isCanExtend()) {
            int timeLimit = member.getTimeLimit();
            book.setDeadlineDate(date.plusDays(timeLimit));
            book.setCanExtend(false);
            writer.write(String.format("The deadline of the book [%d] was extended by member [%d] at %s",book.getBookIndex(),member.getMemberIndex(),date));
            writer.newLine();
            writer.write(String.format("New deadline of book [%d] is %s",book.getBookIndex(),book.getDeadlineDate()));
            writer.newLine();
        }
        //
        else {
            writer.write("You cannot extend the deadline!");
            writer.newLine();
        }
    }

    /**
     * read the book in library it sets the both borrow date and return date to the same date
     * @param command --> readInLibrary Id_Of_Book Id_Of_Library_Member currentDate
     */
    public static void readInLibrary(String[] command,BufferedWriter writer) throws IOException {
        try {
            Book book = LibraryCollection.getBook(command[1]);
            Member member = LibraryCollection.getMember(command[2]);
            LocalDate date = stringToDate(command[3]);
            member.readInLibrary(book, date);
            writer.write(String.format("The book [%d] was read in library by member [%d] at %s",book.getBookIndex(),member.getMemberIndex(),date));
            writer.newLine();
        } catch (IllegalAccessException e) {
            writer.write("Students can not read handwritten books!");
            writer.newLine();
        } catch (AlreadyBoundException e) {
            writer.write("You can not read this book!");
            writer.newLine();
        }

    }

    /**
     * printing the details of history of the library
     * @param command --> getTheHistory
     */
    public static void getTheHistory(String[] command,BufferedWriter writer) throws IOException {
        writer.write("History of library:");
        writer.newLine();
        writer.newLine();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Academic> academics = new ArrayList<>();
        ArrayList<HandwrittenBook> handwrittenBooks = new ArrayList<>();
        ArrayList<PrintedBook> printedBooks = new ArrayList<>();
        for(Member member:LibraryCollection.members) {
            if (member instanceof Student) {
                students.add((Student) member);
            } else if (member instanceof  Academic) {
                academics.add((Academic) member);
            }
        }
        for(Book book:LibraryCollection.books) {
            if (book instanceof HandwrittenBook) {
                handwrittenBooks.add((HandwrittenBook) book);
            } else if (book instanceof PrintedBook) {
                printedBooks.add((PrintedBook) book);
            }
        }
        writer.write(String.format("Number of students: %s",students.size()));
        writer.newLine();
        for (Student student:students) {
            writer.write(String.format("Student [id: %s]",student.getMemberIndex()));
            writer.newLine();
        }
        writer.newLine();
        writer.write(String.format("Number of academics: %s",academics.size()));
        writer.newLine();
        for (Academic academic:academics) {
            writer.write(String.format("Academics [id: %s]",academic.getMemberIndex()));
            writer.newLine();
        }
        writer.newLine();
        writer.write(String.format("Number of Printed Books: %s",printedBooks.size()));
        writer.newLine();
        for (PrintedBook printedBook:printedBooks) {
            writer.write(String.format("Printed books [id: %s]",printedBook.getBookIndex()));
            writer.newLine();
        }
        writer.newLine();
        writer.write(String.format("Number of Handwritten Books: %s",printedBooks.size()));
        writer.newLine();
        for (HandwrittenBook handwrittenBook:handwrittenBooks) {
            writer.write(String.format("Handwritten books [id: %s]",handwrittenBook.getBookIndex()));
            writer.newLine();
        }
        writer.newLine();
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        ArrayList<Book> readInLibraryBooks = new ArrayList<>();
        for (Book book:LibraryCollection.books) {
            if (book.getTakenBy() != 0) {
                if (book.getReturnDate() != null) {
                    readInLibraryBooks.add(book);
                } else {
                    borrowedBooks.add(book);
                }
            }
        }
        writer.write(String.format("Number of borrowed books: %s",borrowedBooks.size()));
        writer.newLine();
        for (Book book : borrowedBooks) {
            writer.write(String.format("The book [%d] was borrowed by member [%d] at %s",book.getBookIndex(),book.getTakenBy(),book.getBorrowDate()));
            writer.newLine();
        }
        writer.newLine();
        writer.write(String.format("Number of books read in library: %s",readInLibraryBooks.size()));
        writer.newLine();
        for (Book book : readInLibraryBooks) {
            writer.write(String.format("The book [%d] was read in library by member [%d] at %s",book.getBookIndex(),book.getTakenBy(),book.getBorrowDate()));
            writer.newLine();
        }


    }

    /**
     * Turning dates from String to the localdate format
     * @param date in string format
     * @return the date but in localdate format
     */
    public static LocalDate stringToDate(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date , format);
    }
    public static void main(String[] args){
        try {
            File outputFile = new File(args[1]);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile,false));
            File inputFile = new File(args[0]);
            FileReader reader = new FileReader(inputFile);
            Scanner scanner = new Scanner(reader);
            //reading lines
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] command = line.split("\t");
                //switch chooses the command
                switch (command[0]) {
                    case "addBook":
                        addBook(command,writer);
                        break;
                    case "addMember":
                        addMember(command,writer);
                        break;
                    case "borrowBook":
                        borrowBook(command,writer);
                        break;
                    case "returnBook":
                        returnBook(command,writer);
                        break;
                    case "extendBook":
                        extendBook(command,writer);
                        break;
                    case "readInLibrary":
                        readInLibrary(command,writer);
                        break;
                    case "getTheHistory":
                        getTheHistory(command,writer);
                        break;
                }
            }
            writer.close();
        } catch (FileNotFoundException e ) {
            System.out.println("ERROR");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}