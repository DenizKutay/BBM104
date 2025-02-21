import java.rmi.AlreadyBoundException;
import java.time.LocalDate;

public class Student extends Member {

    public Student() {
        setMemberIndex(Member.index);
        setTimeLimit(7);
    }

    /**
     *
     * @param book which is borrowed by the member
     * @param date borrowing date
     * @throws AlreadyBoundException when the book has taken
     * @throws IndexOutOfBoundsException when the member has reached his borrow book limit
     * @throws IllegalAccessException when the book is handwritten book
     */
    @Override
    public void borrowBook(Book book, LocalDate date) throws IllegalAccessException, AlreadyBoundException {
        //checks if the book is handwritten
        if (book instanceof HandwrittenBook) {
            throw new IllegalAccessException();
        }
        //that if statement change the books owner to no one (that's for read in library situation)
        if (book.getReturnDate() != null){
                if (date.isAfter(book.getReturnDate())) {
                book.setTakenBy(0);
            }
        }
        //if the book has taken
        if (book.getTakenBy() != 0) {
            throw new AlreadyBoundException();
        }
        //check if the member has reached his borrow book limit
        if (getBorrowedBook() < 2) {
            //SUCCESS
            borrowProcess(book, date);
            book.setDeadlineDate(date.plusDays(getTimeLimit()));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * IMPORTANT: read in library sets the borrow date and return date to the same date since it will
     * be read in library and will be returned at the same day
     * @param book which is borrowed by the member
     * @param date borrowing date
     * @throws AlreadyBoundException when the book has already taken
     */
    @Override
    public void readInLibrary(Book book, LocalDate date) throws IllegalAccessException, AlreadyBoundException {
        if (book instanceof HandwrittenBook) {
            throw new IllegalAccessException();
        } else if (book.getTakenBy() != 0) {
            throw new AlreadyBoundException();
        } else {
            book.setTakenBy(getMemberIndex());
            book.setBorrowDate(date);
            book.setReturnDate(date);
        }
    }
}


