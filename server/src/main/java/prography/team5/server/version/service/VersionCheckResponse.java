package prography.team5.server.version.service;

public record VersionCheckResponse(boolean isLatest, boolean forceUpdateRequired) {

}
