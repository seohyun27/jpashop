package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    // 생성 -> 메소드 재정의(오버라이드)에서 불러와서 사용

    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
