package prography.team5.server.version.service;

public record VersionCheckResponse(String version, String platform, boolean isLatest, boolean forceUpdateRequired) {

}
