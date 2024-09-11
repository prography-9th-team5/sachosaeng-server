package prography.team5.server.common;

import lombok.Getter;

@Getter
public class InformationWrapper<T> {

    private T information;

    public InformationWrapper(final T list) {
        this.information = list;
    }
}
