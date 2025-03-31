package tasks.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArrayTaskListTest {

    private ArrayTaskList taskList;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting tests...");
    }

    @BeforeEach
    void setup() {
        taskList = new ArrayTaskList();
    }

    @DisplayName("Test valid task with interval = 0 (Non-repetitive task)")
    @Test
    void testAddTaskWithValidInterval() {
        // Arrange
        Task task = new Task("Task", new Date(1733076000000L)); // 31.03.2025 10:00

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size());
    }


    @DisplayName("Test valid end date (startDate < endDate)")
    @Test
    void testAddTaskWithValidEndDate() {
        // Arrange
        Task task = new Task("Task", new Date(1733076000000L), new Date(1733079600000L), 0);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size());
    }

    @DisplayName("Test invalid end date (startDate == endDate) - Should throw exception")
    @Test
    void testAddTaskWithInvalidEndDate() {
        // Arrange & Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                new Task("Task", new Date(1733076000000L), new Date(1733076000000L), 5)
        );

        assertEquals("End should be after start", thrown.getMessage());
    }

    @DisplayName("Test valid interval (interval >= 0)")
    @Test
    void testAddTaskWithValidNonNegativeInterval() {
        // Arrange
        Task task = new Task("Task", new Date(1733076000000L), new Date(1733079600000L), 5);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size());
    }

    @DisplayName("Test invalid interval (interval < 0) - Should throw exception")
    @Test
    void testAddTaskWithNegativeInterval() {
        // Arrange & Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                new Task("Task", new Date(1733076000000L), new Date(1733079600000L), -1)
        );

        assertEquals("interval should be > 1", thrown.getMessage());
    }

    @DisplayName("Test valid date constraint (startDate < endDate)")
    @Test
    void testAddTaskWithValidDateConstraint() {
        // Arrange
        Task task = new Task("Task", new Date(1733076000000L), new Date(1733083200000L), 10);

        // Act
        taskList.add(task);

        // Assert
        assertEquals(1, taskList.size());
    }

    @DisplayName("Test invalid date constraint (startDate == endDate) - Should throw exception")
    @Test
    void testAddTaskWithInvalidDateConstraint() {
        // Arrange & Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                new Task("Task", new Date(1733076000000L), new Date(1733076000000L), 10)
        );

        assertEquals("End should be after start", thrown.getMessage());
    }

    @AfterEach
    void teardown() {
        taskList = null;
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("All tests completed.");
    }
}
