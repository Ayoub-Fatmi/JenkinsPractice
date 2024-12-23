package com.example.jenkinstp;

import com.example.jenkinstp.controller.JenkinstpController;
import com.example.jenkinstp.service.JenkinstpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class JenkinsControllerTest {
    @Mock
    private JenkinstpService jenkinstpService;
    @InjectMocks
    private JenkinstpController jenkinstpController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testAdd() {
        when(jenkinstpService.add(1, 2)).thenReturn(3.0);
        assertEquals(3.0, jenkinstpController.add(1, 2));
    }
    @Test
    public void testSubtract() {
        when(jenkinstpService.subtract(5, 3)).thenReturn(2.0);
        assertEquals(2.0, jenkinstpController.subtract(5, 3));
    }
    @Test
    public void testMultiply() {
        when(jenkinstpService.multiply(3, 4)).thenReturn(12.0);
        assertEquals(12.0, jenkinstpController.multiply(3, 4));
    }
    @Test
    public void testDivide() {
        when(jenkinstpService.divide(10, 2)).thenReturn(5.0);
        assertEquals(5.0, jenkinstpController.divide(10, 2));
    }
    @Test
    public void testDivideByZero() {
        when(jenkinstpService.divide(10, 0)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> jenkinstpController.divide(10, 0));
    }
}
