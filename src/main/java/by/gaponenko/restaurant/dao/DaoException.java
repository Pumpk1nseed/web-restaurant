package by.gaponenko.restaurant.dao;

public class DaoException extends Exception{
    private static final long serialVersionUID = 2587845278908575546L;
    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

}
