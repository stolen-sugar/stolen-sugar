package com.stolensugar.web.model;

import java.util.List;
import java.util.Objects;

public class SpokenForm {
    private String id;
    private String repoId;
    private String command;
    private String fileName;
    private String configuration;
    private String appName;
    private String defaultName;
    private List<String> alternatives;

    public SpokenForm(String id, String repoId, String command, String fileName, String configuration, String appName, String defaultName, List<String> alternatives) {
        this.id = id;
        this.repoId = repoId;
        this.command = command;
        this.fileName = fileName;
        this.configuration = configuration;
        this.appName = appName;
        this.defaultName = defaultName;
        this.alternatives = alternatives;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }

    @Override
    public String toString() {
        return "SpokenForms{" +
                "id='" + id + '\'' +
                ", repoId='" + repoId + '\'' +
                ", command='" + command + '\'' +
                ", fileName='" + fileName + '\'' +
                ", configuration='" + configuration + '\'' +
                ", appName='" + appName + '\'' +
                ", defaultName='" + defaultName + '\'' +
                ", alternatives=" + alternatives +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpokenForm that = (SpokenForm) o;
        return getId().equals(that.getId()) && getRepoId().equals(that.getRepoId()) && getCommand().equals(that.getCommand()) && getFileName().equals(that.getFileName()) && getConfiguration().equals(that.getConfiguration()) && getAppName().equals(that.getAppName()) && getDefaultName().equals(that.getDefaultName()) && getAlternatives().equals(that.getAlternatives());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRepoId(), getCommand(), getFileName(), getConfiguration(), getAppName(), getDefaultName(), getAlternatives());
    }
}
