package com.artorias.util;

/**
 * Created by devin on 11/13/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

public class Pagination {

    private int currentPage;
    private int firstPage;
    private int lastPage;
    private int pageSize;
    private int itemCount;

    public Pagination(int count) {
        this.itemCount = count;
        this.firstPage = 1;
        this.currentPage = 1;
        this.pageSize = 50;
    }

    // need to throw an error if this never gets set
    public void setItemCount(int count) {
        this.itemCount = count;
        this.lastPage = numberOfPages(count);
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public int last() {
        return numberOfPages();
    }

    public void setCurrent(String current) {
        int page = pageToInt(current);
        this.currentPage = page;

    }

    public void setCurrent(int current) {
        this.currentPage = current;
    }

    public int current() {
        return currentPage;
    }

    public int next() {
        if (last() <= currentPage) {
            return currentPage;
        }
        return currentPage + 1;
    }

    public int previous() {
        if (currentPage == 1) {
            return 1;
        }
        return currentPage - 1;
    }

    public void setPageSize(int size) {
        this.pageSize = size;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int offset() {
        return offsetFromPage(currentPage);
    }

    public int offsetFromPage(int page) {
        int offset = (page - 1) * this.pageSize;
        return offset;
    }

    public int numberOfPages(int itemCount) {
        int pages = (int) Math.ceil((double) itemCount / this.pageSize);
        if (pages == 0) {
            pages = 1;
        }
        this.lastPage = pages;
        return pages;
    }

    // need to make a lot of noise if this.itemCount is null
    public int numberOfPages() {
        int pages = numberOfPages(this.itemCount);
        return pages;
    }

    public List<Integer> pages() {
        ArrayList<Integer> pages = new ArrayList<>();
        for (int i = this.firstPage; i<=last(); i++) {
            pages.add(i);
        }
        return pages;
    }

    public int pageToInt(String current) {
        int page = 1;
        if (current != null) {
            page = Integer.parseInt(current);
        }
        return page;
    }
}
