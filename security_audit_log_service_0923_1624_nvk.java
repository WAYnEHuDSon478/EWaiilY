// 代码生成时间: 2025-09-23 16:24:14
package com.example.security;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Single;
import java.util.Optional;
import javax.inject.Inject;

@Controller("/api/logs")
public class SecurityAuditLogService {

    // 依赖注入配置文件中的日志文件路径
    @Value("\${audit.log.file.path}")
    private String logFilePath;

    // 依赖注入日志服务，用于写入日志
    @Inject
    private LogService logService;

    // 获取安全审计日志文件的内容
    @Get("/{filename}")
    public Single<HttpResponse<?>> getSecurityAuditLogFile(@PathVariable String filename) {
        try {
            // 检查文件名是否合法
            if (filename.isEmpty() || !filename.endsWith(".log")) {
                return Single.just(HttpResponse.badRequest("Invalid filename"));
            }

            // 构建完整的日志文件路径
            String fullPath = logFilePath + filename;

            // 调用日志服务获取日志文件内容
            return logService.getLogContent(fullPath)
                    .map(content -> HttpResponse.ok(content))
                    .toSingle();

        } catch (Exception e) {
            // 错误处理
            return Single.just(HttpResponse.serverError(e.getMessage()));
        }
    }
}

// 日志服务接口
interface LogService {
    // 获取日志文件内容
    Single<String> getLogContent(String filePath);
}

// 日志服务实现
class LogServiceImpl implements LogService {

    @Override
    public Single<String> getLogContent(String filePath) {
        try {
            // 读取日志文件内容
            return Single.just(Files.readString(Paths.get(filePath)));
        } catch (IOException e) {
            // 文件读取异常处理
            return Single.error(new RuntimeException("Failed to read log file: " + filePath, e));
        }
    }
}

// 配置文件application.yml示例
/*
audit:
  log:
    file:
      path: "/path/to/audit/logs/"
*/