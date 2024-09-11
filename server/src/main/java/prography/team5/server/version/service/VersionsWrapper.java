package prography.team5.server.version.service;

import lombok.Getter;

@Getter
public class VersionsWrapper<T> {

    private T versions;

    public VersionsWrapper(final T list) {
        this.versions = list;
    }
}
