package com.artorias.util;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;

/**
 * Created by devin on 1/12/17.
 */
public class PaginationTest {
    private static final int EXPECTED_DEFAULT_ITEM_COUNT = 10;
    private static final int EXPECTED_DEFAULT_PAGE_SIZE = 50;
    private static final int EXPECTED_DEFAULT_NUMBER_OF_PAGES = 1;
    private static final int EXPECTED_DEFAULT_CURRENT_PAGE = 1;
    private static final int EXPECTED_DEFAULT_PAGE_OFFSET = 0;

    private Pagination page;

    @BeforeMethod
    public void init() {
        page = new Pagination(EXPECTED_DEFAULT_ITEM_COUNT);
    }

    @AfterMethod
    public void cleanup() {
        page = null;
        page = new Pagination(EXPECTED_DEFAULT_ITEM_COUNT);
    }

    @Test
    public void getItemCount_success() {
        Assert.assertEquals(page.getItemCount(), EXPECTED_DEFAULT_ITEM_COUNT);
    }

    @Test
    public void setItemCount_success() {
        page.setItemCount(20);
        Assert.assertEquals(page.getItemCount(), 20);
    }

    @Test
    public void getPageSize_success() {
        Assert.assertEquals(page.getPageSize(), EXPECTED_DEFAULT_PAGE_SIZE);
    }

    @Test
    public void setPageSize_success() {
        page.setPageSize(100);
        Assert.assertEquals(page.getPageSize(), 100);
    }

    @Test
    public void lastPage_success() {
        Assert.assertEquals(page.getPageSize(), EXPECTED_DEFAULT_PAGE_SIZE);
        Assert.assertEquals(page.getItemCount(), EXPECTED_DEFAULT_ITEM_COUNT);
        Assert.assertEquals(page.last(), EXPECTED_DEFAULT_NUMBER_OF_PAGES);

        // default page size: 50
        int newItemCount = 100;
        page.setItemCount(newItemCount);
        int newExpectedLastPage = 2;
        Assert.assertEquals(page.last(), newExpectedLastPage);
    }

    @Test
    public void currentPage_success() {
        Assert.assertEquals(page.current(), EXPECTED_DEFAULT_CURRENT_PAGE);

        int newCurrentPage = EXPECTED_DEFAULT_CURRENT_PAGE + 10;
        page.setCurrent(newCurrentPage);
        Assert.assertEquals(page.current(), newCurrentPage);

        String newCurrentPageString = "12";
        int newCurrentPageToInt = Integer.valueOf(newCurrentPageString);
        page.setCurrent(newCurrentPageString);
        Assert.assertEquals(page.current(), newCurrentPageToInt);
    }

    @Test
    public void pageOffset_success() {
        Assert.assertEquals(page.offset(), EXPECTED_DEFAULT_PAGE_OFFSET);

        int newCurrentPage = 2;
        page.setCurrent(newCurrentPage);
        int newPageOffset = EXPECTED_DEFAULT_PAGE_SIZE;
        Assert.assertEquals(page.offset(), newPageOffset);
    }

    @Test
    public void offsetFromPage_success() {
        for (int i=1; i<100; i++) {
            int expectedOffset = (i - 1) * EXPECTED_DEFAULT_PAGE_SIZE;
            Assert.assertEquals(page.offsetFromPage(i), expectedOffset);
        }
    }

    @Test
    public void numberOfPages_success() {
        int itemCount = EXPECTED_DEFAULT_ITEM_COUNT;
        // (int) Math.ceil((double) itemCount / pageSize);
        int expectedNumberOfPages = 1;
        Assert.assertEquals(page.numberOfPages(itemCount), expectedNumberOfPages);
        Assert.assertEquals(page.numberOfPages(), expectedNumberOfPages);
        // 10 + 100
        itemCount += 100;
        expectedNumberOfPages = 3;
        Assert.assertEquals(page.numberOfPages(itemCount), expectedNumberOfPages);
        page.setItemCount(itemCount);
        Assert.assertEquals(page.numberOfPages(), expectedNumberOfPages);

        page.setItemCount(1);
        Assert.assertEquals(page.numberOfPages(1), 1);
        Assert.assertEquals(page.numberOfPages(), 1);
    }

    @Test
    public void listPageNumbers_success() {
        ArrayList<Integer> pageNumbers = new ArrayList<Integer>() {{
            add(1);
        }};
        Assert.assertEquals(page.pages(), pageNumbers);

        pageNumbers.add(2);
        pageNumbers.add(3);
        page.setItemCount(EXPECTED_DEFAULT_ITEM_COUNT + 100);
        Assert.assertEquals(page.pages(), pageNumbers);
    }

    @Test
    public void pageToInt_success() {
        Assert.assertEquals(page.pageToInt("1"), 1);
    }

    @Test
    public void nextPage_success() {
        // only one page of data, so next page is 1
        Assert.assertEquals(page.next(), 1);

        // 4 total pages
        page.setItemCount(200);
        page.setCurrent(2);
        Assert.assertEquals(page.next(), 3);
    }

    @Test
    public void previousPage_success() {
        Assert.assertEquals(page.previous(), 1);

        page.setItemCount(200);
        page.setCurrent(4);
        Assert.assertEquals(page.previous(), 3);
    }

}
