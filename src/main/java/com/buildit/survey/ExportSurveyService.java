package com.buildit.survey;

import com.buildit.question.Question;
import com.buildit.question.QuestionDao;
import com.buildit.utils.LocalDateTimeFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ExportSurveyService {

    private final static Logger LOGGER = LogManager.getLogger(ExportSurveyService.class);

    private final QuestionDao questionDao;

    private final SurveyDao surveyService;

    ExportSurveyService(QuestionDao questionDao, SurveyDao surveyDao) {
        this.questionDao = questionDao;
        this.surveyService = surveyDao;
    }

    byte[] exportToCSVFile() throws IOException {
        List<SurveyDTO> surveys = new SurveyMapper().mapToDTO(surveyService.getAll());
        File file = generateCSVFile(surveys, questionDao.getQuestions());
        return Files.readAllBytes(file.toPath());
    }

    File generateCSVFile(SurveyDTO survey) throws IOException {
        return generateCSVFile(Collections.singletonList(survey), questionDao.getQuestions());
    }

    File generateCSVFile(List<SurveyDTO> surveys, List<Question> questions) throws IOException {
        File file = File.createTempFile("feedback-survey", ".csv");
        FileWriter out = new FileWriter(file);

        String[] headers = prepareHeaders(questions);
        CSVPrinter printer = CSVFormat.DEFAULT
                .withHeader(headers)
                .print(out);

        surveys.forEach(
                survey -> {
                    List<String> record = prepareRecord(survey, questions);
                    try {
                        printer.printRecord(record);
                    } catch (IOException e) {
                        LOGGER.error("Error in exporting surveys to csv " + e);
                    }
                }
        );
        printer.flush();
        return file;
    }

    private String[] prepareHeaders(List<Question> questions) {
        Stream<String> constantHeaders = Stream.of("Id", "Employee name", "Date");
        Stream<String> questionsContent = questions.stream().map(Question::getContent);
        return Stream.concat(constantHeaders, questionsContent)
                .toArray(String[]::new);
    }

    private List<String> prepareRecord(SurveyDTO survey, List<Question> questions) {
        List<String> record = new ArrayList<>(Arrays.asList(
                survey.getSurveyId(),
                survey.getEmployeeName(),
                LocalDateTimeFormatter.parseDate(survey.getSavedDate())
        ));
        record.addAll(getAnswers(survey, questions));
        return record;
    }

    private List<String> getAnswers(SurveyDTO survey, List<Question> questions) {
        Map<String, String> answerForQuestion = survey.getAnswers().stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Answer::getAnswer));

        return questions.stream()
                .map(question -> answerForQuestion.get(question.getId()))
                .collect(Collectors.toList());
    }

}
