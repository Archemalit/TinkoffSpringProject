package com.archemalt.TinkoffSpringProject.service;

import java.util.concurrent.ExecutionException;

public interface TranslateService {
    String translateText(String ip, String text, String srcLanguage, String resultLanguage, Character separator) throws ExecutionException, InterruptedException;
}
