package de.slackspace.openkeepass.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A builder to create {@link Entry} objects.
 *
 */
public class EntryBuilder implements EntryContract {

    private UUID uuid;

    private History history;

    private String title;

    private String username;

    private String password;

    private String notes;

    private String url;

    private Entry originalEntry;

    private int iconId;

    private byte[] iconData;

    private UUID customIconUUID;

    private List<Property> customPropertyList = new ArrayList<Property>();

    /**
     * Creates a new builder with a random UUID.
     */
    public EntryBuilder() {
        this.uuid = UUID.randomUUID();
    }

    /**
     * Creates a new builder with the given UUID.
     *
     * @param uuid
     *            the UUID which should be used
     */
    public EntryBuilder(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Creates a new builder with the given title.
     *
     * @param title
     *            the title which should be used
     */
    public EntryBuilder(String title) {
        this();
        this.title = title;
    }

    /**
     * Initializes the builder with values from the given entry.
     *
     * @param entry
     *            the values from this will initialize the builder
     */
    public EntryBuilder(Entry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Parameter entry must not be null");
        }
        this.originalEntry = entry;
        this.uuid = entry.getUuid();
        this.title = entry.getTitle();
        this.history = entry.getHistory();
        this.username = entry.getUsername();
        this.password = entry.getPassword();
        this.notes = entry.getNotes();
        this.url = entry.getUrl();
        this.iconId = entry.getIconId();
        this.iconData = entry.getIconData();
        this.customIconUUID = entry.getCustomIconUuid();
        this.customPropertyList.addAll(entry.getCustomProperties());
    }

    public EntryBuilder title(String title) {
        this.title = title;
        return this;
    }

    public EntryBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public EntryBuilder username(String username) {
        this.username = username;
        return this;
    }

    public EntryBuilder password(String password) {
        this.password = password;
        return this;
    }

    public EntryBuilder notes(String notes) {
        this.notes = notes;
        return this;
    }

    public EntryBuilder history(History history) {
        this.history = history;
        return this;
    }

    public EntryBuilder url(String url) {
        this.url = url;
        return this;
    }

    public EntryBuilder iconData(byte[] iconData) {
        this.iconData = iconData;
        return this;
    }

    public EntryBuilder iconId(int iconId) {
        this.iconId = iconId;
        return this;
    }

    public EntryBuilder customIconUuid(UUID uuid) {
        this.customIconUUID = uuid;
        return this;
    }

    /**
     * Builds a new entry with the values from the builder.
     *
     * @return a new entry
     */
    public Entry build() {
        return new Entry(this);
    }

    /**
     * Builds a new entry and place the original one in the history list.
     *
     * @return the new entry.
     */
    public Entry buildWithHistory() {
        if (originalEntry == null) {
            throw new IllegalArgumentException("originalEntry is not set");
        }

        if (history == null) {
            history = new History();
        }

        history.getHistoricEntries().add(originalEntry);
        return build();
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public byte[] getIconData() {
        return iconData;
    }

    @Override
    public int getIconId() {
        return iconId;
    }

    @Override
    public UUID getCustomIconUUID() {
        return customIconUUID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public List<Property> getCustomPropertyList() {
        return customPropertyList;
    }

    @Override
    public History getHistory() {
        return history;
    }
}
