package de.slackspace.openkeepass.domain;

import java.util.Calendar;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.slackspace.openkeepass.xml.UUIDDeserializer;

/**
 * Represents the metadata of the KeePass database like database name, custom
 * icons or how much history entries will be preserved.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    @JacksonXmlProperty(localName = "Generator")
    private String generator;

    @JacksonXmlProperty(localName = "DatabaseName")
    private String databaseName;

    @JacksonXmlProperty(localName = "DatabaseDescription")
    private String databaseDescription;

    @JacksonXmlProperty(localName = "DatabaseNameChanged")
    private Calendar databaseNameChanged;

    @JacksonXmlProperty(localName = "DatabaseDescriptionChanged")
    private Calendar databaseDescriptionChanged;

    @JacksonXmlProperty(localName = "MaintenanceHistoryDays")
    private int maintenanceHistoryDays;

    @JacksonXmlProperty(localName = "RecycleBinUUID")
    @JsonSerialize(using=UUIDSerializer.class)
    @JsonDeserialize(using=UUIDDeserializer.class)
    private UUID recycleBinUuid;

    @JacksonXmlProperty(localName = "RecycleBinChanged")
    private Calendar recycleBinChanged;

    @JacksonXmlProperty(localName = "RecycleBinEnabled")
    private Boolean recycleBinEnabled;

    @JacksonXmlProperty(localName = "HistoryMaxItems")
    private long historyMaxItems;

    @JacksonXmlProperty(localName = "HistoryMaxSize")
    private long historyMaxSize;

    @JacksonXmlProperty(localName = "CustomIcons")
    private CustomIcons customIcons;

    Meta() {
    }

    public Meta(MetaBuilder metaBuilder) {
        this.databaseDescription = metaBuilder.databaseDescription;
        this.databaseDescriptionChanged = metaBuilder.databaseDescriptionChanged;
        this.databaseName = metaBuilder.databaseName;
        this.databaseNameChanged = metaBuilder.databaseNameChanged;
        this.generator = metaBuilder.generator;
        this.historyMaxItems = metaBuilder.historyMaxItems;
        this.historyMaxSize = metaBuilder.historyMaxSize;
        this.maintenanceHistoryDays = metaBuilder.maintenanceHistoryDays;
        this.recycleBinChanged = metaBuilder.recycleBinChanged;
        this.recycleBinEnabled = metaBuilder.recycleBinEnabled;
        this.recycleBinUuid = metaBuilder.recycleBinUuid;
        this.customIcons = metaBuilder.customIcons;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getDatabaseDescription() {
        return databaseDescription;
    }

    public String getGenerator() {
        return generator;
    }

    public Calendar getDatabaseNameChanged() {
        return databaseNameChanged;
    }

    public Calendar getDatabaseDescriptionChanged() {
        return databaseDescriptionChanged;
    }

    public int getMaintenanceHistoryDays() {
        return maintenanceHistoryDays;
    }

    public UUID getRecycleBinUuid() {
        return recycleBinUuid;
    }

    public Calendar getRecycleBinChanged() {
        return recycleBinChanged;
    }

    public long getHistoryMaxItems() {
        return historyMaxItems;
    }

    public long getHistoryMaxSize() {
        return historyMaxSize;
    }

    public boolean getRecycleBinEnabled() {
        return recycleBinEnabled;
    }

    public CustomIcons getCustomIcons() {
        return customIcons;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customIcons == null) ? 0 : customIcons.hashCode());
        result = prime * result + ((databaseDescription == null) ? 0 : databaseDescription.hashCode());
        result = prime * result + ((databaseDescriptionChanged == null) ? 0 : databaseDescriptionChanged.hashCode());
        result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
        result = prime * result + ((databaseNameChanged == null) ? 0 : databaseNameChanged.hashCode());
        result = prime * result + ((generator == null) ? 0 : generator.hashCode());
        result = prime * result + (int) (historyMaxItems ^ (historyMaxItems >>> 32));
        result = prime * result + (int) (historyMaxSize ^ (historyMaxSize >>> 32));
        result = prime * result + maintenanceHistoryDays;
        result = prime * result + ((recycleBinChanged == null) ? 0 : recycleBinChanged.hashCode());
        result = prime * result + ((recycleBinEnabled == null) ? 0 : recycleBinEnabled.hashCode());
        result = prime * result + ((recycleBinUuid == null) ? 0 : recycleBinUuid.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Meta))
            return false;
        Meta other = (Meta) obj;
        if (customIcons == null) {
            if (other.customIcons != null)
                return false;
        } else if (!customIcons.equals(other.customIcons))
            return false;
        if (databaseDescription == null) {
            if (other.databaseDescription != null)
                return false;
        } else if (!databaseDescription.equals(other.databaseDescription))
            return false;
        if (databaseDescriptionChanged == null) {
            if (other.databaseDescriptionChanged != null)
                return false;
        } else if (!databaseDescriptionChanged.equals(other.databaseDescriptionChanged))
            return false;
        if (databaseName == null) {
            if (other.databaseName != null)
                return false;
        } else if (!databaseName.equals(other.databaseName))
            return false;
        if (databaseNameChanged == null) {
            if (other.databaseNameChanged != null)
                return false;
        } else if (!databaseNameChanged.equals(other.databaseNameChanged))
            return false;
        if (generator == null) {
            if (other.generator != null)
                return false;
        } else if (!generator.equals(other.generator))
            return false;
        if (historyMaxItems != other.historyMaxItems)
            return false;
        if (historyMaxSize != other.historyMaxSize)
            return false;
        if (maintenanceHistoryDays != other.maintenanceHistoryDays)
            return false;
        if (recycleBinChanged == null) {
            if (other.recycleBinChanged != null)
                return false;
        } else if (!recycleBinChanged.equals(other.recycleBinChanged))
            return false;
        if (recycleBinEnabled == null) {
            if (other.recycleBinEnabled != null)
                return false;
        } else if (!recycleBinEnabled.equals(other.recycleBinEnabled))
            return false;
        if (recycleBinUuid == null) {
            if (other.recycleBinUuid != null)
                return false;
        } else if (!recycleBinUuid.equals(other.recycleBinUuid))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Meta [generator=" + generator + ", databaseName=" + databaseName + ", databaseDescription=" + databaseDescription + "]";
    }
}
