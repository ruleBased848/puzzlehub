package com.rulebased848.puzzlehub.domain;

public class SearchRequest {
    private int itemNum;
    private int page;

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}