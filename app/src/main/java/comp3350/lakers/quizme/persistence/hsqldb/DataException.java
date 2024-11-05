package comp3350.lakers.quizme.persistence.hsqldb;

public class DataException extends RuntimeException{
    public DataException(final Exception e) {
        super(e);
    }
}
