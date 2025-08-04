// 代码生成时间: 2025-08-04 10:15:38
import io.micronaut.context.annotation.Requires;
    import io.micronaut.scheduling.annotation.Scheduled;
    import javax.inject.Singleton;
    import java.util.concurrent.TimeUnit;
    import java.util.logging.Logger;

    /**
     * 定时任务调度器
     * @author micronaut-user
     */
    @Singleton
    @Requires(property = "myapp.scheduledtask.enabled", value = "true") // 启动时需要配置 myapp.scheduledtask.enabled=true
    public class ScheduledTask {

        private final Logger logger = Logger.getLogger(ScheduledTask.class.getName());

        /**
         * 每10秒执行一次的定时任务
         */
        @Scheduled(fixedRate = "10s")
        public void executeFixedRateTask() {
            try {
                // 任务逻辑实现
                logger.info("Fixed rate task executed at: " + System.currentTimeMillis());
            } catch (Exception e) {
                // 错误处理
                logger.severe("Error executing fixed rate task: " + e.getMessage());
            }
        }

        /**
         * 初始延迟1秒，每5秒执行一次的定时任务
         */
        @Scheduled(initialDelay = 1000, fixedDelay = 5000)
        public void executeInitialDelayTask() {
            try {
                // 任务逻辑实现
                logger.info("Initial delay task executed at: " + System.currentTimeMillis());
            } catch (Exception e) {
                // 错误处理
                logger.severe("Error executing initial delay task: " + e.getMessage());
            }
        }

        // 可以根据需要添加更多的定时任务方法
    }
    