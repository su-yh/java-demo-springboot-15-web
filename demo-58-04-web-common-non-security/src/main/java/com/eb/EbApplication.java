package com.eb;

import com.eb.bytecodes.MybatisSqlDetailText;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author suyh
 * @since 2023-11-26
 */
@SpringBootApplication
public class EbApplication {
    public static void main(String[] args) {
        MybatisSqlDetailText.rebuildSqlDetail();
        SpringApplication.run(EbApplication.class, args);
    }
}
