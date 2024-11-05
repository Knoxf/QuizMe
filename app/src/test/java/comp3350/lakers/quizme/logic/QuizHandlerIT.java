package comp3350.lakers.quizme.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import comp3350.lakers.quizme.objects.quizzes.LongAnswerQuiz;
import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.persistence.IQuiz;
import comp3350.lakers.quizme.persistence.hsqldb.QuizPersistenceHSQLDB;
import comp3350.lakers.quizme.utils.TestUtil;

public class QuizHandlerIT {
    private final String userId = "29289049-b0c4-4aff-9d05-667067ace368";
    private QuizHandler quizHandler;
    private File tempDb;
    private List<QuizBase> list;

    @Before
    public void setUp() throws IOException {
        this.tempDb = TestUtil.copyDB();
        final IQuiz persistence = new QuizPersistenceHSQLDB(this.tempDb.getAbsolutePath().replace(".script", ""));
        this.quizHandler = new QuizHandler(persistence, userId);

        list = new ArrayList<>();
        list.add(new MultipleChoiceQuiz(
                "Which city is the capital of Canada?",
                "Ottawa",
                new String[]{"Toronto", "Winnipeg", "Ottawa", "Vancouver"} , "Capital", "Geography")
        );
        list.add(new LongAnswerQuiz("What is 2 + 2?",
                "4",
                "Sum",
                "Math")
        );
        list.add(new LongAnswerQuiz("What is 2 * 2?",
                "4",
                "Multiplication",
                "Math")
        );
        list.add(new MultipleChoiceQuiz("Who do you think you are?",
                "I do not know",
                new String[]{"No one", "Nappster", "I do not know", "Heck yea"} , "Who?", "Philosophy")
        );
    }

    @Test
    public void testGetQuestion() {

        System.out.println("\nStarting testGetQuestion");

        assertTrue(list.get(0).equals(quizHandler.getQuiz(0)));
        assertTrue(list.get(1).equals(quizHandler.getQuiz(1)));
        assertTrue(list.get(2).equals(quizHandler.getQuiz(2)));
        assertTrue(list.get(3).equals(quizHandler.getQuiz(3)));

        System.out.println("\nEnd testGetQuestion");
    }

    @Test
    public void testGetQuizList() {
        System.out.println("\nStarting testGetList");

        assertTrue(list.get(0).equals(quizHandler.getQuizList(userId).get(0)));
        assertTrue(list.get(1).equals(quizHandler.getQuizList(userId).get(1)));
        assertTrue(list.get(2).equals(quizHandler.getQuizList(userId).get(2)));
        assertTrue(list.get(3).equals(quizHandler.getQuizList(userId).get(3)));

        System.out.println("\nEnd testGetList");
    }

    @Test
    public void testGetSize() {
        System.out.println("\nStarting testGetSize");

        assertEquals(4, quizHandler.getSize());

        System.out.println("\nEnd testGetSize");
    }

    @Test
    public void testInsertQuiz() {
        System.out.println("\nStarting testInsertQuiz");

        LongAnswerQuiz la = new LongAnswerQuiz(
                "AHHHHH",
                "Charlie bit me",
                "That really hurt",
                "OOOOHH");

        QuizBase insertedQuiz = quizHandler.insertQuiz(la, userId);

        assertEquals(5, quizHandler.getQuizList(userId).size());
        assertTrue(la.equals(insertedQuiz));

        System.out.println("\nEnd testInsertQuiz");
    }

    @Test
    public void testGetAnswer() {
        System.out.println("\nStarting testGetAnswer");
        String result = quizHandler.getQuizList(userId).get(0).getAnswer();
        assertEquals("Ottawa", result);
        System.out.println("\nEnd testGetAnswer");
    }
    @Test
    public void testCheckAnswerCorrect() {
        System.out.println("\nStarting testCheckAnswerCorrect");
        int position = 0;
        String answer = quizHandler.getQuizList(userId).get(position).getAnswer();
        boolean result = quizHandler.checkAnswer(position, answer);
        assertTrue(result);
        System.out.println("\nEnd testCheckAnswerCorrect");
    }

    @Test
    public void testCheckAnswerIncorrect() {
        System.out.println("\nStarting testCheckAnswerIncorrect");
        int position = 0;
        String answer = "";
        boolean result = quizHandler.checkAnswer(position, answer);
        assertFalse(result);
        System.out.println("\nEnd testCheckAnswerIncorrect");
    }

    @Test
    public void testFailedInsertQuiz() {
        System.out.println("\nStarting testFailedInsertQuiz");

        LongAnswerQuiz la = null;

        QuizBase insertedQuiz = quizHandler.insertQuiz(la, userId);

        assertEquals(-1, quizHandler.getQuizList(userId).indexOf(insertedQuiz));
        assertEquals(4, quizHandler.getSize());
        assertNull(insertedQuiz);

        System.out.println("\nEnd testFailedInsertQuiz");
    }

    @Test
    public void testFailedGetQuestion() {
        System.out.println("\nStarting testFailedGetQuestion");

        final int INVALID_POS = -1;
        assertNull(quizHandler.getQuiz(INVALID_POS));

        System.out.println("\nEnd testFailedGetQuestion");
    }

    @Test
    public void testDeleteQuiz() {
        System.out.println("\nStarting testDeleteQuiz");

        boolean deleted = quizHandler.deleteQuiz(1, userId);

        assertTrue(deleted);
        assertEquals(4, quizHandler.getSize());

        System.out.println("\nEnding testDeleteQuiz");
    }

    @Test
    public void testCheckAnswerByQIDCorrect() {
        System.out.println("\nStarting testCheckAnswerByQIDCorrect");

        assertEquals(quizHandler.getAnswerByQID(1), "Ottawa");

        System.out.println("\nEnd testCheckAnswerByQIDCorrect");
    }

    @Test
    public void testCheckAnswerByQIDIncorrect() {
        System.out.println("\nStarting testCheckAnswerByQIDIncorrect");

        assertNotEquals("Winnipeg", quizHandler.getAnswerByQID(1));

        System.out.println("\nEnd testCheckAnswerByQIDIncorrect");
    }

    @Test
    public void testGetQuizByQID() {
        System.out.println("\nStarting testGetQuizByQID");

        assertNull(quizHandler.getQuizByQID(5));

        System.out.println("\nEnd testGetQuizByQID");
    }

    @Test
    public void testFailedDeleteQuiz() {
        System.out.println("\nStarting testFailedDeleteQuiz");

        assertEquals(4, quizHandler.getSize());

        assertFalse(quizHandler.deleteQuiz(-1, userId));

        assertEquals(4, quizHandler.getSize());

        assertFalse(quizHandler.deleteQuiz(4, userId));

        assertEquals(4, quizHandler.getSize());
        System.out.println("\nEnding testFailedDeleteQuiz");
    }
}
