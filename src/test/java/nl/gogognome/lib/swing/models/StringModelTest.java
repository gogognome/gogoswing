package nl.gogognome.lib.swing.models;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringModelTest {

    @Test
    public void testValidate() throws Exception {
        assertTrue(new StringModel().setString(null).validate());
        assertTrue(new StringModel().setString("").validate());
        assertTrue(new StringModel().setString("bla").validate());

        assertTrue(new StringModel().mustBeFilled(false).setString(null).validate());
        assertTrue(new StringModel().mustBeFilled(false).setString("").validate());
        assertTrue(new StringModel().mustBeFilled(false).setString("bla").validate());

        assertFalse(new StringModel().mustBeFilled(true).setString(null).validate());
        assertFalse(new StringModel().mustBeFilled(true).setString("").validate());
        assertTrue(new StringModel().mustBeFilled(true).setString("bla").validate());
    }
}