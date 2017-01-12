package com.artorias.util;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by devin on 1/12/17.
 */
public class PaginationTest {
    private static final int EXPECTED_ITEM_COUNT = 10;
    private static final int EXPECTED_PAGE_SIZE = 50;

    private Pagination page;

    @BeforeTest
    public void init() {
        page = new Pagination(EXPECTED_ITEM_COUNT);
    }

    @Test
    public void getItemCount_success() {
        Assert.assertEquals(page.getItemCount(), EXPECTED_ITEM_COUNT);
    }
}
