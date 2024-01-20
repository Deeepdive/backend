package deepdive.backend.divelog.domain.dto;

import lombok.Data;

@Data
public class DiveLogResult<T> {

    private int count;
    private T data;

    public DiveLogResult(int count, T data) {
        this.count = count;
        this.data = data;
    }
}
