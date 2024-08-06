package com.archemalt.TinkoffSpringProject.service.Impl;

import com.archemalt.TinkoffSpringProject.dto.TranlateText;
import com.archemalt.TinkoffSpringProject.repository.TranslateRepository;
import com.archemalt.TinkoffSpringProject.service.TranslateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class TranslateServiceImpl implements TranslateService {
    private final TranslateRepository repository;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    @Override
    public String translateText(String ip, String text, String srcLanguage, String resultLanguage, Character separator) {
        String [] words = text.split(String.valueOf(separator));
        List<Future<String>> translateResult = new ArrayList<>();
        for (String word : words) {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&dt=t&sl=" + srcLanguage + "&tl=" + resultLanguage + "&q=" + word;
            Future<String> task = poolExecutor.submit(new TranlateText(url, restTemplate, mapper));
            translateResult.add(task);
        }
        try {
            StringBuilder resultText = new StringBuilder();
            for (Future<String> item : translateResult) {
                resultText.append(item.get()).append(separator);
            }
            resultText.deleteCharAt(resultText.length() - 1);
            repository.saveResult(ip, srcLanguage, resultLanguage, text, resultText.toString());
            return resultText.toString();
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
