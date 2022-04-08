package ru.sargassov.mcuserweb.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration //Класс указывающий на местоположение конфиг файла токена
@PropertySource("secrets.properties")
public class AppConfig { }
