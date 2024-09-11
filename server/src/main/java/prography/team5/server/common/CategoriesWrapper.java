package prography.team5.server.common;

import lombok.Getter;

@Getter
public class CategoriesWrapper<T> {

    private T categories;

    public CategoriesWrapper(final T list) {
        this.categories = list;
    }
}
