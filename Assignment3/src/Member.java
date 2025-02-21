import java.rmi.AlreadyBoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Member {
    public static int index = 0;
    private int timeLimit;
    private int memberIndex;
    private int borrowedBook = 0;
    private int fee;

    /**
     *
     * @param book
     * @param date
     * @throws IllegalAccessException
     * @throws AlreadyBoundException
     */
    public abstract void borrowBook(Book book, LocalDate date) throws IllegalAccessException, AlreadyBoundException;
    public abstract void readInLibrary(Book book,LocalDate date) throws IllegalAccessException, AlreadyBoundException;
    public int getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getMemberIndex() {
        return memberIndex;
    }

    public void setMemberIndex(int memberIndex) {
        this.memberIndex = memberIndex;
    }

    //calculate how many books the member has borrowed
    public void calculateBorrowedBook(boolean borrow) {
        if (borrow) {
           borrowedBook++;
        } else {
            borrowedBook--;
        }
    }
    //sets everything when borrow a book
    public void borrowProcess(Book book, LocalDate date) {
        calculateBorrowedBook(true);
        book.setBorrowDate(date);
        book.setTakenBy(memberIndex);
        book.setDeadlineDate(date.plusDays(timeLimit));
    }

    public void returnBook(Book book,LocalDate date) throws IllegalAccessException {
        book.setReturnDate(date);
        book.setCanExtend(true);
        calculateBorrowedBook(false);
        book.setTakenBy(0);
        try {
            fee = (int) ChronoUnit.DAYS.between(book.getDeadlineDate(), book.getReturnDate());
            if (fee < 0) {
                fee = 0;
            } if (fee > 0) {
                throw new IllegalAccessException();
            }
        } catch (NullPointerException e) {
            fee = 0;
        }
        book.setReturnDate(null);
        book.setDeadlineDate(null);
        book.setBorrowDate(null);
    }
}
