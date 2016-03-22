package de.slackspace.openkeepass.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import de.slackspace.openkeepass.xml.UUIDDeserializer;

/**
 * A Group represents a structure that consists of entries and subgroups.
 *
 * @see Entry
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group implements KeePassFileElement {

    @JacksonXmlProperty(localName = "UUID")
    @JsonSerialize(using=UUIDSerializer.class)
    @JsonDeserialize(using=UUIDDeserializer.class)
    private UUID uuid;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JacksonXmlProperty(localName = "IconID")
    private int iconId = 49;

    private transient byte[] iconData;

    @JacksonXmlProperty(localName = "CustomIconUUID")
    @JsonSerialize(using=UUIDSerializer.class)
    @JsonDeserialize(using=UUIDDeserializer.class)
    private UUID customIconUUID;

    @JacksonXmlProperty(localName = "Times")
    private Times times;

    @JacksonXmlProperty(localName = "IsExpanded")
    private Boolean isExpanded;

    @JacksonXmlProperty(localName = "Entry")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Entry> entries = new ArrayList<Entry>();

    @JacksonXmlProperty(localName = "Group")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Group> groups = new ArrayList<Group>();

    Group() {
        uuid = UUID.randomUUID();
    }

    public Group(GroupBuilder builder) {
        entries = builder.entries;
        groups = builder.groups;
        iconId = builder.iconId;
        isExpanded = builder.isExpanded;
        name = builder.name;
        times = builder.times;
        uuid = builder.uuid;
        iconData = builder.iconData;
        customIconUUID = builder.customIconUuid;
    }

    /**
     * Retrieves the Uuid of this group.
     *
     * @return the Uuid of this group
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Retrieves the name of the group.
     *
     * @return the name of the group
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves all subgroups of this group.
     *
     * @return all subgroups of this group
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Retrieves all entries of this group.
     *
     * @return all entries of this group
     * @see Entry
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     * Retrieves the entry with the given title.
     *
     * @param title
     *            the title of the entry which should be retrieved
     * @return an entry with matching title
     */
    public Entry getEntryByTitle(String title) {
        for (Entry entry : entries) {
            if (entry.getTitle().equalsIgnoreCase(title)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Retrieves the icon of this group.
     *
     * @return the icon of this group
     */
    public int getIconId() {
        return iconId;
    }

    /**
     * Returns the custom icon of this group.
     *
     * @return the UUID of the custom icon or null
     */
    @JsonIgnore
    public UUID getCustomIconUuid() {
        return customIconUUID;
    }

    /**
     * Returns the raw data of either the custom icon (if specified) or the
     * chosen stock icon.
     *
     * @return the raw icon data if available or null otherwise
     */
    @JsonIgnore
    public byte[] getIconData() {
        return iconData;
    }

    public Times getTimes() {
        return times;
    }

    /**
     * Retrieves the last expanded status of the group.
     *
     * @return true if the group was expanded the last time it was opened in
     *         keepass
     */
    @JsonIgnore
    public boolean isExpanded() {
        if (isExpanded == null) {
            return false;
        }
        return isExpanded.booleanValue();
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customIconUUID == null) ? 0 : customIconUUID.hashCode());
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + iconId;
        result = prime * result + ((isExpanded == null) ? 0 : isExpanded.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((times == null) ? 0 : times.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Group))
            return false;
        Group other = (Group) obj;
        if (customIconUUID == null) {
            if (other.customIconUUID != null)
                return false;
        } else if (!customIconUUID.equals(other.customIconUUID))
            return false;
        if (entries == null) {
            if (other.entries != null)
                return false;
        } else if (!entries.equals(other.entries))
            return false;
        if (groups == null) {
            if (other.groups != null)
                return false;
        } else if (!groups.equals(other.groups))
            return false;
        if (iconId != other.iconId)
            return false;
        if (isExpanded == null) {
            if (other.isExpanded != null)
                return false;
        } else if (!isExpanded.equals(other.isExpanded))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (times == null) {
            if (other.times != null)
                return false;
        } else if (!times.equals(other.times))
            return false;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }

}
