import java.time.LocalDate;

public class Book {
    public static int index = 0;
    private int bookIndex;
    private LocalDate borrowDate = null;
    private LocalDate returnDate = null;
    private LocalDate deadlineDate = null;
    private int takenBy;    //0 when there is no borrower
    private boolean canExtend = true;


    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }


    public void setReturnDate(LocalDate returnTime) {
        this.returnDate = returnTime;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public int getBookIndex() {
        return bookIndex;
    }

    public int getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(int takenBy) {
        this.takenBy = takenBy;
    }

    public boolean isCanExtend() {
        return canExtend;
    }

    public void setCanExtend(boolean canExtend) {
        this.canExtend = canExtend;
    }

    public void setBookIndex(int bookIndex) {
        this.bookIndex = bookIndex;
    }
}
