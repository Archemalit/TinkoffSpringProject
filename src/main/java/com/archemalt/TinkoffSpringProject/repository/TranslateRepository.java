package com.archemalt.TinkoffSpringProject.repository;

public interface TranslateRepository {
    void saveResult(String ip, String srcLanguage, String resultLanguage, String text, String resultText);
}
