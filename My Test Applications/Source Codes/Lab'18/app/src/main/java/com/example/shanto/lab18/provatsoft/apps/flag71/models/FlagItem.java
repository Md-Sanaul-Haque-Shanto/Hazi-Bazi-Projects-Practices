package com.example.shanto.lab18.provatsoft.apps.flag71.models;

public class FlagItem {
    private int displayableFlagId;
    private String flagName;
    private int frameFlagId;

    public FlagItem(String flagName, int displayableFlagId, int frameFlagId) {
        this.flagName = flagName;
        this.displayableFlagId = displayableFlagId;
        this.frameFlagId = frameFlagId;
    }

    public String getFlagName() {
        return this.flagName;
    }

    public int getDisplayableFlagId() {
        return this.displayableFlagId;
    }

    public int getFrameFlagId() {
        return this.frameFlagId;
    }
}
