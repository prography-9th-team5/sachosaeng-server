package prography.team5.server.common;

import lombok.Getter;

@Getter
public class VotesWrapper<T> {

    private T votes;

    public VotesWrapper(final T list) {
        this.votes = list;
    }
}

