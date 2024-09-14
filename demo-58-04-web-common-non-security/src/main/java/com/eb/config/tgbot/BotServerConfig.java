package com.eb.config.tgbot;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = BotServerConfig.PREFIX)
@Data
@Slf4j
/*
 * 机器人服务的配置类
 */
public class BotServerConfig {
    public final static String PREFIX = "bot-server";
    private boolean enable = true;
    private String token;
    private Command command;

    @Data
    public static class Command {
        private String balanceCommand;
        private String balanceDescription;
        private String balanceMessage;
        private String contactCommand;
        private String contactDescription;
        private String contactMessage;
        private List<Contact> contact;
        private String helpCommand;
        private String helpDescription;
        private String helpMessage;
        private String startCommand;
        private String startDescription;
        private String selectCardCommand;
        private String selectCardDescription;
        private String selectCardMessage;
        private String selectNoCardMessage;
        private String selectCardSuccessMessage;
        private String selectCardFailMessage;
        private String unknownCommandMessage;
        private String noBindCodeMessage;
    }

    @Data
    public static class Contact {
        private String name;
        private String contactLink;
    }

    @PostConstruct
    // 打印获取到的日志
    public void LogConfig() {
        log.info("读取tgbot-server配置文件：{}", this.toString());
    }

}
