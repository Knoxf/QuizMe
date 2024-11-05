package comp3350.lakers.quizme.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import comp3350.lakers.quizme.objects.quizzes.LongAnswerQuiz;
import comp3350.lakers.quizme.objects.quizzes.MultipleChoiceQuiz;
import comp3350.lakers.quizme.objects.quizzes.QuizBase;
import comp3350.lakers.quizme.persistence.IQuiz;

public class QuizPersistenceHSQLDB implements IQuiz {
    private final String dbPath;

    public QuizPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public List<QuizBase> getQuizList(String userId) {
        final List<QuizBase> quizzes = new ArrayList<>();
        final Map<Integer, List<String[]>> choiceMap = new HashMap<>();

        try (final Connection connection = connection()) {
            final Statement statement = connection.createStatement();
            final ResultSet choiceResultSet = statement.executeQuery("SELECT * FROM CHOICE");

            // First, go through the entire CHOICE table and map QID to choices.
            while(choiceResultSet.next()) {
                int QID = choiceResultSet.getInt("QID");
                String[] choiceData = new String[2];
                choiceData[0] = choiceResultSet.getString("CHOICETEXT");
                choiceData[1] = Boolean.toString(choiceResultSet.getBoolean("ISANSWER"));

                // Add to map.
                if (!choiceMap.containsKey(QID)) {
                    choiceMap.put(QID, new ArrayList<>());
                }

                Objects.requireNonNull(choiceMap.get(QID)).add(choiceData);
            }

            // Then, go through the quiz table.
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quiz WHERE UID = ?");
            preparedStatement.setString(1, userId);
            final ResultSet quizResultSet = preparedStatement.executeQuery();

            while(quizResultSet.next()) {
                final int QID = quizResultSet.getInt("QID");
                final String type = quizResultSet.getString("TYPE");
                final String question = quizResultSet.getString("QUESTION");
                final String quizName = quizResultSet.getString("QUIZNAME");
                final String quizCategory = quizResultSet.getString("QUIZCATEGORY");

                // Look up the choices from the map.
                final List<String[]> choices = choiceMap.get(QID);

                if(type.equals("MCQ")){
                    String[] option = new String[4];
                    int optionCounter = 0;
                    String answer = "";

                    assert choices != null;
                    for (String[] choice : choices) {
                        if(Boolean.parseBoolean(choice[1])){
                            answer = choice[0];
                        }
                        option[optionCounter] = choice[0];
                        optionCounter++;
                    }

                    MultipleChoiceQuiz multipleChoiceQuiz = new MultipleChoiceQuiz(question , answer , option , quizName , quizCategory );
                    multipleChoiceQuiz.setQID(QID);

                    quizzes.add(multipleChoiceQuiz);
                }else if(type.equals("LAQ")){
                    String answer = "";

                    assert choices != null;
                    for (String[] choice : choices) {
                        if(Boolean.parseBoolean(choice[1])){
                            answer = choice[0];
                            break;
                        }
                    }

                    LongAnswerQuiz longAnswerQuiz = new LongAnswerQuiz(question , answer , quizName , quizCategory);
                    longAnswerQuiz.setQID(QID);
                    quizzes.add(longAnswerQuiz);
                }
            }

            quizResultSet.close();
            choiceResultSet.close();
            statement.close();

            return quizzes;
        } catch (final SQLException e) {
            throw new DataException(e);
        }
    }

    @Override
    public QuizBase insertQuiz(QuizBase currentQuestion, String userId) {

        try (final Connection connection = connection()) {
            PreparedStatement st = connection.prepareStatement("INSERT INTO QUIZ VALUES(?, ?, ?, ?, ?, ?)");
            st.setInt(1 , QIDGenerator());
            st.setString(2 , userId);
            st.setString(3 , currentQuestion.getQuestion());
            st.setString(5 , currentQuestion.getQuizName());
            st.setString(6 , currentQuestion.getCategory());

            if(currentQuestion instanceof MultipleChoiceQuiz){
                st.setString(4 , "MCQ");
                st.executeUpdate();

                String answer = currentQuestion.getAnswer();
                String[] option = ((MultipleChoiceQuiz) currentQuestion).getOption();
                for (String s : option) {
                    st = connection.prepareStatement("INSERT INTO CHOICE VALUES(?, ? , ? , ?)");
                    st.setInt(1, CIDGenerator());
                    st.setInt(2, QIDGenerator() - 1);
                    st.setString(3, s);

                    st.setBoolean(4, s.equals(answer));

                    st.executeUpdate();
                }
            }else if(currentQuestion instanceof LongAnswerQuiz){
                st.setString(4 , "LAQ");
                st.executeUpdate();

                st = connection.prepareStatement("INSERT INTO CHOICE VALUES(?, ? , ? , ?)");
                st.setInt(1 , CIDGenerator());
                st.setInt(2 , QIDGenerator() - 1);
                st.setString(3 , currentQuestion.getAnswer());
                st.setBoolean(4 , true);
                st.executeUpdate();
            }

            st.close();

            return currentQuestion;
        } catch (final SQLException e) {
            throw new DataException(e);
        }

    }

    @Override
    public boolean deleteQuiz(int position, String userId) {
        QuizBase quizBase = getQuizList(userId).get(position);
        int QID = quizBase.getQID();
        boolean deleteSuccessful = false;
        try (final Connection connection = connection()) {
            final Statement statement = connection.createStatement();

            // Deleting corresponding entries in CHOICE table
            int choiceDeleteCount = statement.executeUpdate("DELETE FROM CHOICE WHERE QID=" + QID);

            // Deleting quiz from QUIZ table
            int quizDeleteCount = statement.executeUpdate("DELETE FROM QUIZ WHERE QID=" + QID);

            statement.close();

            if (choiceDeleteCount > 0 && quizDeleteCount > 0) {
                deleteSuccessful = true;
            }

        } catch (final SQLException e) {
            throw new DataException(e);
        }

        return deleteSuccessful;
    }

    private int QIDGenerator(){
        int maxQID = 0;

        try (final Connection connection = connection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(QID) AS MAX_ID FROM QUIZ");

            if (resultSet.next()) {
                maxQID = resultSet.getInt("MAX_ID");
            }

            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DataException(e);
        }

        return maxQID + 1;
    }

    private int CIDGenerator(){
        int maxCID = 0;

        try (final Connection connection = connection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT MAX(CID) AS MAX_ID FROM CHOICE");

            if (resultSet.next()) {
                maxCID = resultSet.getInt("MAX_ID");
            }

            resultSet.close();
            statement.close();
        } catch (final SQLException e) {
            throw new DataException(e);
        }

        return maxCID + 1;
    }


}
