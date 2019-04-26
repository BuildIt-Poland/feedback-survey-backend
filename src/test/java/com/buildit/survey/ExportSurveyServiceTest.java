package com.buildit.survey;

import com.buildit.question.Question;
import com.buildit.question.QuestionService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ExportSurveyServiceTest {

    @Mock
    private QuestionService questionService;

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private ExportSurveyService testee;

    @Test
    void generateCSVFile() throws IOException {
        //GIVEN
        List<Survey> surveys = Arrays.asList(createSurvey(), createSurvey());
        List<Question> questions = Arrays.asList(createQuestion(1L), createQuestion(2L), createQuestion(3L));

        //WHEN
        File file = testee.generateCSVFile(surveys, questions);

        //THEN
        assertTrue(file.exists());
        verifyFileContent(file);
    }

    @Test
    void exportToCSVFile() throws IOException {
        //GIVEN
        List<Survey> surveys = Arrays.asList(createSurvey(), createSurvey());
        List<Question> questions = Arrays.asList(createQuestion(1L), createQuestion(2L), createQuestion(3L));

        //WHEN
        when(questionService.getQuestions()).thenReturn(questions);
        when(surveyService.getAll()).thenReturn(surveys);

        byte[] fileContent = testee.exportToCSVFile();

        //THEN
        verify(questionService).getQuestions();
        verify(surveyService).getAll();

        File file = createFile(fileContent);
        verifyFileContent(file);
    }

    private File createFile(byte[] fileContent) throws IOException {
        File file = File.createTempFile("survey-temp", "csv");
        OutputStream os = new FileOutputStream(file);
        os.write(fileContent);
        os.close();
        return file;
    }

    private void verifyFileContent(File file) throws IOException {
        Reader in = new FileReader(file.getPath());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            assertEquals(5, record.size());
            assertEquals("surveyId", record.get("Id"));
            assertEquals("2019-04-22 11:22:33", record.get("Date"));
            assertEquals("answer 1", record.get("Question 1"));
            assertEquals("answer 2", record.get("Question 2"));
            assertEquals("answer 3", record.get("Question 3"));
        }
    }

    private Question createQuestion(Long id) {
        Question question = new Question();
        question.setId(id.toString());
        question.setContent("Question " + id);
        question.setOrdinal(id);
        return question;
    }

    private Survey createSurvey() {
        List<Answer> answers = Arrays.asList(createAnswer("1"), createAnswer("2"), createAnswer("3"));
        Survey survey = new Survey();
        survey.setSurveyId("surveyId");
        survey.setSavedDate(LocalDateTime.of(2019, 4, 22, 11, 22, 33, 44));
        survey.setAnswers(answers);
        return survey;
    }

    private Answer createAnswer(String id) {
        Answer answer = new Answer();
        answer.setQuestionId(id);
        answer.setAnswer("answer " + id);
        return answer;
    }

}