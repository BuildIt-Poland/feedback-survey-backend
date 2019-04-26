package com.buildit.survey;

import com.buildit.question.Question;
import com.buildit.question.QuestionService;
import com.buildit.utils.LocalDateTimeFormatter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ExportSurveyService {

    private final static Logger LOGGER = LogManager.getLogger(ExportSurveyService.class);

    private final QuestionService questionService;

    private final SurveyService surveyService;

    public ExportSurveyService(QuestionService questionService, SurveyService surveyService) {
        this.questionService = questionService;
        this.surveyService = surveyService;
    }

    byte[] exportToCSVFile() throws IOException {
        File file = generateCSVFile(surveyService.getAll(), questionService.getQuestions());
        return Files.readAllBytes(file.toPath());
    }

    File generateCSVFile(List<Survey> surveys, List<Question> questions) throws IOException {
        File file = File.createTempFile("feedback-survey-temp", ".csv");
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
        Stream<String> constantHeaders = Stream.of("Id", "Date");
        Stream<String> questionsContent = questions.stream().map(Question::getContent);
        return Stream.concat(constantHeaders, questionsContent)
                .toArray(String[]::new);
    }

    private List<String> prepareRecord(Survey survey, List<Question> questions) {
        List<String> record = new ArrayList<>(Arrays.asList(
                survey.getSurveyId(),
                LocalDateTimeFormatter.parseDate(survey.getSavedDate())
        ));
        record.addAll(getAnswers(survey, questions));
        return record;
    }

    private List<String> getAnswers(Survey survey, List<Question> questions) {
        Map<String, String> answerForQuestion = survey.getAnswers().stream()
                .collect(Collectors.toMap(Answer::getQuestionId, Answer::getAnswer));

        return questions.stream()
                .map(question -> answerForQuestion.get(question.getId()))
                .collect(Collectors.toList());
    }

}
