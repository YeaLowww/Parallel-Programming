package org.example.task1;

class WordStats {
    private final int totalLength;
    private final int totalCount;

    public WordStats(int totalLength, int totalCount) {
        this.totalLength = totalLength;
        this.totalCount = totalCount;
    }

    public WordStats merge(WordStats other) {
        return new WordStats(this.totalLength + other.totalLength, this.totalCount + other.totalCount);
    }

    public int getTotalLength() {
        return totalLength;
    }

    public int getTotalCount() {
        return totalCount;
    }
}