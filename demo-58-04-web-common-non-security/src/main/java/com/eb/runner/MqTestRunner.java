package com.eb.runner;

import com.eb.mp.mysql.entity.business.VipInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MqTestRunner implements ApplicationRunner {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (true) {
            return;
        }

        List<VipInfoEntity> entities = TestDataInitRunner.mockVipInfoEntities();
        rabbitTemplate.convertAndSend(entities);
    }
}
