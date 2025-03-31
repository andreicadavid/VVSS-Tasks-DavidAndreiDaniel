package tasks.services;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TaskDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Folosire @TestInstance pentru ciclul de viață
class TaskIOTest {
    private ObservableList<Task> tasks;

    @BeforeEach
    void setUp() {
        ArrayTaskList taskList = new ArrayTaskList();
        TasksService service = new TasksService(taskList);
        tasks = service.getObservableList();
    }

    @AfterEach
    void tearDown() {
        tasks.clear();
    }

    @Test
    @DisplayName("Add Valid Task ECP") //  @DisplayName
    void testECP_1() {
        // Arrange
        assertEquals(tasks.size(), 0);
        TaskDTO taskDTO = new TaskDTO("Doing things", new Date(2024, 4, 5, 10, 0), null, null, true);

        // Act
        TaskIO.insertTask(taskDTO, tasks);

        // Assert
        assertEquals(tasks.size(), 1);
    }

    @Test
    @Tag("InvalidTasks") // @Tag pentru categorii de teste
    @DisplayName("Add Invalid Task ECP")
    void testECP_2() {
        // Arrange
        assertEquals(tasks.size(), 0);
        TaskDTO taskDTO = new TaskDTO("Doing things", new Date(2024, 4, 5, 10, 0), new Date(2024, 4, 5, 12, 0), 0, true);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TaskIO.insertTask(taskDTO, tasks));
        assertEquals("interval should be > 1", exception.getMessage());
        assertEquals(tasks.size(), 0);
    }

    @RepeatedTest(3) // @RepeatedTest pentru a rula de 3 ori testul
    @DisplayName("Add Valid Task BVA")
    void testBVA_1() {
        // Arrange
        assertEquals(tasks.size(), 0);
        TaskDTO taskDTO = new TaskDTO("Doing things", new Date(0), new Date(1), 3, true);

        // Act
        TaskIO.insertTask(taskDTO, tasks);

        // Assert
        assertEquals(tasks.size(), 1);
    }

    @Test
    @Timeout(2) //  @Timeout pentru a limita timpul de execuție
    @DisplayName("Add Invalid Task BVA")
    void testBVA_2() {
        // Arrange
        assertEquals(tasks.size(), 0);
        TaskDTO taskDTO = new TaskDTO("Doing things", new Date(-1), new Date(1), 3, true);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> TaskIO.insertTask(taskDTO, tasks));
        assertEquals("Time cannot be negative", exception.getMessage());
        assertEquals(tasks.size(), 0);
    }

    @Test
    @Disabled("This test is temporarily disabled") // @Disabled pentru a ignora testul
    void testDisabled() {
        fail("This test should be ignored!");
    }
}
