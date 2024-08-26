package co.zelez.core.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void Given_an_positive_number_Then_return_true() {
        //Arrange
        String inputNumber = "123";

        //Assert
        assertTrue(Util.isNumeric(inputNumber));
    }

    @Test
    void Given_an_negative_number_Then_return_true() {
        //Arrange
        String inputNumber = "-123";

        //Assert
        assertTrue(Util.isNumeric(inputNumber));
    }

    @Test
    void Given_an_not_number_Then_return_false() {
        //Arrange
        String inputNonNumber = "soda";

        //Assert
        assertFalse(Util.isNumeric(inputNonNumber));
    }
}