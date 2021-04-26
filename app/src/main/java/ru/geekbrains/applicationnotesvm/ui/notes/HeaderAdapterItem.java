package ru.geekbrains.applicationnotesvm.ui.notes;

import java.util.Objects;

public class HeaderAdapterItem implements AdapterItem {
    private final String header;

    public HeaderAdapterItem(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderAdapterItem that = (HeaderAdapterItem) o;
        return Objects.equals(header, that.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }

    @Override
    public String uniqueTag() {
        return "HeaderAdapterItem_" + header;
    }
}
