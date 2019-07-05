package ncmb;
class NCMBException extends Exception {
    public NCMBException() {
        
    }
    
    public NCMBException(String msg) {
        super(msg);
    }
    
    public NCMBException(Throwable cause) {
        super(cause);
    }
    
    public NCMBException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

class NCMBAuthException extends Exception {
    public NCMBAuthException() {
        
    }
    
    public NCMBAuthException(String msg) {
        super(msg);
    }
    
    public NCMBAuthException(Throwable cause) {
        super(cause);
    }
    
    public NCMBAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}