package com.buildit.survey;

import com.buildit.question.Question;
import com.buildit.question.QuestionDao;
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
    private QuestionDao questionDao;

    @Mock
    private SurveyDao surveyDao;

    @InjectMocks
    private ExportSurveyService testee;

    @Test
    void generateCSVFile() throws IOException {
        //GIVEN
        List<SurveyDTO> surveys = Arrays.asList(new SurveyTestData().prepareSurveyDTO(), new SurveyTestData().prepareSurveyDTO());
        List<Question> questions = Arrays.asList(createQuestion(1L), createQuestion(2L), createQuestion(3L));

        //WHEN
        File file = testee.generateCSVFile(surveys, questions);

        //THEN
        assertTrue(file.exists());
        verifyFileContent(file, "Joanna");
    }

    @Test
    void exportToCSVFile() throws IOException {
        //GIVEN
        List<Survey> surveys = Arrays.asList(new SurveyTestData().prepareSurvey(), new SurveyTestData().prepareSurvey());
        List<Question> questions = Arrays.asList(createQuestion(1L), createQuestion(2L), createQuestion(3L));

        //WHEN
        when(questionDao.getQuestions()).thenReturn(questions);
        when(surveyDao.getAll()).thenReturn(surveys);

        byte[] fileContent = testee.exportToCSVFile();

        //THEN
        verify(questionDao).getQuestions();
        verify(surveyDao).getAll();

        File file = createFile(fileContent);
        verifyFileContent(file, "");
    }


    @Test
    void generateCSVFileForSurvey() throws IOException {
        //GIVEN
        SurveyDTO survey = new SurveyTestData().prepareSurveyDTO();
        List<Question> questions = Arrays.asList(createQuestion(1L), createQuestion(2L), createQuestion(3L));

        //WHEN
        when(questionDao.getQuestions()).thenReturn(questions);

        File file = testee.generateCSVFile(survey);

        //THEN
        assertTrue(file.exists());
        verifyFileContent(file, "Joanna");
    }

    private File createFile(byte[] fileContent) throws IOException {
        File file = File.createTempFile("survey-temp", "csv");
        OutputStream os = new FileOutputStream(file);
        os.write(fileContent);
        os.close();
        return file;
    }

    private void verifyFileContent(File file, String employeeName) throws IOException {
        Reader in = new FileReader(file.getPath());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        for (CSVRecord record : records) {
            assertEquals(6, record.size());
            assertEquals("surveyId", record.get("Id"));
            assertEquals(employeeName, record.get("Employee name"));
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

}